package com.intuitive.yummy;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

public class VendorAdminAccountActivity extends Activity {
	private Vendor vendor = new Vendor(1, "Jack's Pizza", "We sell Pizzas!", "123 Main St", new int[][] {{830,1700}, {830,1700}, {830,1700}, {830,1700}, {830,1700}, {0,0}, {0,0}}, false, null, new com.intuitive.yummy.Menu(new ArrayList<MenuItem>()));
	private String button = "View Pending Orders";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vender_admin_account);
        Intent intent = getIntent();
        if (intent.getStringExtra("Activity").equals("Edit"))
        	vendor = (Vendor)intent.getSerializableExtra("Vendor");
		
        ImageView vendorPicture = (ImageView) findViewById(R.id.adminVendorPicture);
        if (vendor.getPictureURL() != null) {
        	vendorPicture.setImageBitmap(BitmapFactory.decodeFile(vendor.getPictureURL()));
        }
        TextView vendorName = (TextView)findViewById(R.id.adminVendorName);
		vendorName.setText(vendor.getName());
		TextView vendorAddress = (TextView)findViewById(R.id.adminVendorAddress);
		vendorAddress.setText(vendor.getAddress());
		TextView vendorStatus = (TextView)findViewById(R.id.adminVendorStatus);
		if (vendor.getStatus())
			vendorStatus.setText("Open");
		else
			vendorStatus.setText("Close");
		
		if (intent.getBooleanExtra("employee",true)){
			Button genInfo = (Button)findViewById(R.id.button_edgeninfo_viewpendhist);
			genInfo.setText(button);
			Button menuEdit = (Button)findViewById(R.id.button_edit_menu);
			menuEdit.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_vender_admin_account, menu);
		return true;
	}
	
	public void genInfo_pendHist(View v) {
		Button genInfo = (Button)findViewById(R.id.button_edgeninfo_viewpendhist);
		if (genInfo.getText() == button) {
			Intent intent = new Intent(this, VendorEditActivity.class); //PendingOrdersActivity.class will be made
	    	intent.putExtra("Vendor", vendor);
	    	startActivity(intent);
		}
		else {
			Intent intent = new Intent(this, VendorEditActivity.class);
    		intent.putExtra("Vendor", vendor);
    		startActivity(intent);
		}
	}
	
	public void editMenu(View v){
		Intent intent = new Intent(this, VendorEditActivity.class);
		intent.putExtra("Vendor", vendor);
		startActivity(intent);
	}
	
	public void viewOrderHist(View v){
		Intent intent = new Intent(this, VendorEditActivity.class); //OrderHistoryActivity.class will be made
		intent.putExtra("Vendor", vendor);
		startActivity(intent);
	}
}
