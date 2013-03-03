package com.intuitive.yummy;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class OrderConfirmationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_confirmation);
		
		//random number generator just for dummy data
		Random generator = new Random();
		
		//find the textfield to be used in xml
		TextView number = (TextView) findViewById(R.id.orderNumber);
		TextView minutes = (TextView) findViewById(R.id.waitTime);
		
		//show order number on the app
		number.setText(Integer.toString(generator.nextInt(100)));
		minutes.setText(Integer.toString(generator.nextInt(60)) + " minutes");
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
    
    //Go to cart page when cart button is clicked
    public void cart(View v){
    	Intent intent = new Intent(this, CartActivity.class);
    	startActivity(intent);
    }

}
