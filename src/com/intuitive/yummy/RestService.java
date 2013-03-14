package com.intuitive.yummy;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.Log;

public class RestService extends IntentService {
	
	public RestService(){
		super("RestAPI service");
		Log.d("yummy", "RestService started");
	}
	
	public RestService(String name) {
		super(name);
		Log.d("yummy", "RestService started");
	}
	
	JSONParser jParser = new JSONParser();
	String vendorUrl = "http://yummy.edgarpaz.com/cakephp/vendors";
	JSONArray vendors = null;
	ArrayList<HashMap<String, String>> vendorsList =  new ArrayList<HashMap<String, String>>();
	
	protected void onHandleIntent(Intent intent) {
		Log.d("yummy", "Handling JSON request intent...");
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle b = new Bundle();
        
        receiver.send(RestResultCode.RUNNING.getValue(), Bundle.EMPTY);
        try {
        	// getting JSON string from URL
        	Log.d("yummy", "Making HTTP request...");
        	JSONObject json = jParser.makeHttpRequest(vendorUrl, "GET", null);

        	// Check your log cat for JSON reponse
        	Log.d("yummy json response: ", json.toString());

        	try {
        		// Checking for SUCCESS TAG
        		b.putString("response", json.toString());
        		
        		Boolean success = json.getBoolean("success");
        		if (success) {
        			receiver.send(RestResultCode.FINISHED.getValue(), b);
        		} else {
        			receiver.send(RestResultCode.ERROR.getValue(), b);
        		}
        		
        	} catch (JSONException e) {
        		e.printStackTrace();
        	}

        } catch(Exception e) {
        	b.putString(Intent.EXTRA_TEXT, e.toString());
        	receiver.send(RestResultCode.ERROR.getValue(), b);
        }    
	}

}
