package com.intuitive.yummy.models;

import java.text.NumberFormat;
import java.util.ArrayList;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.drawable;
import com.intuitive.yummy.R.id;
import com.intuitive.yummy.activities.OrderDetailActivity;
import com.intuitive.yummy.sqlitedb.SQLiteDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
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
		final int positionToRemove = position;	// for order items that will be removed
		OrderItemHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new OrderItemHolder();
			holder.quantity = (TextView)row.findViewById(R.id.quantity);
			holder.orderItem = (TextView)row.findViewById(R.id.orderitem);
			holder.price= (TextView)row.findViewById(R.id.price);
			holder.removeItem = (TextView)row.findViewById(R.id.remove_item);
			row.setTag(holder);
			
			/*holder.removeItem.setOnClickListener( new OnClickListener(){
				@Override
				public void onClick(View v){
					// remove item from cart
					AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
					builder.setMessage("Remove " + orderItems.get(positionToRemove).getName() + " from your order?");
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   orderItems.remove(positionToRemove);
				        	   
				           }});
					v.postInvalidate();
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			});
			row.postInvalidate();
			*/
		} else {
			holder = (OrderItemHolder)row.getTag();
		}
		OrderItem orderItem = orderItems.get(position);
		holder.quantity.setText(orderItem.getQuantity().toString());
		holder.orderItem.setText(orderItem.getName());
		holder.price.setText(NumberFormat.getCurrencyInstance().format(orderItem.getPrice()));
		
		return row;
	}
		
	static class OrderItemHolder
	{
		TextView quantity;
		TextView orderItem;
		TextView price;
		TextView removeItem;
	}
}