package com.intuitive.yummy.models;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.id;
import com.intuitive.yummy.activities.MainActivity;
import com.intuitive.yummy.activities.OrderDetailActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PendOrdersAdapter extends ArrayAdapter<Order> {
	Context context;
	int layoutResourceId;
	Order data[] = null;
	
	public PendOrdersAdapter (Context context, int layoutResourceId, Order[] data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		OrderHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new OrderHolder();
			holder.orderID = (TextView)row.findViewById(R.id.orderID);
			holder.timeCreated = (TextView)row.findViewById(R.id.waitTime);
			holder.waitTime = (TextView)row.findViewById(R.id.waitTime);
			row.setTag(holder);
			
			holder.orderID.setOnClickListener( new OnClickListener(){
				@Override
				public void onClick(View v){
					Intent intent = new Intent(v.getContext(), OrderDetailActivity.class);
					//intent.putExtra("OrderID", orderID.getText());
			    	v.getContext().startActivity(intent);
				}
			});
		} else {
			holder = (OrderHolder)row.getTag();
		}
		Order ord = data[position];
		holder.orderID.setText(Integer.toString(ord.getId()));
		holder.timeCreated.setText(Integer.toString(ord.getWaitTime()));
		holder.waitTime.setText(Integer.toString(ord.getWaitTime()));
		
		// put setonclicklistener
		
		
		return row;
	}
	
	static class OrderHolder
	{
		TextView orderID;
		TextView timeCreated;
		TextView waitTime;
	}
}
