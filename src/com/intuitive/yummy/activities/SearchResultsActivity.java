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
import com.intuitive.yummy.models.MenuItem;
import com.intuitive.yummy.models.Vendor;
import com.intuitive.yummy.webservices.*;

public class SearchResultsActivity extends ListActivity implements RestResponseReceiver.Receiver{

	public RestResponseReceiver mReceiver;
	ArrayList<Vendor> vendors;
	// dummy data for vendors
	private com.intuitive.yummy.models.Menu menu = new com.intuitive.yummy.models.Menu();
	private int[][] hours = new int[][] {{830,1700}, {830,1700}, {830,1700}, {830,1700}, {830,1700}, {0,0}, {0,0}};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// Get the list of vendor and create a array of vendors by name only
    	// then pass it to the list adapter to display it as a list in the app
    	
    	menu.addMenuItem(new MenuItem(1, "16 inch Cheese Pizza", 10, "Pizza", "Plain Cheese Pizza", true, null, null));
    	menu.addMenuItem(new MenuItem(2, "16 inch Pepperoni Pizza", 11, "Pizza", "Pizza with Pepperoni Topping", true, null, null));
    	menu.addMenuItem(new MenuItem(3, "16 inch Sausage Pizza", 12, "Pizza", "Pizza with Sausage", true, null, null));
    	menu.addMenuItem(new MenuItem(4, "Cheese Pizza Slice", 1.5, "Pizza", "Plain Cheese Pizza (slice)", true, null, null));
    	menu.addMenuItem(new MenuItem(5, "Pepperoni Pizza Slice", 1.65, "Pizza", "Pizza with Pepperoni Topping (slice)", true, null, null));
    	menu.addMenuItem(new MenuItem(6, "Sausage Pizza Slice", 1.75, "Pizza", "Pizza with Sausage (slice)", true, null, null));
    	menu.addMenuItem(new MenuItem(7, "Pizza Cheesesteak", 5, "Cheesesteak", "Cheesesteak filled with pizza topping", true, null, null));
    	menu.addMenuItem(new MenuItem(8, "Chicken Cheesesteak", 6, "Cheesesteak", "Cheesesteak filled with chicken", true, null, null));
    	menu.addMenuItem(new MenuItem(9, "Pepsi 2 Liter", 2.5, "Drink", "2 Liter Pepsi", true, null, null));
    	menu.addMenuItem(new MenuItem(10, "Coca-Cola 2 Liter", 2.5, "Drink", "2 Liter Coca-Cola", true, null, null));
    		
        
    	super.onCreate(savedInstanceState);

        mReceiver = new RestResponseReceiver(new Handler());
        mReceiver.setReceiver(this);
         
        final Intent apiIntent = new Intent(Intent.ACTION_SYNC, null, this, RestService.class);
        apiIntent.putExtra(IntentExtraKeys.RECEIVER, mReceiver);
        apiIntent.putExtra(IntentExtraKeys.MODEL_TYPE, Vendor.class);
        apiIntent.putExtra(IntentExtraKeys.ACTION, RestService.Action.READALL);
        
        startService(apiIntent);
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
        
    	final int  running = 0;
    	final int finished = 1;
    	final int error = 2;
    	
    	switch (resultCode) {
		    
			case running:
		        // TODO show progress
		        break;
		        
		    case finished:
		    	
		    	vendors = objectData.getParcelableArrayList(RestService.BundleObjectKey);
		    	
		    	// update UI
		    	ArrayAdapter<Vendor> adapter = new ArrayAdapter<Vendor>(this,
		    			android.R.layout.simple_list_item_1, vendors);

		    	setListAdapter(adapter);
		        
		        
		        // TODO hide progress
		        break;
		    case error:
		        // TODO handle the error;
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
    	intent.putExtra("VendorID", vendor.getID());
    	startActivity(intent);    	
    }
    
    
    
}
