package com.intuitive.yummy.activities;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;
import com.intuitive.yummy.R;
import com.intuitive.yummy.models.Order;
import com.intuitive.yummy.models.OrderItem;
import com.intuitive.yummy.models.Vendor;
import com.intuitive.yummy.sqlitedb.SQLiteDB;
import com.intuitive.yummy.webservices.IntentExtraKeys;
import com.intuitive.yummy.webservices.RestResponseReceiver;
import com.intuitive.yummy.webservices.RestResultCode;
import com.intuitive.yummy.webservices.RestService;
import com.intuitive.yummy.webservices.RestService.Action;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.view.Menu;
import android.view.View;

public class CartActivity extends Activity implements RestResponseReceiver.Receiver{

	//Dummy data
	//String[] addedItems = { "16 inch Cheese Pizza", "16 inch Pepperoni Pizza",
		//	"16 inch Sausage Pizza", "Cheese Pizza Slice" };
	//double[] price = { 10, 11, 12, 1.5 };

	List<OrderItem> orderItems;
	public RestResponseReceiver responseReceiver;
	TableLayout t1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);

		SQLiteDB cache = new SQLiteDB(this);
		Double totalPrice = 0.0;
		t1 = (TableLayout) findViewById(R.id.table1);
		
		
		orderItems = cache.getAllOrderItems();
		
		for (OrderItem orderItem : orderItems) {
			TableRow tr = new TableRow(this);
			TextView tv1 = new TextView(this);
			TextView tv2 = new TextView(this);
			TextView tv3 = new TextView(this);
			//user defined function
			
			// order: quantity, item name, price
			createView(tr, tv1, String.valueOf(orderItem.getQuantity()));
			createView(tr, tv2, orderItem.getName());
			String price = NumberFormat.getCurrencyInstance().format(orderItem.getPrice());
			createView(tr, tv3, price);
			
			//add row to table
			t1.addView(tr);
			
			// keep track of totalPrice
			totalPrice += orderItem.getPrice();
	    }
		
		//adding the last row for the total amount using dummy data

		TableRow tr2 = new TableRow(this);
		TextView tv4 = new TextView(this);
		TextView tv5 = new TextView(this);
		TextView tv6 = new TextView(this);
		createView(tr2, tv4, "Total:");
		createView(tr2, tv5, "   ");
		createView(tr2, tv6, NumberFormat.getCurrencyInstance().format(totalPrice));
		t1.addView(tr2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cart, menu);
		return true;
	}

	//Function to add each cell to row
	public void createView(TableRow tr, TextView t, String viewdata) {
		//content of each cell
		t.setText(viewdata);
		t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		t.setTextColor(Color.WHITE);
		t.setBackgroundColor(Color.BLACK);
		//creating borders
		t.setPadding(20, 0, 0, 0);
		tr.setPadding(0, 1, 0, 1);
		tr.setBackgroundColor(Color.WHITE);
		//add textview to row
		tr.addView(t);
	}
	
	//Go to home page when home button is clicked
    public void goToHome(View v){
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
    
    //Go to checkout page when checkout button is clicked
    public void goToCheckout(View v){
    	// send order to server
    	Gson gson = new Gson();
    	String jsonString = gson.toJson(orderItems);
    	
    	
    	responseReceiver = new RestResponseReceiver(new Handler());
        responseReceiver.setReceiver(this);
    	
    	/*
    	final Intent intent = new Intent(Intent.ACTION_SYNC, null, this, RestService.class);
		intent.putExtra(IntentExtraKeys.ACTION, Action.CREATE_JSON);
		intent.putExtra(IntentExtraKeys.RECEIVER, responseReceiver);
      	intent.putExtra(IntentExtraKeys.JSON_STRING, jsonString);
      	intent.putExtra(IntentExtraKeys.MODEL_CLASS, Order.class);
    	startService(intent);
    	*/
        
        Intent intent = new Intent(this, OrderConfirmationActivity.class);
        startActivity(intent);
    }

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch (resultCode) {
	    
		case RestResultCode.RUNNING:
	        // TODO show progress
	        break;
	        
	    case RestResultCode.FINISHED:
	    	
	    	
	    	ArrayList<Order> orders = resultData.getParcelableArrayList(RestService.BundleObjectKey);
	    	
	    	Intent intent = new Intent(this, OrderConfirmationActivity.class);
	    	intent.putExtra(IntentExtraKeys.MODEL, (Parcelable) orders.get(0));
        	startActivity(intent);

	        
	        // TODO hide progress
	        break;
	    case RestResultCode.ERROR:
	        	//RestResultCode.ERROR.getValue()
	        break;
	}
		
	}
}
