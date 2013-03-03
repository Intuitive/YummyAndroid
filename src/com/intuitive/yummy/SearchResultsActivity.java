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

	public RestResponseReceiver receiver;
	private String [] values = new String[7];
	
	//dummy data for vendors
	private Vendor[] vendors = {
			new Vendor(1, "Jack's Pizza", "We sell Pizzas!", "", new int[][] {{830,1700}, {830,1700}, {830,1700}, {830,1700}, {830,1700}, {0,0}, {0,0}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>())),
			new Vendor(2, "Sally's Subs", "Welcome to Sally's Subs", "",  new int[][] {{830,1800}, {830,1800}, {830,1800}, {830,1800}, {830,1800}, {0,0}, {0,0}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>())),
			new Vendor(3, "Bob's Burritos", "Hello from Bob's Burritos", "",  new int[][] {{900,1700}, {900,1700}, {900,1700}, {900,1700}, {900,1700}, {900,1900}, {900,1900}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>())),
			new Vendor(4, "Larry's Lasagna", "Welcome to Bob's Burritos", "",  new int[][] {{800,1700}, {800,1700}, {800,1700}, {800,1700}, {800,1700}, {800,2000}, {800,2000}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>())),
			new Vendor(5, "Helga's Hell Kitchen", "Hello from Helga's Hell Kitchen", "",  new int[][] {{830,1700}, {830,1700}, {830,1700}, {830,1700}, {830,1700}, {900,1800}, {0,0}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>())),
			new Vendor(6, "Carlos' Cuisine", "Welcome to Carlos' Cuisine", "",  new int[][] {{0,0}, {830,1700}, {830,1700}, {830,1700}, {830,1700}, {830,1700}, {830,1700}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>())),
			new Vendor(7, "Isabel's Ice Cream", "Isabel's Ice Cream\nThe Best Ice Cream In The World", "",  new int[][] {{830,1700}, {830,1700}, {830,1700}, {830,1700}, {830,1700}, {900,1800}, {900,1800}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>()))
			};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	//Get the list of vendor and create a array of vendors by name only
    	//then pass it to the list adapter to display it as a list in the app
    	for (int i = 0; i < vendors.length; i++)
    	{
    		values[i] = vendors[i].getName();
    	}
    	
    	vendors[0].getMenu().addMenuItem(new MenuItem(1, "16 inch Cheese Pizza", 10, "Pizza", "Plain Cheese Pizza", true, null, null));
    	vendors[0].getMenu().addMenuItem(new MenuItem(2, "16 inch Pepperoni Pizza", 11, "Pizza", "Pizza with Pepperoni Topping", true, null, null));
    	vendors[0].getMenu().addMenuItem(new MenuItem(3, "16 inch Sausage Pizza", 12, "Pizza", "Pizza with Sausage", true, null, null));
    	vendors[0].getMenu().addMenuItem(new MenuItem(4, "Cheese Pizza Slice", 1.5, "Pizza", "Plain Cheese Pizza (slice)", true, null, null));
    	vendors[0].getMenu().addMenuItem(new MenuItem(5, "Pepperoni Pizza Slice", 1.65, "Pizza", "Pizza with Pepperoni Topping (slice)", true, null, null));
    	vendors[0].getMenu().addMenuItem(new MenuItem(6, "Sausage Pizza Slice", 1.75, "Pizza", "Pizza with Sausage (slice)", true, null, null));
    	vendors[0].getMenu().addMenuItem(new MenuItem(7, "Pizza Cheesesteak", 5, "Cheesesteak", "Cheesesteak filled with pizza topping", true, null, null));
    	vendors[0].getMenu().addMenuItem(new MenuItem(8, "Chicken Cheesesteak", 6, "Cheesesteak", "Cheesesteak filled with chicken", true, null, null));
    	vendors[0].getMenu().addMenuItem(new MenuItem(9, "Pepsi 2 Liter", 2.5, "Drink", "2 Liter Pepsi", true, null, null));
    	vendors[0].getMenu().addMenuItem(new MenuItem(10, "Coca-Cola 2 Liter", 2.5, "Drink", "2 Liter Coca-Cola", true, null, null));
    	    	
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        /*
        if(intent.getStringExtra("criteria").equals("nearby")){
        	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        			android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);
        }*/
        
        receiver = new RestResponseReceiver(new Handler());
        receiver.setReceiver(this);
        
        final Intent apiIntent = new Intent(Intent.ACTION_SYNC, null, this, RestService.class);
        apiIntent.putExtra("receiver", receiver);
        apiIntent.putExtra("class","Vendor");
        apiIntent.putExtra("operation", "read");
        
        Log.d("yummy", "Starting service...");
        startService(intent);
        
    }
    
    
    @Override
    public void onResume() {
    	Log.d("yummy", "Resumed...");
    	super.onResume();
    }
    
    @Override
    public void onPause() {
    	Log.d("yummy", "We paused");
        receiver.setReceiver(null); // clear receiver so no leaks.
        super.onPause();
    }
    
    
    
    public void onReceiveResult(int resultCode, Bundle resultData) {
        
    	final int  running = 0;
    	final int finished = 1;
    	final int error = 2;
    	
    	try{
	    	switch (resultCode) {
		        case running:
		            //show progress
		            break;
		        case finished:
		            String response = resultData.getString("response");
		            // Getting Array of vendors
		            JSONObject json = new JSONObject(response);
	                JSONArray vendorsResponse = json.getJSONArray("vendors");
	                ArrayList<HashMap<String, String>> vendorsList =  new ArrayList<HashMap<String, String>>();
	                
	                List<String> vendorNameList = new ArrayList<String>();
	                
	                // looping through All vendors
	                for (int i = 0; i < vendorsResponse.length(); i++) {
	                    JSONObject c = vendorsResponse.getJSONObject(i);
	
	                    // Storing each json item in variable
	                    //String id = c.getString("id");
	                    String name = c.getString("name");
	                    vendorNameList.add(name);
	                   
	                    // creating new HashMap
	                    //HashMap<String, String> map = new HashMap<String, String>();
	
	                    // adding each child node to HashMap key => value
	                    //map.put("id", id);
	                    //map.put("name", name);
	
	                    // adding HashList to ArrayList
	                    //vendorsList.add(map);
	                    
	                    // update UI
	                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	                			android.R.layout.simple_list_item_1, vendorNameList);
	                    setListAdapter(adapter);
	                }
		            
		            // hide progress
		            break;
		        case error:
		            // handle the error;
		            break;
	        }
    	} catch(JSONException e){
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
    	Vendor vendor = vendors[position];
    	Intent intent = new Intent(this, VendorActivity.class);
    	intent.putExtra("Vendor", vendor);
    	startActivity(intent);    	
    }
    
    
    
}
