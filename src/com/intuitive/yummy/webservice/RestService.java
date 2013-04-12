package com.intuitive.yummy.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.intuitive.yummy.model.Model;
import com.intuitive.yummy.model.Vendor;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Used to make HTTP requests to the backend Yummy server. 
 * @author Edgar Paz
 *
 */
public class RestService extends IntentService {
	
	private JSONParser jParser = new JSONParser();
	private static String baseUrl = "http://yummy.edgarpaz.com/cakephp";
	
	private JSONArray vendors = null;
	private ArrayList<HashMap<String, String>> vendorsList =  new ArrayList<HashMap<String, String>>();
	
	public RestService(){
		super("RestAPI service");
		Log.d("yummy", "RestService started");
	}
	
	public RestService(String name) {
		super(name);
		Log.d("yummy", "RestService started");
	}
	
	public enum Action{
		CREATE,
		READALL,
		READSINGLE,
		UPDATE,
		DELETE
	}
	
	
	
	private final static HashMap<Class<?>,String> controllerNames = new HashMap<Class<?>, String>() {
		private static final long serialVersionUID = 1L;
	{
		put(Vendor.class, "vendors");
		// TODO add other classes and their controllers
	}};
	
	
	// for now, all controllers will have the same action name uri's
	private final static HashMap<Action,String> actionNames = new HashMap<Action, String>() {
		private static final long serialVersionUID = 1L;
	{
		put(Action.CREATE, "add");
		put(Action.READSINGLE, "view");
		put(Action.READALL, "index");
		put(Action.UPDATE, "edit");
		put(Action.DELETE, "delete");
	}};
	
	/**
	 * Builds the request URL based on the model type and action
	 * @param modelType - the class of the resource to request
	 * @param action - the action the URL will be used to perform
	 * @return entire request URL
	 */
	private final static String getUrl(Class<?> modelType, Action action, ArrayList<Object> parameters){
		
		StringBuilder url = new StringBuilder(baseUrl);
		url.append("/".concat(controllerNames.get(modelType)));
		url.append("/".concat(actionNames.get(action)));
		
		// add parameters
		// TODO add some sort of checks to make sure the correct # of params
		// have been passed given the action being performed
		if(parameters != null && parameters.size() > 0){
			for (Object param : parameters){
				url.append("/".concat(param.toString()));
			}	
		}
		
		return url.toString();
	}
	
	protected void onHandleIntent(Intent intent) {
		Log.d("yummy", "Handling JSON request intent...");
        
		// get data properties
		@SuppressWarnings("unchecked")
		Class<Model> modelType = (Class<Model>) intent.getSerializableExtra(IntentExtraKeys.MODEL_TYPE);
		Action action = (Action) intent.getSerializableExtra(IntentExtraKeys.ACTION);
		ArrayList<String> parameters = new ArrayList<String>();
		
		// get parameters
		int param = 1;
		while(intent.hasExtra(IntentExtraKeys.PARAMETER(param))){
			parameters.add(intent.getStringExtra(IntentExtraKeys.PARAMETER(param)));
			param++;
		}

		// build URL
		String requestUrl = getUrl(modelType, action, null);
		
		// create receiver and alert to progress
		final ResultReceiver receiver = intent.getParcelableExtra(IntentExtraKeys.RECEIVER);
        receiver.send(RestResultCode.RUNNING.getValue(), Bundle.EMPTY);
        
        // initialize bundle to pass back to caller
        Bundle b = new Bundle();
       
        // fire HTTP request and handle response
        try {
        	
        	Log.v("yummy", "Making HTTP request...");
        	JSONObject json = jParser.makeHttpRequest(requestUrl, "GET", null);
        	Log.d("yummy", "JSON response: ".concat(json.toString()));

        	try {
        		// only continue on success
        		Boolean success = json.getBoolean("success");
        		if (success) {

        			JSONArray objects = json.getJSONArray("data");
        			if(!json.has("data")){
        				throw new JSONException("JSON response badly formatted. No 'data' element.");
        			}
        			
        			// convert JSONArray items to model objects
        			Model[] objectArray = new Model[objects.length()];
        			for(int i=0; i<objects.length(); i++){
        				
        				Model object = (Model) modelType.newInstance();
        				JSONObject jsonObject = objects.getJSONObject(i).getJSONObject(object.getModelName());
        				object.parseJson(jsonObject);
        				
        				objectArray[i] = object;
        			}
        			
        			b.putParcelableArray("objectsArray", objectArray);
    
        			receiver.send(RestResultCode.FINISHED.getValue(), b);
        			
        		} else {
        			receiver.send(RestResultCode.ERROR.getValue(), b);
        		}
        		
        	} catch (JSONException e) {
        		e.printStackTrace();
        		receiver.send(RestResultCode.ERROR.getValue(), b);
        	}

        } catch(Exception e) {
        	b.putString(Intent.EXTRA_TEXT, e.toString());
        	receiver.send(RestResultCode.ERROR.getValue(), b);
        }    
	}

}
