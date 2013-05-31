package com.intuitive.yummy.activities;

import com.intuitive.yummy.R;
import com.intuitive.yummy.webservices.IntentExtraKeys;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;

import android.widget.TextView;

public class OrderConfirmationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_confirmation);
		
		Intent intent = this.getIntent();
		Integer orderId = intent.getIntExtra(IntentExtraKeys.MODEL_ID, -1);
		Integer waitTime = intent.getIntExtra("waitTime", -1);
		
		//find the textfield to be used in xml
		TextView orderNumber = (TextView) findViewById(R.id.orderNumber);
		TextView waitTime_tv = (TextView) findViewById(R.id.waitTime);
		
		//show order number on the app
		orderNumber.setText(String.valueOf(orderId));
		waitTime_tv.setText(String.valueOf(waitTime) + " minutes");
		
		// set to bold
		orderNumber.setTypeface(null, Typeface.BOLD);
		waitTime_tv.setTypeface(null, Typeface.BOLD);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_order_confirmation, menu);
		return true;
	}
	
	//Go to home page when home button is clicked
    public void home(View v){
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }

}
