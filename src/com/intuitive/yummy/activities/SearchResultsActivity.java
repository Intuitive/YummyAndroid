package com.intuitive.yummy.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.intuitive.yummy.R;
import com.intuitive.yummy.models.*;

import com.intuitive.yummy.webservices.*;

public class SearchResultsActivity extends ListActivity implements RestResponseReceiver.Receiver{

	public RestResponseReceiver mReceiver;
	ArrayList<Vendor> vendors;
	// dummy data for vendors
	private com.intuitive.yummy.models.Menu menu = new com.intuitive.yummy.models.Menu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// Get the list of vendor and create a array of vendors by name only
    	// then pass it to the list adapter to display it as a list in the app
    	
    	/*
    	menu.addMenuItem(new MenuItem(1, 1, "16 inch Cheese Pizza", 10, "Pizza", "Plain Cheese Pizza", true, null));
    	menu.addMenuItem(new MenuItem(2, 1, "16 inch Pepperoni Pizza", 11, "Pizza", "Pizza with Pepperoni Topping", true, null));
    	menu.addMenuItem(new MenuItem(3, 1, "16 inch Sausage Pizza", 12, "Pizza", "Pizza with Sausage", true, null));
    	menu.addMenuItem(new MenuItem(4, 1, "Cheese Pizza Slice", 1.5, "Pizza", "Plain Cheese Pizza (slice)", true, null));
    	menu.addMenuItem(new MenuItem(5, 1, "Pepperoni Pizza Slice", 1.65, "Pizza", "Pizza with Pepperoni Topping (slice)", true, null));
    	menu.addMenuItem(new MenuItem(6, 1, "Sausage Pizza Slice", 1.75, "Pizza", "Pizza with Sausage (slice)", true, null));
    	menu.addMenuItem(new MenuItem(7, 1, "Pizza Cheesesteak", 5, "Cheesesteak", "Cheesesteak filled with pizza topping", true, null));
    	menu.addMenuItem(new MenuItem(8, 1, "Chicken Cheesesteak", 6, "Cheesesteak", "Cheesesteak filled with chicken", true, null));
    	menu.addMenuItem(new MenuItem(9, 1, "Pepsi 2 Liter", 2.5, "Drink", "2 Liter Pepsi", true, null));
    	menu.addMenuItem(new MenuItem(10, 1, "Coca-Cola 2 Liter", 2.5, "Drink", "2 Liter Coca-Cola", true, null));
    	*/	
        
    	super.onCreate(savedInstanceState);

        mReceiver = new RestResponseReceiver(new Handler());
        mReceiver.setReceiver(this);
        
        // test read all
        final Intent intent = RestService.getReadManyIntent(Vendor.class, this, mReceiver);
          
        // test read single
        //Intent intent = RestService.getReadByIdIntent(1, MenuItem.class, this, mReceiver);
          
        // test create
        
        
//        Order order = new Order();
//        order.setId(2);
//        order.setPaymentMethod(0);
//        order.setTotalPrice(2.0);
//        order.setWaitTime(10);
//        order.setStatus(OrderStatus.IN_PROGRESS);
//        order.setUserId(1);
//        order.setVendorId(25);
//        
//        Intent intent = RestService.getCreateIntent(order, this, mReceiver);
//             
        // test delete
        //Intent intent = RestService.getUpdateIntent(order, this, mReceiver);
        
        startService(intent);
        Log.d("yummy", "Starting up REST service...");
        
    }
    
    @Override
    public void onResume() {
    	mReceiver.setReceiver(this);
    	super.onResume();
    }
    
    @Override
    public void onPause() {
        mReceiver.setReceiver(null); // clear receiver so no leaks.
        super.onPause();
    }
    
    
    public void onReceiveResult(int resultCode, Bundle objectData) {
    	final int running = 0;
    	final int finished = 1;
    	final int error = 2;
    	
    	switch (resultCode) {
		    
			case running:
		        // TODO show progress
		        break;
		        
		    case finished:
		    
		    	// to test reads
		    	vendors = objectData.getParcelableArrayList(RestService.BundleObjectKey);
		    	ArrayList<Order> orders = objectData.getParcelableArrayList(RestService.BundleObjectKey);
		    	
		    	// to test others

//		    	String success = objectData.getBoolean(IntentExtraKeys.SUCCESS) ? "true" : "false";
//		    	Vendor v = new Vendor();
//		    	v.setName(success);
//		    	ArrayList<Vendor> vendors = new ArrayList<Vendor>();
//		    	vendors.add(v);

		    	// update UI
		    	ArrayAdapter<Vendor> adapter = new ArrayAdapter<Vendor>(this,
		    			android.R.layout.simple_list_item_1, vendors);

		    	setListAdapter(adapter);
		        
		        // TODO hide progress
		        break;
		    case error:
		        	//RestResultCode.ERROR.getValue()
		        break;
		}
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search_results, menu);
        return true;
    }
    
    //get the correspond vendor and pass it to the VendorActivity
    protected void onListItemClick(ListView lv, View v, int position, long id){
    	Vendor vendor = vendors.get(position);
    	Intent intent = new Intent(this, VendorActivity.class);
    	
    	intent.putExtra("Vendor", (Parcelable) vendor);
    	intent.putExtra("VendorID", vendor.getId());
    	startActivity(intent);    	
    }
    
    
    
}
