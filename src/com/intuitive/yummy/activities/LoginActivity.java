package com.intuitive.yummy.activities;

import java.util.ArrayList;

import com.intuitive.yummy.R;
import com.intuitive.yummy.models.User;
import com.intuitive.yummy.models.User.UserAccountType;
import com.intuitive.yummy.webservices.IntentExtraKeys;
import com.intuitive.yummy.webservices.RestResponseReceiver;
import com.intuitive.yummy.webservices.RestResultCode;
import com.intuitive.yummy.webservices.RestService;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements RestResponseReceiver.Receiver{
	
	Button cancel;
	public RestResponseReceiver responseReceiver;
	public User u;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ((Button)findViewById(R.id.button_cancel)).setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
        
        super.onCreate(savedInstanceState);
        responseReceiver = new RestResponseReceiver(new Handler());
        responseReceiver.setReceiver(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    public void checkUser(View v) {
    	
    	String enteredUsername = ((EditText)findViewById(R.id.username)).getText().toString();
    	
    	final Intent getUserIntent = User.getUsersIntent(null, enteredUsername, this, responseReceiver);
    	startService(getUserIntent);
    }
    
    public void goToRegister(View v){
    	Intent intent = new Intent(this, RegisterActivity.class);
    	startActivity(intent);
    	
    	// execution continues asynchronously at onReceiveResult()
    }
    
    public void clearEditText() {
    	((EditText)findViewById(R.id.username)).setText("");
    	((EditText)findViewById(R.id.password)).setText("");
    }
    
    @SuppressWarnings("unused")
	private void verifyPassword(String username){
    	// TODO grab user info from json and check for matching username/password
    }

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch (resultCode) {
	    
		case RestResultCode.RUNNING:
	        // TODO show progress
	        break;
	        
	    case RestResultCode.FINISHED:	    
	    	
	    	// Only one user should be returned. We're just checking wether the username exists
	    	ArrayList<User> userSet = resultData.getParcelableArrayList(RestService.BundleObjectKey);
	    	if(userSet.size() == 0){
	    		Toast.makeText(getApplicationContext(), "Username/Password incorrect.", Toast.LENGTH_SHORT).show();
	    		clearEditText();
	    		return;
	    	}
	    	
	    	// then we check the User's account type and route the intent appropriately
	    	Intent accountActivityIntent = new Intent();
	    	if (userSet.get(0).getAccountType() == UserAccountType.VENDOR_ADMIN) {
	    		accountActivityIntent = new Intent(this, VendorAdminAccountActivity.class);
	    		accountActivityIntent.putExtra(IntentExtraKeys.MODEL_ID, userSet.get(0).getVendorId());
	    		accountActivityIntent.putExtra(IntentExtraKeys.PARAMETER1, true);
	        	
	        }else if(userSet.get(0).getAccountType() == UserAccountType.VENDOR_EMPLOYEE){
	        	accountActivityIntent = new Intent(this, VendorAdminAccountActivity.class);
	        	accountActivityIntent.putExtra(IntentExtraKeys.MODEL_ID, userSet.get(0).getVendorId());
	        	accountActivityIntent.putExtra(IntentExtraKeys.PARAMETER1, false);
	    	} else if(userSet.get(0).getAccountType() == UserAccountType.CUSTOMER){
	    		accountActivityIntent = new Intent(this, CustomerAccountActivity.class);
	    		accountActivityIntent.putExtra(IntentExtraKeys.MODEL, (Parcelable) userSet.get(0));
	    	}
	    	
	    	startActivity(accountActivityIntent);

	        // TODO hide progress
	        break;
	    case RestResultCode.ERROR:
	        	//RestResultCode.ERROR.getValue()
	        break;
	}
		
	}
}
