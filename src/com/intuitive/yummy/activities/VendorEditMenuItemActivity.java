package com.intuitive.yummy.activities;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.layout;
import com.intuitive.yummy.R.menu;
import com.intuitive.yummy.models.MenuItem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VendorEditMenuItemActivity extends Activity {
	Button cancel, delete;
	TextView required1, required2, required3;
	EditText name, price, description, category;
	Pattern pattern;
	Matcher matcher;
	MenuItem item;
	final String PRICE_PATTERN = "^[0-9]*(\\.[0-9]{0,1})[0-9]$";

	private void init() {
		required1 = (TextView)findViewById(R.id.required1_editmenu);
		required1.setTextColor(Color.parseColor("#FF0000"));
		required1.setVisibility(TextView.INVISIBLE);
		required2 = (TextView)findViewById(R.id.required2_editmenu);
		required2.setTextColor(Color.parseColor("#FF0000"));
		required2.setVisibility(TextView.INVISIBLE);
		required3 = (TextView)findViewById(R.id.required3_editmenu);
		required3.setTextColor(Color.parseColor("#FF0000"));
		required3.setVisibility(TextView.INVISIBLE);
		name = (EditText)findViewById(R.id.itemname_field);
		price = (EditText)findViewById(R.id.itemprice_field);
		description = (EditText)findViewById(R.id.itemdescription_field);
		category = (EditText)findViewById(R.id.itemcategory_field);
		cancel = (Button)findViewById(R.id.button_edit_menu_cancel);
        cancel.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
        delete = (Button)findViewById(R.id.button_edit_menu_delete);
        delete.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		// Delete item from database
        		finish();
        	}
        });
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendor_edit_menu_item);
		init();
        Intent intent = getIntent();
        int MenuItemId = (Integer)intent.getSerializableExtra("MenuItemId");
        // Connect to the database to get the menu item information
        MenuItem item = (MenuItem)intent.getSerializableExtra("MenuItem");
    	//System.out.println("MenuItemId: " + item.getId() + ", MenuItemName: " + item.getName());
        name.setText(item.getName());
        price.setText(Double.toString(item.getPrice()));
        description.setText(item.getDescription());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_vendor_edit_menu_item, menu);
		return true;
	}

	public void checkComplete(View v) {
		resetCheck();
		if (!checkAllCompleted()) {
			Toast.makeText(getApplicationContext(), "Please Complete All Fields Marked by *", Toast.LENGTH_LONG).show();
		} else if (!checkValidPrice()) {
			required2.setVisibility(TextView.VISIBLE);
    		Toast.makeText(getApplicationContext(), "Price Can Contain Number Only", Toast.LENGTH_LONG).show();
		} else {
			//save information to database

			finish();
		}
	}

	private void resetCheck()
	{
		required1.setVisibility(TextView.INVISIBLE);
		required2.setVisibility(TextView.INVISIBLE);
		required3.setVisibility(TextView.INVISIBLE);
	}

	private boolean checkAllCompleted() {
		boolean allCompleted = true;
		if (name.getText().toString().isEmpty()) {
			allCompleted = false;
			required1.setVisibility(TextView.VISIBLE);
		}
		if (price.getText().toString().isEmpty()) {
			allCompleted = false;
			required2.setVisibility(TextView.VISIBLE);
		}
		if (category.getText().toString().isEmpty()) {
			allCompleted = false;
			required3.setVisibility(TextView.VISIBLE);
		}
		return allCompleted;
	}

	// Check using patterns to make sure username is in valid format
	private boolean checkValidPrice() {
		pattern = Pattern.compile(PRICE_PATTERN);
		matcher = pattern.matcher(price.getText().toString());
		return matcher.matches();
	}
}