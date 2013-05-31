package com.intuitive.yummy.activities;

import java.text.NumberFormat;
import java.util.ArrayList;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.id;
import com.intuitive.yummy.R.layout;
import com.intuitive.yummy.R.menu;
import com.intuitive.yummy.models.OrderItem;
import com.intuitive.yummy.models.Vendor;
import com.intuitive.yummy.webservices.IntentExtraKeys;
import com.intuitive.yummy.webservices.RestResponseReceiver;
import com.intuitive.yummy.webservices.RestResultCode;
import com.intuitive.yummy.webservices.RestService;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.view.Menu;
import android.view.View;

public class OrderDetailActivity extends Activity implements RestResponseReceiver.Receiver{

	public RestResponseReceiver responseReceiver;
	public ArrayList<OrderItem> orderItems;
	
	TableLayout orderItemsTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);

		// grab order id from intent
		Intent intent = getIntent();
		String orderId = intent.getStringExtra(IntentExtraKeys.MODEL_ID);
		
		// request order items from db
		responseReceiver = new RestResponseReceiver(new Handler());
        responseReceiver.setReceiver(this);
        
		final Intent getOrderItemsIntent = RestService.getReadManyByParamIntent(OrderItem.class, orderId, this, responseReceiver);
		startService(getOrderItemsIntent);
		//adding cell to each row with dummy data
		/*
		t1 = (TableLayout) findViewById(R.id.table1);
		for (int i = 0; i < orderedItems.length; i++) {
			TableRow tr = new TableRow(this);
			TextView tv1 = new TextView(this);
			TextView tv2 = new TextView(this);
			TextView tv3 = new TextView(this);
			//user defined function
			createView(tr, tv1, Integer.toString(1));
			createView(tr, tv2, orderedItems[i]);
			createView(tr, tv3, "");
			//add row to table
			t1.addView(tr);
		}
		*/
		
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
    
    public void toggleOrderReady(View v){
    	// send to server that order is ready
    	Toast.makeText(getApplicationContext(), "Order is ready", Toast.LENGTH_LONG).show();
    }

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch (resultCode) {
	    
		case RestResultCode.RUNNING:
	        // TODO show progress
	        break;
	        
	    case RestResultCode.FINISHED:
	    
	    	orderItems = resultData.getParcelableArrayList(RestService.BundleObjectKey);
	    	
	    	// add order items to table
	    	Double totalPrice = 0.0;
	    	orderItemsTable = (TableLayout) findViewById(R.id.table1);
			for (int i = 0; i < orderItems.size(); i++) {
				// create order item row
				TableRow orderItemRow = new TableRow(this);
				TextView quantity = new TextView(this);
				TextView orderItemName = new TextView(this);
				TextView specialInstructions = new TextView(this);
				createView(orderItemRow, quantity, String.valueOf(orderItems.get(i).getQuantity()));
				createView(orderItemRow, orderItemName, orderItems.get(i).getName());
				createView(orderItemRow, specialInstructions, orderItems.get(i).getSpecialInstructions());
				
				// add to table
				orderItemsTable.addView(orderItemRow);
				
				// add up total price
				totalPrice += orderItems.get(i).getPrice();
			}
	        
			// add last row for total price
			TableRow totalPriceRow = new TableRow(this);
			TextView totalLabel = new TextView(this);
			TextView totalPrice_v = new TextView(this);
			TextView emptyRightCell = new TextView(this);
			createView(totalPriceRow, totalLabel, "Total:");
			createView(totalPriceRow, totalPrice_v, NumberFormat.getCurrencyInstance().format(totalPrice));
			createView(totalPriceRow, emptyRightCell, "   ");
			orderItemsTable.addView(totalPriceRow);
	    	
	        // TODO hide progress
	        break;
	    case RestResultCode.ERROR:
	        	//RestResultCode.ERROR.getValue()
	        break;
	}
		
	}
}
