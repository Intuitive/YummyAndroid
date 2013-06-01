package com.intuitive.yummy.activities;

import java.util.ArrayList;

import com.intuitive.yummy.R;
import com.intuitive.yummy.models.MenuItem;
import com.intuitive.yummy.models.PendOrdersAdapter;
import com.intuitive.yummy.models.Order;
import com.intuitive.yummy.webservices.IntentExtraKeys;
import com.intuitive.yummy.webservices.RestResponseReceiver;
import com.intuitive.yummy.webservices.RestResultCode;
import com.intuitive.yummy.webservices.RestService;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class PendingOrdersActivity extends Activity implements RestResponseReceiver.Receiver{
	private static int YOrder = 1;
	private static int NOrder = 2;
	private ArrayList<Order> orders;
	public RestResponseReceiver responseReceiver;
	//private ArrayList<Order> orders = new ArrayList<Order> ();
	
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pending_orders);
		
		Intent incomingIntent = getIntent();
		
		// Get vendor id from intent
		if(!incomingIntent.hasExtra(IntentExtraKeys.MODEL_ID)) throw new IllegalArgumentException("Vendor Id must be > 0");
		Integer vendorId = incomingIntent.getIntExtra(IntentExtraKeys.MODEL_ID, -1);
		
		// Get orders that are still in progress
		responseReceiver = new RestResponseReceiver(new Handler());
        responseReceiver.setReceiver(this);
        
        final Intent restServiceIntent = Order.getOrdersIntent(vendorId, null, null, this, responseReceiver);
        startService(restServiceIntent);
            
        // setup UI
		setContentView(R.layout.activity_pending_orders);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pending_order, menu);
		return true;
	}
	
	public void toHome(View v){
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
	
	public void toOrderDetails(View v){
		Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, OrderDetailActivity.class);
		intent.putExtra("OrderId", "20");
		startActivity(intent);
		
	}
	
	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch (resultCode) {

		case RestResultCode.RUNNING:
			// TODO show progress
			break;

		case RestResultCode.FINISHED:

			// Get orders from result
			orders = resultData.getParcelableArrayList(RestService.BundleObjectKey);

			// Load orders into the list
			PendOrdersAdapter adapter = new PendOrdersAdapter(this, R.layout.list_pending_order, orders);
			listView = (ListView) findViewById(R.id.listPendingOrders);
			listView.setAdapter(adapter);
			registerForContextMenu(listView);

			// TODO hide progress
			break;
		case RestResultCode.ERROR:
			//RestResultCode.ERROR.getValue()
			break;
		}

	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("Is the order complete?");
        menu.add(android.view.Menu.NONE, YOrder, android.view.Menu.NONE, "Yes");
        menu.add(android.view.Menu.NONE, NOrder, android.view.Menu.NONE, "No");
	}
	
	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
		//AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
    		switch (item.getItemId()) {
    		case 1:
    			Intent intent = new Intent(this, OrderDetailActivity.class);
    	    	intent.putExtra("orderComp", true);
    			Toast.makeText(getApplicationContext(), "test", 2).show();
    			return true;
    		case 2:
    			closeContextMenu();
    		default:
    			return super.onContextItemSelected(item);
    		}
	}
}


