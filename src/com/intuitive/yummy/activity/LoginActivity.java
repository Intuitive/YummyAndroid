package com.intuitive.yummy.activity;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.id;
import com.intuitive.yummy.R.layout;
import com.intuitive.yummy.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private String user = "username";
	private String pass = "password";
	Button cancel;
	EditText userName, passWord;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    public void checkUser(View v) {
    	String enteredUsername = ((EditText)findViewById(R.id.username)).getText().toString();
    	String enteredPassword = ((EditText)findViewById(R.id.password)).getText().toString();
    	if (enteredUsername.equals(user) && enteredPassword.equals(pass)) {
        	Intent intent = new Intent(this, VendorAdminAccountActivity.class);
        	intent.putExtra("Activity", "Login");
        	startActivity(intent);
    	} else {
    		Toast.makeText(getApplicationContext(), "Username/Password incorrect.", Toast.LENGTH_SHORT).show();
    		clearEditText();
    	}
    }
    
    public void register(View v){
    	Intent intent = new Intent(this, RegisterActivity.class);
    	startActivity(intent);
    }
    
   public void clearEditText() {
    	userName.setText("");
    	passWord.setText("");
    }
}
