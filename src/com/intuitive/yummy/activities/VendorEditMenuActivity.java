package com.intuitive.yummy.activities;

import java.util.ArrayList;

import com.intuitive.yummy.R;
import com.intuitive.yummy.models.*;
import com.intuitive.yummy.webservices.IntentExtraKeys;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class VendorEditMenuActivity extends Activity {

	private com.intuitive.yummy.models.Menu menu = new com.intuitive.yummy.models.Menu();
	private ListView listView;
	Button addNewItemBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        final int vendorId = (Integer) intent.getSerializableExtra(IntentExtraKeys.MODEL_ID);
        ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
        
        // TODO Connect to the server and get the list of menuitem using the vendorID
        menu.add(new MenuItem(1, 1, "16 inch Cheese Pizza", 10.0, "Pizza", "Plain Cheese Pizza", true, null));
    	menu.add(new MenuItem(2, 1, "16 inch Pepperoni Pizza", 11.0, "Pizza", "Pizza with Pepperoni Topping", true, null));
    	menu.add(new MenuItem(3, 1, "16 inch Sausage Pizza", 12.0, "Pizza", "Pizza with Sausage", true, null));
    	menu.add(new MenuItem(4, 1, "Cheese Pizza Slice", 1.5, "Pizza", "Plain Cheese Pizza (slice)", true, null));
    	menu.add(new MenuItem(5, 1, "Pepperoni Pizza Slice", 1.65, "Pizza", "Pizza with Pepperoni Topping (slice)", true, null));
    	menu.add(new MenuItem(6, 1, "Sausage Pizza Slice", 1.75, "Pizza", "Pizza with Sausage (slice)", true, null));
    	menu.add(new MenuItem(7, 1, "Pizza Cheesesteak", 5.0, "Cheesesteak", "Cheesesteak filled with pizza topping", true, null));
    	menu.add(new MenuItem(8, 1, "Chicken Cheesesteak", 6.0, "Cheesesteak", "Cheesesteak filled with chicken", true, null));
    	menu.add(new MenuItem(9, 1, "Pepsi 2 Liter", 2.5, "Drink", "2 Liter Pepsi", true, null));
    	menu.add(new MenuItem(10, 1, "Coca-Cola 2 Liter", 2.5, "Drink", "2 Liter Coca-Cola", true, null));
    	
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendor_edit_menu);
		
		// setup add new button
		addNewItemBtn = (Button)findViewById(R.id.add_item);
		addNewItemBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		startEditMenuItemActivity(v, new MenuItem());
        	}
        });
		
		// setup list
		MenuItemAdapter adapter = new MenuItemAdapter(this, R.layout.list_menuitem, menu);
		listView = (ListView) findViewById(R.id.editMenuItemList);
		
		// setup listener for clicks on MenuItems
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View v, int position,
					long id) {
		    	MenuItem menuItem = (MenuItem) listView.getItemAtPosition(position);
		    	startEditMenuItemActivity(v, menuItem);
			}			
		});
		
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_vendor_edit_menu, menu);
		return true;
	}

	/**
	 * Will pass a MenuItem id to the VendorEditMenuItemActivity. If the id is > 0, the
	 * existing MenuItem will be edited. If the id is equal to 0, a new MenuItem will be created.
	 * @param v
	 * @param menuItemId
	 */
    private void startEditMenuItemActivity(View v, MenuItem menuItem) {
    	Intent intent = new Intent(this, VendorEditMenuItemActivity.class);
    	intent.putExtra(IntentExtraKeys.MODEL, (Parcelable) menuItem);
    	
    	startActivity(intent);
    }
}