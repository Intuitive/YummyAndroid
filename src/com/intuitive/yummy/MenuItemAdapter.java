package com.intuitive.yummy;

import java.text.NumberFormat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {
	Context context;
	int layoutResourceId;
	MenuItem data[] = null;
	
	public MenuItemAdapter (Context context, int layoutResourceId, MenuItem[] data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ItemHolder holder = null;
		if(row == null)
		{
			System.out.println("Row = null");
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ItemHolder();
			holder.name = (TextView)row.findViewById(R.id.item_name);
			holder.price = (TextView)row.findViewById(R.id.item_price);
			row.setTag(holder);
		} else {
			System.out.println("Row != null");
			holder = (ItemHolder)row.getTag();
		}
		MenuItem item = data[position];
		holder.name.setText(item.getName());
		NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
		holder.price.setText(defaultFormat.format(item.getPrice()));
		
		return row;
	}
	
	static class ItemHolder
	{
		TextView name;
		TextView price;
	}
	
}
