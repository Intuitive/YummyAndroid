package com.intuitive.yummy.activities;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;
import com.intuitive.yummy.R;
import com.intuitive.yummy.models.Order;
import com.intuitive.yummy.models.OrderItem;
import com.intuitive.yummy.models.OrderItemAdapter;
import com.intuitive.yummy.models.ReviewAdapter;
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
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;

import android.widget.AdapterView.OnItemClickListener;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

public class CartActivity extends Activity implements RestResponseReceiver.Receiver{

	//Dummy data
	//String[] addedItems = { "16 inch Cheese Pizza", "16 inch Pepperoni Pizza",
		//	"16 inch Sausage Pizza", "Cheese Pizza Slice" };
	//double[] price = { 10, 11, 12, 1.5 };

	
	ArrayList<OrderItem> orderItems;
	public RestResponseReceiver responseReceiver;
	private ListView listView;
	SQLiteDB cache = null;
	Double totalPrice = 0.0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);

		cache = new SQLiteDB(this);
		
		orderItems = (ArrayList<OrderItem>) cache.getAllOrderItems();
			
		final OrderItemAdapter adapter = new OrderItemAdapter(this, R.layout.list_order_item, orderItems);		
		listView = (ListView)findViewById(R.id.listOrderItem);
		listView.setAdapter(adapter);
		
		// --------------------------------
		// http://stackoverflow.com/questions/2558591/remove-listview-items-in-android
		listView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> a, View v, int position, long id){
				AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
				builder.setMessage("Remove this item from your order?");
				final int positionToRemove = position;
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						totalPrice -= orderItems.get(positionToRemove).getPrice();
						cache.deleteOrderItem(orderItems.get(positionToRemove));
						orderItems.remove(positionToRemove);
						adapter.notifyDataSetChanged();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {}
				});
				builder.show();
			}			
		});
		// --------------------------------		
		
		
		for( int i = 0; i < orderItems.size(); i ++ ){
			totalPrice += ( orderItems.get(i).getPrice() * orderItems.get(i).getQuantity() );
		}
		String total = NumberFormat.getCurrencyInstance().format(totalPrice); 
		((TextView)findViewById(R.id.total_price)).setText(total);
		cache.close();
		
		cache.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cart, menu);
		return true;
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
    	
    	
    	final Intent intent = new Intent(Intent.ACTION_SYNC, null, this, RestService.class);
		intent.putExtra(IntentExtraKeys.ACTION, Action.CREATE_JSON);
		intent.putExtra(IntentExtraKeys.RECEIVER, responseReceiver);
      	intent.putExtra(IntentExtraKeys.JSON_STRING, jsonString);
      	intent.putExtra(IntentExtraKeys.MODEL_CLASS, Order.class);
    	startService(intent);
    	
        
        //Intent intent = new Intent(this, OrderConfirmationActivity.class);
        //startActivity(intent);
    }

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch (resultCode) {
	    
		case RestResultCode.RUNNING:
	        // TODO show progress
	        break;
	        
	    case RestResultCode.FINISHED:
	    	
	    	//orderItems = resultData.getParcelableArrayList(RestService.BundleObjectKey);
	    
	    	/*OrderItemAdapter adapter = new OrderItemAdapter(this, R.layout.list_order_item, orderItems);
	    	listView = (ListView)findViewById(R.id.listOrderItem);
	    	listView.setAdapter(adapter);
	    	*/
	    	// clean out cart
	    	SQLiteDB cache = new SQLiteDB(this);
	    	cache.deleteAllOrderItems();
	    	cache.close();	    	   	
	    	
	    	// get order and pass it to OrderConfirmation
	    	ArrayList<Order> orders = resultData.getParcelableArrayList(RestService.BundleObjectKey);
	    	Intent intent = new Intent(this, OrderConfirmationActivity.class);
	    	intent.putExtra(IntentExtraKeys.MODEL_ID, orders.get(0).getId());
	    	intent.putExtra("waitTime", orders.get(0).getWaitTime());
        	startActivity(intent);

	        // TODO hide progress
	        break;
	    case RestResultCode.ERROR:
	        	//RestResultCode.ERROR.getValue()
	        break;
		}
	}
}
