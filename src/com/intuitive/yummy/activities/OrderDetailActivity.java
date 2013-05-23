package com.intuitive.yummy.activities;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.id;
import com.intuitive.yummy.R.layout;
import com.intuitive.yummy.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.view.Menu;
import android.view.View;

public class OrderDetailActivity extends Activity {

	//Dummy data
	String[] orderedItems = { "16 inch Cheese Pizza", "16 inch Pepperoni Pizza",
			"16 inch Sausage Pizza", "Cheese Pizza Slice" };
	String[] specialInstructions = {"double cheese", "", "light sauce", "" };

	TableLayout t1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);

		//adding cell to each row with dummy data
		t1 = (TableLayout) findViewById(R.id.table1);
		for (int i = 0; i < orderedItems.length; i++) {
			TableRow tr = new TableRow(this);
			TextView tv1 = new TextView(this);
			TextView tv2 = new TextView(this);
			TextView tv3 = new TextView(this);
			//user defined function
			createView(tr, tv1, Integer.toString(1));
			createView(tr, tv2, orderedItems[i]);
			createView(tr, tv3, specialInstructions[i]);
			//add row to table
			t1.addView(tr);
		}
		
		//adding the last row for the total amount using dummy data
		TableRow tr2 = new TableRow(this);
		TextView tv4 = new TextView(this);
		TextView tv5 = new TextView(this);
		TextView tv6 = new TextView(this);
		createView(tr2, tv4, "Total:");
		createView(tr2, tv5, "   ");
		createView(tr2, tv6, "   ");
		t1.addView(tr2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cart, menu);
		return true;
	}

	//Function to add each cell to row
	public void createView(TableRow tr, TextView t, String viewdata) {
		//content of each cell
		t.setText(viewdata);
		t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		t.setTextColor(Color.WHITE);
		t.setBackgroundColor(Color.BLACK);
		//creating borders
		t.setPadding(20, 0, 0, 0);
		tr.setPadding(0, 1, 0, 1);
		tr.setBackgroundColor(Color.WHITE);
		//add textview to row
		tr.addView(t);
	}
	
	//Go to home page when home button is clicked
    public void goToHome(View v){
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
    
    public void toggleOrderReady(View v){
    	// send to server that order is ready
    	Toast.makeText(getApplicationContext(), "Order is ready", Toast.LENGTH_LONG).show();
    }
}
