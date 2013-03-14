package com.intuitive.yummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchResultsActivity extends ListActivity implements RestResponseReceiver.Receiver{

	public RestResponseReceiver mReceiver;
	private String [] values = new String[7];
	private ArrayList<Vendor> vendors = new ArrayList<Vendor>();
	private com.intuitive.yummy.Menu menu = new com.intuitive.yummy.Menu();
	//dummy data for vendors
	/*
	private Vendor[] vendors = {
			new Vendor(1, "Jack's Pizza", "We sell Pizzas!", "", new int[][] {{830,1700}, {830,1700}, {830,1700}, {830,1700}, {830,1700}, {0,0}, {0,0}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>())),
			new Vendor(2, "Sally's Subs", "Welcome to Sally's Subs", "",  new int[][] {{830,1800}, {830,1800}, {830,1800}, {830,1800}, {830,1800}, {0,0}, {0,0}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>())),
			new Vendor(3, "Bob's Burritos", "Hello from Bob's Burritos", "",  new int[][] {{900,1700}, {900,1700}, {900,1700}, {900,1700}, {900,1700}, {900,1900}, {900,1900}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>())),
			new Vendor(4, "Larry's Lasagna", "Welcome to Bob's Burritos", "",  new int[][] {{800,1700}, {800,1700}, {800,1700}, {800,1700}, {800,1700}, {800,2000}, {800,2000}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>())),
			new Vendor(5, "Helga's Hell Kitchen", "Hello from Helga's Hell Kitchen", "",  new int[][] {{830,1700}, {830,1700}, {830,1700}, {830,1700}, {830,1700}, {900,1800}, {0,0}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>())),
			new Vendor(6, "Carlos' Cuisine", "Welcome to Carlos' Cuisine", "",  new int[][] {{0,0}, {830,1700}, {830,1700}, {830,1700}, {830,1700}, {830,1700}, {830,1700}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>())),
			new Vendor(7, "Isabel's Ice Cream", "Isabel's Ice Cream\nThe Best Ice Cream In The World", "",  new int[][] {{830,1700}, {830,1700}, {830,1700}, {830,1700}, {830,1700}, {900,1800}, {900,1800}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>()))
			};
	*/
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// Get the list of vendor and create a array of vendors by name only
    	// then pass it to the list adapter to display it as a list in the app
    	/*
    	for (int i = 0; i < vendors.length; i++)
    	{
    		values[i] = vendors[i].getName();
    	}
    	*/
    	
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
        
        /*
        Intent intent = getIntent();
        
        if(intent.getStringExtra("criteria").equals("nearby")){
        	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        			android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);
        }
        */
        mReceiver = new RestResponseReceiver(new Handler());
        mReceiver.setReceiver(this);
        
        final Intent apiIntent = new Intent(Intent.ACTION_SYNC, null, this, RestService.class);
        apiIntent.putExtra("receiver", mReceiver);
        apiIntent.putExtra("class","Vendor");
        apiIntent.putExtra("operation", "read");
        
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
    
    
    
    public void onReceiveResult(int resultCode, Bundle resultData) {
        
    	final int  running = 0;
    	final int finished = 1;
    	final int error = 2;
    	
    	try{
	    	switch (resultCode) {
		        
	    		case running:
		            // TODO show progress
		            break;
		            
		        case finished:
		            String response = resultData.getString("response");

		            // TODO check result code before continuing
		            
		            JSONArray vendors_json = (new JSONObject(response)).getJSONArray("data");
		            
	                // looping through all vendors
	                for (int i = 0; i < vendors_json.length(); i++) {
	                    
	                	JSONObject v = vendors_json.getJSONObject(i);

	                    // specifically get Vendor class attributes of vendor
	                    JSONObject vendorInfo = v.getJSONObject("Vendor");
	                    
	                    // create Vendor object
	                    Vendor vendor = new Vendor();
	                    vendor.setID(vendorInfo.getInt("id"));
	                    vendor.setName(vendorInfo.getString("name"));
	                    vendor.setDescription(vendorInfo.getString("description"));
	                    vendor.setMenu(menu);
	                    Boolean isOpen = vendorInfo.getBoolean("status");
	                    if(isOpen){
	                    	vendor.openTruck();
	                    }else{
	                    	vendor.closeTruck();
	                    }
	                    
	                    //add to list of Vendors
	                    vendors.add(vendor);
	                                       
	                    // update UI
	                    ArrayAdapter<Vendor> adapter = new ArrayAdapter<Vendor>(this,
	                			android.R.layout.simple_list_item_1, vendors);
	                    
	                    setListAdapter(adapter);
	                }
		            
		            // TODO hide progress
		            break;
		        case error:
		            // handle the error;
		            break;
	        }
    	} catch(JSONException e){
    		// TODO handle this
    		e.printStackTrace();
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
    	
    	intent.putExtra("Vendor", vendor);
    	startActivity(intent);    	
    }
    
    
    
}
