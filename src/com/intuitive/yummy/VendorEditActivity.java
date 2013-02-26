package com.intuitive.yummy;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class VendorEditActivity extends Activity {
	private Vendor vendor;
	private ToggleButton toggle;
	private Pattern pattern;
	private Matcher matcher;
	final String TIME_PATTERN = "((?=.*[0-9]).{1,2})";
	EditText name, description, location,
	monOpenHour, monOpenMinute, monCloseHour, monCloseMinute, tuesOpenHour, tuesOpenMinute, tuesCloseHour, tuesCloseMinute,
	wedOpenHour, wedOpenMinute, wedCloseHour, wedCloseMinute, thursOpenHour, thursOpenMinute, thursCloseHour, thursCloseMinute,
	friOpenHour, friOpenMinute, friCloseHour, friCloseMinute, satOpenHour, satOpenMinute, satCloseHour, satCloseMinute,
	sunOpenHour, sunOpenMinute, sunCloseHour, sunCloseMinute;
	ArrayList<ArrayList<EditText>> arrayEditText = new ArrayList<ArrayList<EditText>> ();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendor_edit);
        Intent intent = getIntent();
        vendor = (Vendor)intent.getSerializableExtra("Vendor");
		name = (EditText)findViewById(R.id.name_field);
		name.setText(vendor.getName());
		description = (EditText)findViewById(R.id.description_field);
		description.setText(vendor.getDescription());
		location = (EditText)findViewById(R.id.location_field);
		location.setText(vendor.getAddress());
        toggle = (ToggleButton)findViewById(R.id.status_field);
        toggle.setChecked(vendor.getStatus());
        
		ArrayList<EditText> temp = new ArrayList<EditText> ();
		monOpenHour = (EditText)findViewById(R.id.monOpenHour);
		monOpenMinute = (EditText)findViewById(R.id.monOpenMinute);
		monCloseHour = (EditText)findViewById(R.id.monCloseHour);
		monCloseMinute = (EditText)findViewById(R.id.monCloseMinute);
		temp.add(monOpenHour);
		temp.add(monOpenMinute);
		temp.add(monCloseHour);
		temp.add(monCloseMinute);
		arrayEditText.add(temp);
		temp = new ArrayList<EditText> ();
		tuesOpenHour = (EditText)findViewById(R.id.tuesOpenHour);
		tuesOpenMinute = (EditText)findViewById(R.id.tuesOpenMinute);
		tuesCloseHour = (EditText)findViewById(R.id.tuesCloseHour);
		tuesCloseMinute = (EditText)findViewById(R.id.tuesCloseMinute);
		temp.add(tuesOpenHour);
		temp.add(tuesOpenMinute);
		temp.add(tuesCloseHour);
		temp.add(tuesCloseMinute);
		arrayEditText.add(temp);
		temp = new ArrayList<EditText> ();
		wedOpenHour = (EditText)findViewById(R.id.wedOpenHour);
		wedOpenMinute = (EditText)findViewById(R.id.wedOpenMinute);
		wedCloseHour = (EditText)findViewById(R.id.wedCloseHour);
		wedCloseMinute = (EditText)findViewById(R.id.wedCloseMinute);
		temp.add(wedOpenHour);
		temp.add(wedOpenMinute);
		temp.add(wedCloseHour);
		temp.add(wedCloseMinute);
		arrayEditText.add(temp);
		temp = new ArrayList<EditText> ();
		thursOpenHour = (EditText)findViewById(R.id.thursOpenHour);
		thursOpenMinute = (EditText)findViewById(R.id.thursOpenMinute);
		thursCloseHour = (EditText)findViewById(R.id.thursCloseHour);
		thursCloseMinute = (EditText)findViewById(R.id.thursCloseMinute);
		temp.add(thursOpenHour);
		temp.add(thursOpenMinute);
		temp.add(thursCloseHour);
		temp.add(thursCloseMinute);
		arrayEditText.add(temp);
		temp = new ArrayList<EditText> ();
		friOpenHour = (EditText)findViewById(R.id.friOpenHour);
		friOpenMinute = (EditText)findViewById(R.id.friOpenMinute);
		friCloseHour = (EditText)findViewById(R.id.friCloseHour);
		friCloseMinute = (EditText)findViewById(R.id.friCloseMinute);
		temp.add(friOpenHour);
		temp.add(friOpenMinute);
		temp.add(friCloseHour);
		temp.add(friCloseMinute);
		arrayEditText.add(temp);
		temp = new ArrayList<EditText> ();
		satOpenHour = (EditText)findViewById(R.id.satOpenHour);
		satOpenMinute = (EditText)findViewById(R.id.satOpenMinute);
		satCloseHour = (EditText)findViewById(R.id.satCloseHour);
		satCloseMinute = (EditText)findViewById(R.id.satCloseMinute);
		temp.add(satOpenHour);
		temp.add(satOpenMinute);
		temp.add(satCloseHour);
		temp.add(satCloseMinute);
		arrayEditText.add(temp);
		temp = new ArrayList<EditText> ();
		sunOpenHour = (EditText)findViewById(R.id.sunOpenHour);
		sunOpenMinute = (EditText)findViewById(R.id.sunOpenMinute);
		sunCloseHour = (EditText)findViewById(R.id.sunCloseHour);
		sunCloseMinute = (EditText)findViewById(R.id.sunCloseMinute);
		temp.add(sunOpenHour);
		temp.add(sunOpenMinute);
		temp.add(sunCloseHour);
		temp.add(sunCloseMinute);
		arrayEditText.add(temp);
		
		for (int i = 0; i < 7; i++) {
    		int temp1 = vendor.getHours()[i][0];
    		int openHour = (int)Math.floor(temp1/100);
    		int openMinute = temp1%100;
    		int temp2 = vendor.getHours()[i][1];
    		int closeHour = (int)Math.floor(temp2/100);
    		int closeMinute = temp2%100;
			arrayEditText.get(i).get(0).setText(Integer.toString(openHour));
			if (openMinute == 0)
				arrayEditText.get(i).get(1).setText("00");
			else
				arrayEditText.get(i).get(1).setText(Integer.toString(openMinute));
			arrayEditText.get(i).get(2).setText(Integer.toString(closeHour));
			if (closeMinute == 0)
				arrayEditText.get(i).get(3).setText("00");
			else
				arrayEditText.get(i).get(3).setText(Integer.toString(closeMinute));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_vendor_edit, menu);
		return true;
	}
	
	public void changeStatus(View v) {
		if (toggle.isChecked())
			vendor.openTruck();
		else
			vendor.closeTruck();
	}
	
	public void save(View v) {
		if (check()) {
			vendor.setName(name.getText().toString());
			vendor.setDescription(description.getText().toString());
			vendor.setAddress(location.getText().toString());
			int[][] hours = vendor.getHours();
			for (int i = 0; i < 7; i++) {
				int open = Integer.parseInt(arrayEditText.get(i).get(0).getText().toString())*100 + Integer.parseInt(arrayEditText.get(i).get(1).getText().toString());
				int close = Integer.parseInt(arrayEditText.get(i).get(2).getText().toString())*100 + Integer.parseInt(arrayEditText.get(i).get(3).getText().toString());
				hours[i][0] = open;
				hours[i][1] = close;
			}
	    	Intent intent = new Intent(this, VendorAdminAccountActivity.class);
	    	intent.putExtra("Activity", "Edit");
	    	intent.putExtra("Vendor", vendor);
	    	startActivity(intent);
		}
	}
	
	public void cancel(View v) {
    		finish();
	}
	
	private boolean check() {
		boolean completed = true;
		boolean timeFormatCheck = true;
		boolean hourCheck = true;
		boolean minuteCheck = true;
		boolean closeHrAfterOpenHr = true;
		boolean check = false;
				
		if (name.getText().toString().length() == 0)
			completed = false;
		else if (description.getText().toString().length() == 0)
			completed = false;
		else if (location.getText().toString().length() == 0)
			completed = false;
		else {
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 4; j++) {
					if (arrayEditText.get(i).get(j).getText().toString().length() == 0)
						completed = false;
					else if (!checkTime(arrayEditText.get(i).get(j).getText().toString()))
						timeFormatCheck = false;
					else if (j == 0 || j == 2) {
						if(!checkHour(arrayEditText.get(i).get(j).getText().toString()))
							hourCheck = false;
					} else {
						if (!checkMinute(arrayEditText.get(i).get(j).getText().toString()))
							minuteCheck = false;
					}
				}
			}
		}
		
		if (completed && timeFormatCheck && hourCheck && minuteCheck) {
			for (int i = 0; i < 7; i++) {
				if (!checkCorrect(arrayEditText.get(i).get(0).getText().toString(),
						arrayEditText.get(i).get(1).getText().toString(),
						arrayEditText.get(i).get(2).getText().toString(),
						arrayEditText.get(i).get(3).getText().toString()))
					closeHrAfterOpenHr = false;
			}
		}
				
		if (!completed) {
    		Toast.makeText(getApplicationContext(), "Please Complete All Fields", Toast.LENGTH_LONG).show();
		} else if (!timeFormatCheck) {
    		Toast.makeText(getApplicationContext(), "Time Format Error, Enter 2 Digit Number Only", Toast.LENGTH_LONG).show();
		} else if (!hourCheck) {
    		Toast.makeText(getApplicationContext(), "Hour Must Be In Between 0 to 23", Toast.LENGTH_LONG).show();			
		} else if (!minuteCheck) {
    		Toast.makeText(getApplicationContext(), "Minutes Must Be In between 0 to 59", Toast.LENGTH_LONG).show();
		} else if (!closeHrAfterOpenHr) {
    		Toast.makeText(getApplicationContext(), "Closing Hour Must be After Opening (All 00 for Closed All Day)", Toast.LENGTH_LONG).show();
		}
		else
			check = true;
		
		return check;
	}
	
	private boolean checkTime(String time) {
		pattern = Pattern.compile(TIME_PATTERN);
		matcher = pattern.matcher(time);
		return matcher.matches();
	}
	
	private boolean checkHour(String time) {
		if (0 <= Integer.parseInt(time) && Integer.parseInt(time) < 24)
			return true;
		else
			return false;
	}
	private boolean checkMinute(String time) {
		if (0 <= Integer.parseInt(time) && Integer.parseInt(time) < 60)
			return true;
		else
			return false;
	}
	private boolean checkCorrect(String openHr, String openMin, String closeHr, String closeMin) {
		int open = Integer.parseInt(openHr)*100 + Integer.parseInt(openMin);
		int close = Integer.parseInt(closeHr)*100 + Integer.parseInt(closeMin);
		if (close > open)
			return true;
		else if (open == close && open == 0 && close == 0)
			return true;
		else
			return false;
	}
	
}
