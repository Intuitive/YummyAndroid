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
import android.util.Log;

public class RestService extends IntentService {
	
	public RestService(String name) {
		
		super(name);
		// TODO Auto-generated constructor stub
		Log.d("yummy", "RestService constructed");
	}
	
	JSONParser jParser = new JSONParser();
	String vendorUrl = "http://yummy.edgarpaz.com/cakephp/vendors";
	JSONArray vendors = null;
	ArrayList<HashMap<String, String>> vendorsList =  new ArrayList<HashMap<String, String>>();
	
	protected void onHandleIntent(Intent intent) {
		Log.d("yummy", "Handling JSON request intent...");
        final RestResponseReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle b = new Bundle();
        
        receiver.send(RestResultCode.RUNNING.getValue(), Bundle.EMPTY);
        try {
        	// getting JSON string from URL
        	JSONObject json = jParser.makeHttpRequest(vendorUrl, "GET", null);

        	// Check your log cat for JSON reponse
        	Log.d("yummy json response: ", json.toString());

        	try {
        		// Checking for SUCCESS TAG
        		int success = json.getInt("success");

        		if (success == 1) {
        			// products found
        			b.putString("response", json.toString());
        			receiver.send(RestResultCode.FINISHED.getValue(), b);

        		} else {
        			// no products found
        			b.putString("response", json.toString());
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
