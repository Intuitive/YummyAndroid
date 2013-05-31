package com.intuitive.yummy.webservices;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.intuitive.yummy.models.Model;
import com.intuitive.yummy.models.Order;
import com.intuitive.yummy.models.OrderItem;
import com.intuitive.yummy.models.User;
import com.intuitive.yummy.models.Vendor;
import com.intuitive.yummy.models.MenuItem;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
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
		DELETE,
		CREATE_JSON
	}
	
	public final static String BundleObjectKey = "OBJECTS";
	
	private final static HashMap<Class<?>,String> controllerNames = new HashMap<Class<?>, String>() {
		private static final long serialVersionUID = 1L;
	{
		put(Vendor.class, "vendors");
		put(User.class, "users");
		put(MenuItem.class, "menuItems");
		put(Order.class, "orders");
		put(OrderItem.class, "orderItems");
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
		put(Action.CREATE_JSON, "add");
	}};
	
	private final static HashMap<Action,String> actionMethodMapping = new HashMap<Action, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	{
		put(Action.CREATE, "POST");
		put(Action.READSINGLE, "GET");
		put(Action.READALL, "GET");
		put(Action.UPDATE, "POST");
		put(Action.DELETE, "POST");
		put(Action.CREATE_JSON, "JSON");
	}};
	
	/**
	 * Builds the request URL based on the model type and action
	 * URL format is : [base url]/[controller name]/[action name]/[optional parameters]
	 * examples: http://www.example.com/people/view/1
	 * http://www.example.com/people/index
	 * @param modelType - the class of the resource to request
	 * @param action - the action the URL will be used to perform
	 * @return entire request URL
	 */
	private final static String buildUrl(Class<?> modelType, Action action, Intent intent){
		
		StringBuilder url = new StringBuilder(baseUrl);
		String controllerName = controllerNames.get(modelType);
		
		if(controllerName == null)
			throw new IllegalArgumentException("No controller name mapped for the class " + modelType.toString());
		
		// add controller and action to url
		url.append("/".concat(controllerName));
		url.append("/".concat(actionNames.get(action)));
		
		// add parameters
		if(action == Action.READALL || action == Action.READSINGLE){
			// TODO add support for option params i.e., paging, limit, etc
			if(intent.hasExtra(IntentExtraKeys.MODEL_ID))
				url.append("/".concat(String.valueOf(intent.getIntExtra(IntentExtraKeys.MODEL_ID, 0))));
			if(intent.hasExtra(IntentExtraKeys.PARAMETER1))
				url.append("/".concat(intent.getStringExtra(IntentExtraKeys.PARAMETER1)));
			if(intent.hasExtra(IntentExtraKeys.PARAMETER2))
				url.append("/".concat(intent.getStringExtra(IntentExtraKeys.PARAMETER2)));
			if(intent.hasExtra(IntentExtraKeys.PARAMETER3))
				url.append("/".concat(intent.getStringExtra(IntentExtraKeys.PARAMETER3)));
		}
		else if(action == Action.UPDATE){
			url.append("/".concat(String.valueOf(intent.getIntExtra(IntentExtraKeys.MODEL_ID, 0))));
		}
		else if(action != Action.CREATE)
			url.append("/".concat(String.valueOf(intent.getIntExtra(IntentExtraKeys.MODEL_ID, 0))));
		
		return url.toString();
	}
	
	/**
	 * Creates an intent for the RestService to create a Model object
	 * @param modelObj The object to persist
	 * @param activity An instance of the calling activity
	 * @param receiver The receiver to send the result data to
	 * @return An intent with the proper extras attached
	 */
	public static Intent getCreateIntent(Model modelObj, Activity activity, RestResponseReceiver receiver){
		
		if(modelObj.getId() != null && modelObj.getId() > 0){
			throw new IllegalArgumentException("Models can only be created with Id < 1. For updates/edits, use RestService.getUpdateIntent().");
		}
		
		if(!Model.class.isAssignableFrom(modelObj.getClass())){
			throw new IllegalArgumentException("model class must implement Model interface");
		}
		
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, activity, RestService.class);
		intent.putExtra(IntentExtraKeys.ACTION, Action.CREATE);
		intent.putExtra(IntentExtraKeys.RECEIVER, receiver);
		
      	intent.putExtra(IntentExtraKeys.MODEL, (Parcelable) modelObj);
      	intent.putExtra(IntentExtraKeys.MODEL_CLASS, modelObj.getClass());
      	
      	return intent;
	}
	
	/**
	 * Creates an intent for the RestService to read a single Model object
	 * @param modelId The Id of the Model object to read
	 * @param modelClass The class of Model objects to read.
	 * @param activity An instance of the calling activity
	 * @param receiver The receiver to send the result data to
	 * @return An intent with the proper extras attached
	 */
	public static Intent getReadByIdIntent(int modelId, Class<?> modelClass, Activity activity, RestResponseReceiver receiver){
		
		if(modelId < 1){
			throw new IllegalArgumentException("modelId must be > 0");
		}
		if(!Model.class.isAssignableFrom(modelClass)){
			throw new IllegalArgumentException("model class must implement Model interface");
		}
		
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, activity, RestService.class);
		intent.putExtra(IntentExtraKeys.ACTION, Action.READSINGLE);
		intent.putExtra(IntentExtraKeys.RECEIVER, receiver);
	
		intent.putExtra(IntentExtraKeys.MODEL_ID, modelId);
		intent.putExtra(IntentExtraKeys.MODEL_CLASS, modelClass);
		
		return intent;
	}
	
	/**
	 * Creates an intent for the RestService to read multiple Model objects
	 * @param modelClass The class of Model objects to read.
	 * @param activity An instance of the calling activity
	 * @param receiver The receiver to send the result data to
	 * @return An intent with the proper extras attached
	 */
	public static Intent getReadManyIntent(Class<?> modelClass, Activity activity, RestResponseReceiver receiver){
		
		if(!Model.class.isAssignableFrom(modelClass)){
			throw new IllegalArgumentException("model class must implement Model interface");
		}		
		
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, activity, RestService.class);
		intent.putExtra(IntentExtraKeys.RECEIVER, receiver);
		intent.putExtra(IntentExtraKeys.ACTION, Action.READALL);
		
		intent.putExtra(IntentExtraKeys.MODEL_CLASS, modelClass);
		
		return intent;
	}
	
	/**
	 * Creates an intent for the RestService to read all Model objects that meet parameter
	 * @param modelClass The class of Model objects to read.
	 * @param activity An instance of the calling activity
	 * @param receiver The receiver to send the result data to
	 * @return An intent with the proper extras attached
	 */
	public static Intent getReadManyByParamIntent(Class<?> modelClass, String parameter, Activity activity, RestResponseReceiver receiver){
		
		if(!Model.class.isAssignableFrom(modelClass)){
			throw new IllegalArgumentException("model class must implement Model interface");
		}		
		
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, activity, RestService.class);
		intent.putExtra(IntentExtraKeys.RECEIVER, receiver);
		intent.putExtra(IntentExtraKeys.ACTION, Action.READALL);
		
		intent.putExtra(IntentExtraKeys.MODEL_CLASS, modelClass);
		intent.putExtra(IntentExtraKeys.PARAMETER1, parameter);
		
		return intent;
	}
	
	/**
	 * Creates an intent for the RestService to persist an existing Model.
	 * @param modelObj The object to persist
	 * @param activity An instance of the calling activity
	 * @param receiver The receiver to send the result data to
	 * @return An intent with the proper extras attached
	 */
	public static Intent getUpdateIntent(Model modelObj, Activity activity, RestResponseReceiver receiver){
		
		if(modelObj.getId() <= 0){
			throw new IllegalArgumentException("Model Id must be > 0 in order to update object.");
		}
		
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, activity, RestService.class);
		intent.putExtra(IntentExtraKeys.ACTION, Action.UPDATE);
		intent.putExtra(IntentExtraKeys.RECEIVER, receiver);
		
		intent.putExtra(IntentExtraKeys.MODEL_CLASS, modelObj.getClass());
		intent.putExtra(IntentExtraKeys.MODEL, (Parcelable) modelObj);
		intent.putExtra(IntentExtraKeys.MODEL_ID, modelObj.getId());
		
		
		
		return intent;
	}
	
	/**
	 * Creates an intent for the RestService when deleting a Model  
	 * @param modelId The id of the Model to delete
	 * @param modelClass The class of Model objects to read.
	 * @param activity An instance of the calling activity
	 * @param receiver The receiver to send the result data to
	 * @return An intent with the proper extras attached
	 */
	public static Intent getDeleteIntent(int modelId, Class<?> modelClass, Activity activity, RestResponseReceiver receiver){
		
		if(modelId < 1){
			throw new IllegalArgumentException("modelId must be > 0");
		}
		
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, activity, RestService.class);
		intent.putExtra(IntentExtraKeys.ACTION, RestService.Action.DELETE);
		intent.putExtra(IntentExtraKeys.RECEIVER, receiver);
		
		intent.putExtra(IntentExtraKeys.MODEL_CLASS, modelClass);
		intent.putExtra(IntentExtraKeys.MODEL_ID, modelId);
		
		return intent;
	}
	
	
	protected void onHandleIntent(Intent intent) {
		Log.d("yummy", "Handling JSON request intent...");
        
		// get data properties
		@SuppressWarnings("unchecked")
		Class<Model> modelType = (Class<Model>) intent.getSerializableExtra(IntentExtraKeys.MODEL_CLASS);
		Action action = (Action) intent.getSerializableExtra(IntentExtraKeys.ACTION);

		// build URL
		String requestUrl = buildUrl(modelType, action, intent);
		
		// create receiver and alert to progress
		final ResultReceiver receiver = intent.getParcelableExtra(IntentExtraKeys.RECEIVER);
        receiver.send(RestResultCode.RUNNING, Bundle.EMPTY);
        
        // initialize bundle to pass back to caller
        Bundle b = new Bundle();
       
        
        try {
        	
        	// get postData if action is a POST action
        	ArrayList<PostParameter> postParams = null;
        	if(actionMethodMapping.get(action) == "POST" && action != Action.DELETE && action != Action.CREATE_JSON)
        	{
        		Model modelObject = intent.getParcelableExtra(IntentExtraKeys.MODEL);
        		postParams = PostParameter.hashMapToNameValuePairs(modelObject.getPostData());
        	}        	
        	
        	
        	// log URL and post data
        	// TODO take this out for production? or use debug var to check
        	Log.v("yummy", "Making HTTP request to URL: " + requestUrl);
        	if(actionMethodMapping.get(action) == "POST" && action != Action.DELETE && action != Action.CREATE_JSON)
        	{
        		StringBuilder postData_logMsg = new StringBuilder();
        		postData_logMsg.append("POST data:\n");
        		for(PostParameter param : postParams){
        			postData_logMsg.append(param.getName() + ": " + param.getValue() + "\n");
        		}
        		Log.d("yummy", postData_logMsg.toString());
        	}
        	String jsonString = intent.getStringExtra(IntentExtraKeys.JSON_STRING);
        	if(actionMethodMapping.get(action) == "JSON")
        		Log.d("yummy", jsonString);
        	
        	// fire HTTP request and handle response
        	JSONObject json = jParser.makeHttpRequest(requestUrl, actionMethodMapping.get(action), postParams, jsonString);
        	Log.d("yummy", "JSON response: ".concat(json.toString()));

        	try {
        		// only continue on success
        		String success = json.getString("success");
        		b.putBoolean(IntentExtraKeys.SUCCESS, success.equals("true"));
        		
        		if(action == Action.READALL || action == Action.READSINGLE || action == Action.CREATE_JSON){
	        			if (success.equals("false"))
	            			throw new Exception();
	            		
        			
	        			if(!json.has("data")){
	        				throw new JSONException("JSON response badly formatted. No 'data' element.");
	        			}
			        			
	        			// create list to return via bundle
		        		ArrayList<Model> objectList = new ArrayList<Model>();
		        		int count = json.getInt("count");
		        		
		        		if(count == 1){
		        			// TODO problem here
		        			try{
		        				
		        				JSONObject obj_json = json.getJSONObject("data");
		        				Model object = (Model) modelType.newInstance();
			        			JSONObject jsonObject = obj_json.getJSONObject(object.getModelName());
			        			object.parseJson(jsonObject);
			        			objectList.add(object);
			        			
		        			}catch(JSONException e){
		        				// if JSONObject conversion fails, data may hold array of 1
		        				// so then we try the next if case
		        				count++;
		        			}
		        		}
		        		
		        		if(count > 1){
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
	        		}
        		
        		receiver.send(RestResultCode.FINISHED, b);
        		
        	} catch (JSONException e) {
        		e.printStackTrace();
        		receiver.send(RestResultCode.ERROR, b);
        	}

        } catch(Exception e) {
        	Log.e("yummy", e.toString());
        	b.putString(IntentExtraKeys.ERROR, e.getMessage());
        	receiver.send(RestResultCode.ERROR, b);
        }    
	}

}
