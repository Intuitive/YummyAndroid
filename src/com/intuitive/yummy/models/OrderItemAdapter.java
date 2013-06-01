package com.intuitive.yummy.models;

import java.text.NumberFormat;
import java.util.ArrayList;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.drawable;
import com.intuitive.yummy.R.id;
import com.intuitive.yummy.activities.OrderDetailActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderItemAdapter extends ArrayAdapter<OrderItem>{
	Context context;
	int layoutResourceId;
	ArrayList<OrderItem> orderItems = null;
	AlertDialog.Builder builder = null;
		
	public OrderItemAdapter(Context context, int layoutResourceId, ArrayList<OrderItem> data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.orderItems = data;
	}
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		OrderItemHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new OrderItemHolder();
			holder.quantity = (TextView)row.findViewById(R.id.quantity);
			holder.orderItem = (TextView)row.findViewById(R.id.orderitem);
			holder.price= (TextView)row.findViewById(R.id.price);
			row.setTag(holder);
			
			holder.orderItem.setOnClickListener( new OnClickListener(){
				@Override
				public void onClick(View v){
					// remove item from cart
					AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
					builder.setMessage("Would you like to remove this item?");
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   
				           }});
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			});
			
		} else {
			holder = (OrderItemHolder)row.getTag();
		}
		OrderItem orderItem = orderItems.get(position);
		holder.orderItem.setText(orderItem.getName());
		holder.price.setText(NumberFormat.getCurrencyInstance().format(orderItem.getPrice()));
		
		return row;
	}
		
	static class OrderItemHolder
	{
		TextView quantity;
		TextView orderItem;
		TextView price;
	}
}