package com.intuitive.yummy.activities;

import java.text.NumberFormat;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.layout;
import com.intuitive.yummy.R.menu;
import com.intuitive.yummy.models.MenuItem;
import com.intuitive.yummy.models.Vendor;
import com.intuitive.yummy.webservices.IntentExtraKeys;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuItemActivity extends Activity {
	
	private MenuItem menuItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_item);
		
		// Get the menu item
        Intent intent = getIntent();
        menuItem = (MenuItem) intent.getParcelableExtra(IntentExtraKeys.MODEL);
        
        // set UI values
        String price = NumberFormat.getCurrencyInstance().format(menuItem.getPrice());
        ((TextView) findViewById(R.id.menuItemPrice)).setText(price);
        ((TextView) findViewById(R.id.menuItemName)).setText(menuItem.getName());
        ((TextView) findViewById(R.id.menuItemDescription)).setText(menuItem.getDescription());
        
        // setup quantity number picker
        
	}
	
	public void increaseQuantity(View target) {
		TextView quantityCount = (TextView) findViewById(R.id.quantityCount);
		Integer quantityCountValue = Integer.parseInt((String) quantityCount.getText());
		quantityCount.setText(String.valueOf(quantityCountValue + 1));
    }
	
	public void decreaseQuantity(View target) {
		TextView quantityCount = (TextView) findViewById(R.id.quantityCount);
		Integer quantityCountValue = Integer.parseInt((String) quantityCount.getText());
		
		if(quantityCountValue > 0)
			quantityCount.setText(String.valueOf(quantityCountValue - 1));
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_item, menu);
		return true;
	}

}
