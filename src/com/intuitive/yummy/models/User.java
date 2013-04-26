package com.intuitive.yummy.models;

import java.sql.Timestamp;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.util.Log;

public class User implements Model{
	/**
	 * TODO this is default
	 */
	private static final long serialVersionUID = 1L;
	
	// what the model is referred to on the backend
	private static final String modelName = "User";

	private int id = -1;
	private int vendorId = -1;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private UserAccountType accountType;
	private Timestamp dateCreated;
	private Timestamp dateLastModified;
	private Boolean isDeleted;
	
	public static enum UserAccountType{
		CUSTOMER(0), VENDOR_EMPLOYEE(1), VENDOR_ADMIN(2);
	    
		private final int value;
	    private UserAccountType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	    
	    public static UserAccountType parseInt(int i) {
	        switch(i) {
	        case 0:
	            return CUSTOMER;
	        case 1:
	            return VENDOR_EMPLOYEE;
	        case 2:
	        	return VENDOR_ADMIN;
	        default:
	        	return null;
	        }
	    }
	};
	
	//Constructor
	public User (String username, String firstName, String lastName, String email) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	@Override
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserAccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(UserAccountType accountType) {
		this.accountType = accountType;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Timestamp getDateLastModified() {
		return dateLastModified;
	}
	public void setDateLastModified(Timestamp dateLastModified) {
		this.dateLastModified = dateLastModified;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Model createFromParcel(Parcel arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Model[] newArray(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void parseJson(JSONObject json) {		
		try {
			id = json.getInt("id");
			vendorId = json.getInt("vendor_id");
			username = json.getString("username");
			firstName = json.getString("first_name");
			lastName = json.getString("last_name");
			email = json.getString("email");
			accountType = UserAccountType.parseInt(json.getInt("account_type"));
			dateCreated = Timestamp.valueOf(json.getString("date_created"));
			dateLastModified = Timestamp.valueOf(json.getString("date_last_modified "));
			isDeleted = json.getBoolean("deleted");
		} catch (JSONException e) {
			Log.e("Yummy", "JSON object did not map to User object.");
			e.printStackTrace();
		}
	}
	@Override
	public String getModelName() {
		return modelName;
	}
	@Override
	public HashMap<String, String> getPostData() {
		HashMap<String, String> postData = new HashMap<String, String>();

		if(id != -1) postData.put("id", String.valueOf(id));
		if(vendorId != -1) postData.put("id", String.valueOf(vendorId));
		if(firstName != null) postData.put("first_name", firstName);
		if(lastName != null) postData.put("last_name", lastName);
		if(email != null) postData.put("email", email);
		postData.put("account_type", String.valueOf(accountType.getValue()));
		
		// TODO test these timestamps compatibility with CakePHP backend
		if(dateCreated != null) postData.put("date_created", dateCreated.toString());
		if(dateLastModified != null) postData.put("date_last_modified", dateLastModified.toString());
		if(isDeleted != null) postData.put("deleted", isDeleted ? "true" : "false");
		
		return postData;
	}
}
