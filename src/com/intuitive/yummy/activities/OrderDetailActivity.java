package com.intuitive.yummy.activities;

import java.text.NumberFormat;
import java.util.ArrayList;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.id;
import com.intuitive.yummy.R.layout;
import com.intuitive.yummy.R.menu;
import com.intuitive.yummy.models.Order;
import com.intuitive.yummy.models.Order.OrderStatus;
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
import android.widget.ToggleButton;
import android.view.Menu;
import android.view.View;

public class OrderDetailActivity extends Activity implements RestResponseReceiver.Receiver{

	public String orderId = null;
	public String vendorId = null;
	public RestResponseReceiver responseReceiver;
	public ArrayList<OrderItem> orderItems;
	
	public static int ORDER_ID_TAG = 1;
	public static int VENDOR_ID_TAG = 2;
	
	TableLayout orderItemsTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);

		// grab order id from intent
		Intent intent = getIntent();
		orderId = intent.getStringExtra(IntentExtraKeys.MODEL_ID);
		vendorId = intent.getStringExtra("vendorId");
		
		// request order items from db
		responseReceiver = new RestResponseReceiver(new Handler());
        responseReceiver.setReceiver(this);
        
		final Intent getOrderItemsIntent = RestService.getReadManyByParamIntent(OrderItem.class, orderId, this, responseReceiver);
		startService(getOrderItemsIntent);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cart, menu);
		return true;
	}

	//Function to add each cell to row
	public void createCell(TableRow tr, TextView t, String viewdata) {
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
    	// just update the order status
    	Order order = new Order();
    	order.setId(Integer.parseInt(orderId));
    	order.setStatus(OrderStatus.FULFILLED);
    	
    	final Intent getOrderItemsIntent = RestService.getUpdateIntent(order, this, responseReceiver);
		startService(getOrderItemsIntent);    	
    }

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch (resultCode) {
		    
			case RestResultCode.RUNNING:
		        // TODO show progress
		        break;
		        
		    case RestResultCode.FINISHED:
		    
		    	orderItems = resultData.getParcelableArrayList(RestService.BundleObjectKey);
		    	
		    	if(orderItems != null){
			    	// add order items to table
			    	Double totalPrice = 0.0;
			    	orderItemsTable = (TableLayout) findViewById(R.id.table1);
					for (int i = 0; i < orderItems.size(); i++) {
						// create order item row
						TableRow orderItemRow = new TableRow(this);
						TextView quantity = new TextView(this);
						TextView orderItemName = new TextView(this);
						TextView specialInstructions = new TextView(this);
						createCell(orderItemRow, quantity, String.valueOf(orderItems.get(i).getQuantity()));
						createCell(orderItemRow, orderItemName, orderItems.get(i).getName());
						createCell(orderItemRow, specialInstructions, orderItems.get(i).getSpecialInstructions());
						
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
					createCell(totalPriceRow, totalLabel, "Total:");
					createCell(totalPriceRow, totalPrice_v, NumberFormat.getCurrencyInstance().format(totalPrice));
					createCell(totalPriceRow, emptyRightCell, "   ");
					orderItemsTable.addView(totalPriceRow);
		    	}else{
		    		Toast.makeText(getApplicationContext(), "Order is ready", Toast.LENGTH_LONG).show();
		    		
		    		// disable button (once an order is fullfilled it cannot be "unfullfilled"
		    		ToggleButton fullfillToggle = (ToggleButton) findViewById(R.id.order_ready_button);
		    		fullfillToggle.setEnabled(false);
		    		fullfillToggle.setFocusable(false);
		    	}
		        // TODO hide progress
		        break;
		    case RestResultCode.ERROR:
		        	//RestResultCode.ERROR.getValue()
		        break;
		}
	}
	
	@Override
	public void onBackPressed() {
		// we need to re-create the pending orders activity in order to refresh
		Intent intent = new Intent(this, PendingOrdersActivity.class);
		intent.putExtra(IntentExtraKeys.MODEL_ID, Integer.valueOf(vendorId));
		startActivity(intent);
	    finish();
	 }
}
