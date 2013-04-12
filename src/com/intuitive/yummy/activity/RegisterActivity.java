package com.intuitive.yummy.activity;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.id;
import com.intuitive.yummy.R.layout;
import com.intuitive.yummy.R.menu;

public class RegisterActivity extends Activity {
	Button cancel;
	TextView required1, required2, required3, required4, required5, required6, required7;
	EditText username, password1, password2, firstname, lastname, phone, email;
	Pattern pattern;
	Matcher matcher;
	// Patterns for error checking in register page
	// Username has to be 6 to 20 characters and must contain at least one lower-case letter
	final String USERNAME_PATTERN = "((?=.*[a-z]).{6,20})";
	// Password has to be 8 characters or above and must contain at least one upper-case letter,
	// one lower-case letter, and one digit
	final String PASSWORD_PATTERN = "((?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,})";
	// Name cannot contain any digit
	final String NAME_PATTERN = "[^0-9]*";
	// Phone number must be in the format of 123-456-7890
	final String PHONE_PATTERN = "^(\\d{3}-{1}\\d{3}-{1}\\d{4})$";
	// Email must be in correct format, xxx@yyy.zzz
	final String EMAIL_PATTERN = "^[A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*[A-Za-z0-9]+@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	// Initialize all the formats in the page and button listeners
	private void init() {
		required1 = (TextView)findViewById(R.id.required1);
		required1.setTextColor(Color.parseColor("#FF0000"));
		required1.setVisibility(TextView.INVISIBLE);
		required2 = (TextView)findViewById(R.id.required2);
		required2.setTextColor(Color.parseColor("#FF0000"));
		required2.setVisibility(TextView.INVISIBLE);
		required3 = (TextView)findViewById(R.id.required3);
		required3.setTextColor(Color.parseColor("#FF0000"));
		required3.setVisibility(TextView.INVISIBLE);
		required4 = (TextView)findViewById(R.id.required4);
		required4.setTextColor(Color.parseColor("#FF0000"));
		required4.setVisibility(TextView.INVISIBLE);
		required5 = (TextView)findViewById(R.id.required5);
		required5.setTextColor(Color.parseColor("#FF0000"));
		required5.setVisibility(TextView.INVISIBLE);
		required6 = (TextView)findViewById(R.id.required6);
		required6.setTextColor(Color.parseColor("#FF0000"));
		required6.setVisibility(TextView.INVISIBLE);
		required7 = (TextView)findViewById(R.id.required7);
		required7.setTextColor(Color.parseColor("#FF0000"));
		required7.setVisibility(TextView.INVISIBLE);
		
		username = (EditText)findViewById(R.id.username_field);
		password1 = (EditText)findViewById(R.id.password_field);
		password2 = (EditText)findViewById(R.id.reenter_password_field);
		firstname = (EditText)findViewById(R.id.firstname_field);
		lastname = (EditText)findViewById(R.id.lastname_field);
		phone = (EditText)findViewById(R.id.phone_field);
		email = (EditText)findViewById(R.id.email_field);
		
		cancel = (Button)findViewById(R.id.button_register_cancel);
        cancel.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_register, menu);
		return true;
	}
	
	public void checkRegistration(View v) {
		// Clear the '!' and the check status when user click on the register button
		resetCheck();

		if (!checkAllCompleted()) {
    		Toast.makeText(getApplicationContext(), "Please Complete All Fields Marked by *", Toast.LENGTH_LONG).show();
		} else if (!checkValidUsername()) {
			required1.setVisibility(TextView.VISIBLE);
    		Toast.makeText(getApplicationContext(), "Username Must Be Between 8 to 20 Characters and Must Contain At Least 1 Letter", Toast.LENGTH_LONG).show();
		} else if (!checkValidPassword()) {
			required2.setVisibility(TextView.VISIBLE);
    		Toast.makeText(getApplicationContext(), "Password Must Be Between 8 to 20 Characters and Must Contain At Least 1 Uppercase, 1 Lowercase, and 1 Number", Toast.LENGTH_LONG).show();
		} else if (!checkPasswordMatch()) {
			required2.setVisibility(TextView.VISIBLE);
			required3.setVisibility(TextView.VISIBLE);
    		Toast.makeText(getApplicationContext(), "Password Mis-Match, Please Re-Enter", Toast.LENGTH_LONG).show();
		} else if (!checkValidFirstname()) {
			required4.setVisibility(TextView.VISIBLE);
    		Toast.makeText(getApplicationContext(), "Firstname Cannot Include Numbers", Toast.LENGTH_LONG).show();
		} else if (!checkValidLastname()) {
			required5.setVisibility(TextView.VISIBLE);
    		Toast.makeText(getApplicationContext(), "Lastname Cannot Include Numbers", Toast.LENGTH_LONG).show();
		} else if (!checkValidPhone()) {
			required6.setVisibility(TextView.VISIBLE);
    		Toast.makeText(getApplicationContext(), "Phone Number in Wrong Format.  Please Follow The Format 123-456-7890", Toast.LENGTH_LONG).show();
		} else if (!checkValidEmail()) {
			required7.setVisibility(TextView.VISIBLE);
    		Toast.makeText(getApplicationContext(), "Email in Wrong Format.  Please Double Check", Toast.LENGTH_LONG).show();
		} else {
			//register
		}
	}
	
	private void resetCheck() {
		required1.setVisibility(TextView.INVISIBLE);
		required2.setVisibility(TextView.INVISIBLE);
		required3.setVisibility(TextView.INVISIBLE);
		required4.setVisibility(TextView.INVISIBLE);
		required5.setVisibility(TextView.INVISIBLE);
		required6.setVisibility(TextView.INVISIBLE);
		required7.setVisibility(TextView.INVISIBLE);
	}
	
	// Check to make sure all fields are completed
	private boolean checkAllCompleted() {
		boolean allCompleted = true;
		if (username.getText().toString().isEmpty()) {
			allCompleted = false;
			required1.setVisibility(TextView.VISIBLE);
		}
		if (password1.getText().toString().isEmpty()) {
			allCompleted = false;
			required2.setVisibility(TextView.VISIBLE);
		}
		if (password2.getText().toString().isEmpty()) {
			allCompleted = false;
			required3.setVisibility(TextView.VISIBLE);
		}
		if (firstname.getText().toString().isEmpty()) {
			allCompleted = false;
			required4.setVisibility(TextView.VISIBLE);
		}
		if (lastname.getText().toString().isEmpty()) {
			allCompleted = false;
			required5.setVisibility(TextView.VISIBLE);
		}
		if (phone.getText().toString().isEmpty()) {
			allCompleted = false;
			required6.setVisibility(TextView.VISIBLE);
		}
		if (email.getText().toString().isEmpty()) {
			allCompleted = false;
			required7.setVisibility(TextView.VISIBLE);
		}
		return allCompleted;
	}
	
	// Check using patterns to make sure username is in valid format
	private boolean checkValidUsername() {
		pattern = Pattern.compile(USERNAME_PATTERN);
		matcher = pattern.matcher(username.getText().toString());
		return matcher.matches();
	}

	// Check using patterns to make sure password is in valid format
	private boolean checkValidPassword() {
		pattern = Pattern.compile(PASSWORD_PATTERN);
		matcher = pattern.matcher(password1.getText().toString());
		return matcher.matches();
	}
	
	// Check to make sure both passwords entered is match
	private boolean checkPasswordMatch() {
		return password1.getText().toString().equals(password2.getText().toString());
	}

	// Check to make sure first name is in valid format
	private boolean checkValidFirstname() {
		pattern = Pattern.compile(NAME_PATTERN);
		matcher = pattern.matcher(firstname.getText().toString());
		return matcher.matches();
	}
	
	// Check to make sure last name is in valid format
	private boolean checkValidLastname() {
		pattern = Pattern.compile(NAME_PATTERN);
		matcher = pattern.matcher(lastname.getText().toString());
		return matcher.matches();
	}
	
	// Check to make sure phone number is in valid format
	private boolean checkValidPhone() {
		pattern = Pattern.compile(PHONE_PATTERN);
		matcher = pattern.matcher(phone.getText().toString());
		return matcher.matches();
	}
	
	// Check to make sure the email is in valid format
	private boolean checkValidEmail() {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email.getText().toString());
		return matcher.matches();
	}
}
