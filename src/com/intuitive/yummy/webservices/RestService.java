package com.intuitive.yummy.webservices;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.intuitive.yummy.models.Model;
import com.intuitive.yummy.models.Vendor;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
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
	
	public final static String BundleObjectKey = "objects";
	
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
	
	private final static HashMap<Action,String> actionMethodMapping = new HashMap<Action, String>() {
		private static final long serialVersionUID = 1L;
	{
		put(Action.CREATE, "POST");
		put(Action.READSINGLE, "GET");
		put(Action.READALL, "GET");
		put(Action.UPDATE, "POST");
		put(Action.DELETE, "DELETE");
	}};
	
	/**
	 * Builds the request URL based on the model type and action
	 * @param modelType - the class of the resource to request
	 * @param action - the action the URL will be used to perform
	 * @return entire request URL
	 */
	private final static String buildUrl(Class<?> modelType, Action action, Intent intent){
		
		StringBuilder url = new StringBuilder(baseUrl);
		url.append("/".concat(controllerNames.get(modelType)));
		url.append("/".concat(actionNames.get(action)));
		
		// add parameters
		// TODO add support for option params i.e., paging, limit, etc
		if(action == Action.READSINGLE){
			if(!intent.hasExtra(IntentExtraKeys.MODEL_ID))
				throw new IllegalArgumentException("An id parameter must be supplied when reading a single Model object.");
			url.append("/".concat(String.valueOf(intent.getIntExtra(IntentExtraKeys.MODEL_ID, 0))));
		}
		else if(action == Action.UPDATE){
			if(!intent.hasExtra(IntentExtraKeys.MODEL_ID))
				throw new IllegalArgumentException("An id parameter must be supplied when reading a single Model object.");
			
			url.append("/".concat(String.valueOf(intent.getIntExtra(IntentExtraKeys.MODEL_ID, 0))));
		}
		
		
		return url.toString();
	}
	
	protected void onHandleIntent(Intent intent) {
		Log.d("yummy", "Handling JSON request intent...");
        
		// get data properties
		@SuppressWarnings("unchecked")
		Class<Model> modelType = (Class<Model>) intent.getSerializableExtra(IntentExtraKeys.MODEL_CLASS);
		Action action = (Action) intent.getSerializableExtra(IntentExtraKeys.ACTION);
		ArrayList<String> parameters = new ArrayList<String>();
		
		// get parameters
		for(int i=0; intent.hasExtra(IntentExtraKeys.PARAMETER(i)); i++){
			parameters.add(intent.getStringExtra(IntentExtraKeys.PARAMETER(i)));
		}

		// build URL
		String requestUrl = buildUrl(modelType, action, intent);
		
		// create receiver and alert to progress
		final ResultReceiver receiver = intent.getParcelableExtra(IntentExtraKeys.RECEIVER);
        receiver.send(RestResultCode.RUNNING.getValue(), Bundle.EMPTY);
        
        // initialize bundle to pass back to caller
        Bundle b = new Bundle();
       
        
        try {
        	
        	// get postData is action is a POST action
        	ArrayList<PostParameter> postParams = null;
        	if(actionMethodMapping.get(action) == "POST")
        	{
        		Model modelObject = intent.getParcelableExtra(IntentExtraKeys.MODEL);
        		postParams = PostParameter.hashMapToNameValuePairs(modelObject.getPostData());
        	}        	
        	
        	// log URL and post data
        	// TODO take this out for production? or use debug var to check
        	Log.v("yummy", "Making HTTP request to URL: " + requestUrl);
        	if(actionMethodMapping.get(action) == "POST")
        	{
        		StringBuilder postData_logMsg = new StringBuilder();
        		postData_logMsg.append("POST data:\n");
        		for(PostParameter param : postParams){
        			postData_logMsg.append("key: " + param.getName() + ", value: " + param.getValue());
        		}
        		Log.d("yummy", postData_logMsg.toString());
        	}
        	
        	// fire HTTP request and handle response
        	JSONObject json = jParser.makeHttpRequest(requestUrl, actionMethodMapping.get(action), postParams);
        	Log.d("yummy", "JSON response: ".concat(json.toString()));

        	try {
        		// only continue on success
        		String success = json.getString("success");
        		        			
        		if(action == Action.READALL || action == Action.READSINGLE){
	        			if (success.equals("false"))
	            			throw new Exception();
	            		
        			
	        			if(!json.has("data")){
	        				throw new JSONException("JSON response badly formatted. No 'data' element.");
	        			}
			        			
	        			// create list to return via bundle
		        		ArrayList<Model> objectList = new ArrayList<Model>();
		        		
		        		if(json.getInt("count") == 1){
		        			JSONObject obj_json = json.getJSONObject("data");
		
		        			Model object = (Model) modelType.newInstance();
		        			JSONObject jsonObject = obj_json.getJSONObject(object.getModelName());
		        			object.parseJson(jsonObject);
		
		        			objectList.add(object);
		        		}else if(json.getInt("count") > 1){
			        		JSONArray objects_json = json.optJSONArray("data"); 
		        			
			        		// convert JSONArray items to model objects
		        			for(int i=0; i<objects_json.length(); i++){
		        				// create a new Model type and fill it using the JSON data
		        				Model object = (Model) modelType.newInstance();
		        				JSONObject jsonObject = objects_json.getJSONObject(i).getJSONObject(object.getModelName());
		        				object.parseJson(jsonObject);
		        				
		        				objectList.add(object);
		        			}
		        		}
		        		b.putParcelableArrayList(BundleObjectKey, objectList);
		        		
	        		}else{
	        			b.putBoolean(IntentExtraKeys.SUCCESS, success.equals("true"));
	        		}
        		
        		receiver.send(RestResultCode.FINISHED.getValue(), b);
        		
        	} catch (JSONException e) {
        		e.printStackTrace();
        		receiver.send(RestResultCode.ERROR.getValue(), b);
        	}

        } catch(Exception e) {
        	Log.e("yummy", e.toString());
        	b.putString(IntentExtraKeys.ERROR, e.getMessage());
        	receiver.send(RestResultCode.ERROR.getValue(), b);
        }    
	}

}
