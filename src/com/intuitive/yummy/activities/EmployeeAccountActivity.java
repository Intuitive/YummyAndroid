package com.intuitive.yummy.activities;

import com.intuitive.yummy.R;
import com.intuitive.yummy.models.User;
import com.intuitive.yummy.models.User.UserType;
import com.intuitive.yummy.models.Vendor.VendorStatus;
import com.intuitive.yummy.models.Vendor;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EmployeeAccountActivity extends Activity {
	private User user = new User("nikb248", "Marco", "Polo", "nik@gmail.com", "1234567890");

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_account_main);
		
		//Intent intent = getIntent();
		//if( intent.getStringExtra("Activity").equals("Login"))
			//user = (User)intent.getSerializableExtra("User");
		
		TextView firstName = (TextView)findViewById(R.id.customer_firstname);
		TextView lastName = (TextView)findViewById(R.id.customer_lastname);
		firstName.setText(user.getFirstName());
		lastName.setText(user.getLastName());
	}
	
	//Go to home page when home button is clicked
    public void home(View v){
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
    
    //Go to cart page when cart button is clicked
    public void cart(View v){
    	Intent intent = new Intent(this, CartActivity.class);
    	startActivity(intent);
    }
}
