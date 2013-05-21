package com.intuitive.yummy.activities;

import java.text.NumberFormat;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.layout;
import com.intuitive.yummy.R.menu;
import com.intuitive.yummy.models.MenuItem;
import com.intuitive.yummy.models.Vendor;
import com.intuitive.yummy.sqlitedb.SQLiteDB;
import com.intuitive.yummy.webservices.IntentExtraKeys;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MenuItemActivity extends Activity {
	
	private MenuItem menuItem;
	private TextView quantityCount;
	
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
        
        // store this for ease of access within class
        quantityCount = (TextView) findViewById(R.id.quantityCount);
	}
	
	public void increaseQuantity(View target) {
		quantityCount.setText(String.valueOf(Integer.parseInt((String) quantityCount.getText()) + 1));
    }
	
	public void decreaseQuantity(View target) {
		Integer quantityCountValue = Integer.parseInt((String) quantityCount.getText());
		if(quantityCountValue > 1)
			quantityCount.setText(String.valueOf(quantityCountValue - 1));
    }
	
	public void addMenuItemToCart(View target)
	{
		// add item to cart
		SQLiteDB db = new SQLiteDB(this);
		Integer quantity = Integer.parseInt((String) quantityCount.getText());
		String specialInstructions = ((EditText) findViewById(R.id.specialInstructions)).getText().toString();
		db.addOrderItem(menuItem, quantity, specialInstructions);
		
		// launch confirmation box
		String itemGrammar = quantity > 1 ? "Items have" : "Item has";
		
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("")
        .setMessage(itemGrammar + " been added to your cart!")
        .setCancelable(false)
        .setNeutralButton("Ok",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                
                // go to MenuActivity
                Intent menuIntent= new Intent(MenuItemActivity.this, MenuActivity.class);
        		menuIntent.putExtra(IntentExtraKeys.MODEL_ID, menuItem.getVendorId());
        		startActivity(menuIntent);
            }
        });
        AlertDialog confimDialog = dialogBuilder.create();
        confimDialog.show();

		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_item, menu);
		return true;
	}

}
