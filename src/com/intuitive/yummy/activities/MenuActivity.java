package com.intuitive.yummy.activities;

import java.util.ArrayList;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.id;
import com.intuitive.yummy.R.layout;
import com.intuitive.yummy.R.menu;
import com.intuitive.yummy.models.MenuItem;
import com.intuitive.yummy.models.MenuItemAdapter;
import com.intuitive.yummy.models.Vendor;
import com.intuitive.yummy.webservices.IntentExtraKeys;
import com.intuitive.yummy.webservices.RestResponseReceiver;
import com.intuitive.yummy.webservices.RestResultCode;
import com.intuitive.yummy.webservices.RestService;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MenuActivity extends Activity implements RestResponseReceiver.Receiver, OnItemClickListener{
	
	private ListView listView;
	public RestResponseReceiver responseReceiver;
	ArrayList<MenuItem> menuItems;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
        
		// get the vendor whose menu items we have to display
        Intent intent = getIntent();
        Integer vendorId = intent.getIntExtra(IntentExtraKeys.MODEL_ID, -1);
        
        if(vendorId < 0) throw new IllegalArgumentException("Vendor Id must be > 0");
        
        // set up our receiver and rest service intent
        responseReceiver = new RestResponseReceiver(new Handler());
        responseReceiver.setReceiver(this);
        final Intent restServiceIntent = RestService.getReadManyByParamIntent(MenuItem.class, String.valueOf(vendorId), this, responseReceiver);
        
        // start up service
        startService(restServiceIntent);
    }
    
    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.d("yummy", "menu item clicked!");
		
		Intent menuItemIntent= new Intent(this, MenuItemActivity.class);
		menuItemIntent.putExtra(IntentExtraKeys.MODEL, (Parcelable) menuItems.get(position));
		startActivity(menuItemIntent);
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search_results, menu);
        return true;
    }

    
    public void cart(View v){
    	Intent intent = new Intent(this, CartActivity.class);
    	startActivity(intent);
    }

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {

    	switch (resultCode) {
		    
			case RestResultCode.RUNNING:
		        // TODO show progress
		        break;
		        
		    case RestResultCode.FINISHED:
		    
		    	menuItems = resultData.getParcelableArrayList(RestService.BundleObjectKey);

		    	if(menuItems.size() == 0){
		    		// TODO display menu empty (or more likely, throw error since this shouldn't happen)
		    	}

		    	// update UI
		    	MenuItemAdapter adapter = new MenuItemAdapter(this, R.layout.list_menuitem, menuItems);
		    	listView = (ListView) findViewById(R.id.listMenuItem);
		    	listView.setOnItemClickListener(this);
				listView.setAdapter(adapter);
				
		        // TODO hide progress
		        break;
		    case RestResultCode.ERROR:
		        	//
		        break;
		}
		
	}

	
    
}
