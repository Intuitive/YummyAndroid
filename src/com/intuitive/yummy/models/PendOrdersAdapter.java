package com.intuitive.yummy.models;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.id;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
		} else {
			holder = (OrderHolder)row.getTag();
		}
		Order ord = data[position];
		holder.orderID.setText(Integer.toString(ord.getId()));
		holder.timeCreated.setText(Integer.toString(ord.getWaitTime()));
		holder.waitTime.setText(Integer.toString(ord.getWaitTime()));
		
		return row;
	}
	
	static class OrderHolder
	{
		TextView orderID;
		TextView timeCreated;
		TextView waitTime;
	}
}
