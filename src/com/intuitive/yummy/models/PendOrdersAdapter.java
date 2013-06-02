package com.intuitive.yummy.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.intuitive.yummy.R;
import com.intuitive.yummy.activities.MainActivity;
import com.intuitive.yummy.activities.OrderDetailActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PendOrdersAdapter extends ArrayAdapter<Order> {
	Context context;
	int layoutResourceId;
	ListView listView;
	
	ArrayList<Order> orders = null;
	ArrayList<OrderHolder> orderRows = new ArrayList<OrderHolder>();
	ArrayList<OrderCountDownTimer> timers = new ArrayList<OrderCountDownTimer>();
	
	public PendOrdersAdapter (Context context, int layoutResourceId, ArrayList<Order> orders) {
		super(context, layoutResourceId, orders);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.orders = orders;
	}
	
	@SuppressLint("DefaultLocale")
	@Override
	public View getView(int position, View row, ViewGroup parent) {
		OrderHolder holder = null;
		
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new OrderHolder();
			holder.orderId = (TextView)row.findViewById(R.id.orderId);
			holder.timeRemaining = (TextView)row.findViewById(R.id.timeRemaining);
			row.setTag(holder);
			
			row.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					String orderId = (String) v.findViewById(R.id.orderId).getTag(R.string.order_id_tag);
					String vendorId = (String) v.findViewById(R.id.orderId).getTag(R.string.vendor_id_tag);
					
					// pass orderId to OrderDetail screen
					Intent intent = new Intent(v.getContext(), OrderDetailActivity.class);
					intent.putExtra(IntentExtraKeys.MODEL_ID, orderId);
					intent.putExtra("vendorId", vendorId);
					v.getContext().startActivity(intent);
				}
			});
			
		} else {
			holder = (OrderHolder)row.getTag();
		}		
		orderRows.add(holder);
		
		// put setonclicklistener

		Order order = orders.get(position);
		holder.orderId.setText("Order #: " + Integer.toString(order.getId()));
		holder.orderId.setTag(R.string.order_id_tag, Integer.toString(order.getId()));
		holder.orderId.setTag(R.string.vendor_id_tag, Integer.toString(order.getVendorId()));
		// calculate time remaining [(dateCreated + waitTime) - now]
		long timeOrderIsDue = order.getDateCreated().getTime() + order.getWaitTime() * 60000L;
		long timeRemaining = timeOrderIsDue - new Date().getTime();
		if(timeRemaining < 0) timeRemaining = 0L;
		
		timers.add(new OrderCountDownTimer(timeRemaining, 1*1000, position));
		timers.get(position).start();
		
		return row;
	}
	
	static class OrderHolder
	{
		TextView orderId;
		TextView timeCreated;
		TextView timeRemaining;
	}
	
	public class OrderCountDownTimer extends CountDownTimer {
		private int orderIndex;
		
		public OrderCountDownTimer(long startTime, long interval, int index) {
			super(startTime, interval);
			this.orderIndex = index;
		}

		@Override
		public void onFinish() {
			orderRows.get(orderIndex).timeRemaining.setText("3:00");
		}

		@SuppressLint("DefaultLocale")
		@Override
		public void onTick(long millisUntilFinished) {
			String timeRemaining = String.format("%02d:%02d", 
					TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
					TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)- 
					TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
				);
			// TODO change color of text depending on how much time is left
			//Log.d("yummy", "tick!");
			orderRows.get(orderIndex).timeRemaining.setText(timeRemaining);
			if (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) < 2){
				orderRows.get(orderIndex).timeRemaining.setTextColor(0xff0000);
			} else if (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) < 5 
					&& TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) > 2){
				orderRows.get(orderIndex).timeRemaining.setTextColor(0xffff00);
			} else {
				orderRows.get(orderIndex).timeRemaining.setTextColor(0x008000);
			}
		}
	}
}
