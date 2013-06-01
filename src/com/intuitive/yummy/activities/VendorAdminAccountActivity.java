package com.intuitive.yummy.activities;

import java.util.ArrayList;

import com.intuitive.yummy.R;
import com.intuitive.yummy.models.Vendor;
import com.intuitive.yummy.models.Vendor.VendorStatus;
import com.intuitive.yummy.webservices.IntentExtraKeys;
import com.intuitive.yummy.webservices.RestResponseReceiver;
import com.intuitive.yummy.webservices.RestResultCode;
import com.intuitive.yummy.webservices.RestService;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VendorAdminAccountActivity extends Activity implements RestResponseReceiver.Receiver{
	private Vendor vendor;
	private int[][] vendorHours = {{830,1700}, {830,1700}, {830,1700}, {830,1700}, {830,1700}, {0,0}, {0,0}};
	Integer vendorId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendor_admin_account);
        Intent intent = getIntent();
		
        // setup rest receiver
        RestResponseReceiver responseReceiver = new RestResponseReceiver(new Handler());
        responseReceiver.setReceiver(this);
        
        // get id of vendor to get
        vendorId = intent.getIntExtra(IntentExtraKeys.MODEL_ID, -1);
        if(vendorId == -1) throw new IllegalArgumentException("Vendor id not found on intent.");
        
        
        final Intent getVendorIntent = RestService.getReadByIdIntent(vendorId, Vendor.class, this, responseReceiver);
        
        startService(getVendorIntent);
        
        // setup UI based on account type (PARAMETER1 == isAdmin)
        if (intent.getBooleanExtra(IntentExtraKeys.PARAMETER1, false)){
			Button genInfo = (Button)findViewById(R.id.button_edit_general_info);
			genInfo.setVisibility(View.GONE);
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
	
	public void editGenInfo(View v) {
			Intent intent = new Intent(this, VendorEditActivity.class);
			intent.putExtra("Vendor", (Parcelable) vendor);
			intent.putExtra(IntentExtraKeys.MODEL_ID, vendor.getId());
			startActivity(intent);
	}
	
	public void editMenu(View v) {
		Intent intent = new Intent(this, VendorEditMenuActivity.class);
		intent.putExtra(IntentExtraKeys.MODEL_ID, vendor.getId());
		startActivity(intent);
	}
	
	public void viewPendOrder(View v){
		Intent intent = new Intent(this, PendingOrdersActivity.class);
		intent.putExtra(IntentExtraKeys.MODEL_ID, vendor.getId());
		startActivity(intent);
	}
	
	public void viewOrderHist(View v){
		Intent intent = new Intent(this, VendorEditActivity.class); //OrderHistoryActivity.class will be made
		intent.putExtra(IntentExtraKeys.MODEL_ID, vendor.getId());
		startActivity(intent);
	}
	
	public void setWaitTime(View v){
		RestResponseReceiver responseReceiver = new RestResponseReceiver(new Handler());
        responseReceiver.setReceiver(this);
        
        EditText waitTime = (EditText)findViewById(R.id.waitTime);
        
        //Edgar just uncomment this and change it to the setWaitTime or setDuration that you made.
        /*if (waitTime.getText() == null){
        	vendor.setWaitTime(20);
        }
        else{
        	vendor.setWaitTime(Integer.parseInt(waitTime.getText().toString()));
        }*/
        
        Intent updateVendor = RestService.getUpdateIntent(vendor, this, responseReceiver);
		startService(updateVendor);
		
    	Toast.makeText(getApplicationContext(), "Wait time set to " + waitTime.getText(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		
		switch (resultCode) {

		case RestResultCode.RUNNING:
			// TODO show progress
			break;

		case RestResultCode.FINISHED:

			// to test reads
			ArrayList<Vendor> vendorSet = resultData.getParcelableArrayList(RestService.BundleObjectKey);
			
			if(vendorSet.size() == 0) Log.e("yummy", "No vendor found with id " + String.valueOf(vendorId));
					
			vendor = vendorSet.get(0);
			vendor.setHours(vendorHours);
			
			/*ImageView vendorPicture = (ImageView) findViewById(R.id.adminVendorPicture);
	        if (vendor.getPictureUrl() != null) {
	        	vendorPicture.setImageBitmap(BitmapFactory.decodeFile(vendor.getPictureUrl()));
	        }*/
	        
	        ((TextView)findViewById(R.id.adminVendorName)).setText(vendor.getName());
			((TextView)findViewById(R.id.adminVendorAddress)).setText(vendor.getLocation());
			
			TextView vendorStatus = (TextView) findViewById(R.id.adminVendorStatus);
			if (vendor.getStatus() == VendorStatus.OPEN)
				vendorStatus.setText("Open");
			else
				vendorStatus.setText("Close");
			
			
			
			
			// TODO hide progress
			break;
		case RestResultCode.ERROR:
			//RestResultCode.ERROR.getValue()
			break;
		}
	}
}
