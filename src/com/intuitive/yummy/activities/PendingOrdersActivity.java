package com.intuitive.yummy.activities;

import java.util.ArrayList;

import com.intuitive.yummy.R;
import com.intuitive.yummy.models.Order.OrderStatus;
import com.intuitive.yummy.models.PendOrdersAdapter;
import com.intuitive.yummy.models.Order;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PendingOrdersActivity extends Activity{
	
	private Order ordprog1 = new Order(20, 15, OrderStatus.IN_PROGRESS, 15);
	private Order ordprog2 = new Order(21, 15, OrderStatus.IN_PROGRESS, 20);
	private Order ordfill = new Order(22, 15, OrderStatus.FULLFILLED, 15);
	
	private ArrayList<Order> orders = new ArrayList<Order> ();
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pending_orders);
		
		orders.add(ordprog1);
		orders.add(ordprog2);
		orders.add(ordfill);
		
		Order[] ordersArray = new Order[orders.size()];
		orders.toArray(ordersArray);
		
		PendOrdersAdapter adapter = new PendOrdersAdapter(this, R.layout.list_pending_order, ordersArray);
		listView = (ListView)findViewById(R.id.listPendingOrders);
		listView.setAdapter(adapter);
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
}
