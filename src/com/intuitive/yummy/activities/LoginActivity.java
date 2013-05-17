package com.intuitive.yummy.activities;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.id;
import com.intuitive.yummy.R.layout;
import com.intuitive.yummy.R.menu;
import com.intuitive.yummy.models.User;
import com.intuitive.yummy.models.Vendor;
import com.intuitive.yummy.webservices.RestResponseReceiver;
import com.intuitive.yummy.webservices.RestService;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements RestResponseReceiver.Receiver{
	private String customerUser = "customer";
	private String employeeUser = "employee";
	private String adminUser = "admin";
	private String pass = "password";
	
	Button cancel;
	EditText userName, passWord;
	public RestResponseReceiver mReceiver;
	public User u;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cancel = (Button)findViewById(R.id.button_cancel);
        userName = (EditText)findViewById(R.id.username);
        passWord = (EditText)findViewById(R.id.password);

        cancel.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
        
        super.onCreate(savedInstanceState);
        mReceiver = new RestResponseReceiver(new Handler());
        mReceiver.setReceiver(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    public void checkUser(View v) {
    	String enteredUsername = ((EditText)findViewById(R.id.username)).getText().toString();
    	String enteredPassword = ((EditText)findViewById(R.id.password)).getText().toString();
    	// TODO load user info from json
    	// check userType to determine screen to load next
    	if (enteredUsername.equals(adminUser) && enteredPassword.equals(pass)) {
        	Intent intent = new Intent(this, VendorAdminAccountActivity.class);
        	intent.putExtra("Activity", "Login");
        	startActivity(intent);
    	} else if(enteredUsername.equals(employeeUser) && enteredPassword.equals(pass)){
    		Intent intent = new Intent(this, VendorAdminAccountActivity.class);
    		intent.putExtra("Activity", "Login");
    		startActivity(intent);
    	} else if(enteredUsername.equals(customerUser) && enteredPassword.equals(pass)){
    		//Toast.makeText(getApplicationContext(), "hi customer", Toast.LENGTH_SHORT).show();
    		Intent intent = new Intent(this, CustomerAccountActivity.class);
    		intent.putExtra("Activity", "Login");
    		startActivity(intent);
    	} else {
    		Toast.makeText(getApplicationContext(), "Username/Password incorrect.", Toast.LENGTH_SHORT).show();
    		clearEditText();
    	}

    }
    
    public void goToRegister(View v){
    	Intent intent = new Intent(this, RegisterActivity.class);
    	startActivity(intent);
    }
    
    public void clearEditText() {
    	userName.setText("");
    	passWord.setText("");
    }
    
    private void verifyPassword(String username){
    	// TODO grab user info from json and check for matching username/password
    }

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		// TODO Auto-generated method stub
		
	}
}
