package com.intuitive.yummy.models;

import java.sql.Timestamp;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.intuitive.yummy.models.Order.OrderStatus;
import com.intuitive.yummy.webservices.IntentExtraKeys;
import com.intuitive.yummy.webservices.RestResponseReceiver;
import com.intuitive.yummy.webservices.RestService;
import com.intuitive.yummy.webservices.RestService.Action;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.util.Log;

public class User implements Model{
	/**
	 * TODO this is default
	 */
	private static final long serialVersionUId = 1L;
	
	// what the model is referred to on the backend
	private static final String modelName = "User";

	private Integer id;
	private Integer vendorId;
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

	public enum UserType{			
		CUSTOMER,
		EMPLOYEE,
		ADMIN;
	}
	
	public User(){}
	
	public User (String username, String firstName, String lastName, String email) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	@Override
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
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
	public void writeToParcel(Parcel out, int flags) {
		
		if (id == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeInt(id);
		}
		
		if (vendorId == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeInt(vendorId);
		}
		
		if (username == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(username);
		}
		
		if (firstName == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(firstName);
		}
		
		if (firstName == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(firstName);
		}
		
		if (lastName == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(lastName);
		}
		
		if (email == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(email);
		}
		
		if (email == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(email);
		}
		
		if (accountType == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeInt(accountType.getValue());
		}
		
		if (dateCreated == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(dateCreated.toString());
		}
		
		if (dateLastModified == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(dateLastModified.toString());
		}
		
		if (isDeleted == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeInt(isDeleted ? 1 : 0);
		}
		
	}
	@Override
	public Model createFromParcel(Parcel parcel) {
		return new User(parcel);
	}
	private User(Parcel parcel) {
		if (parcel.readInt() == 1)
			id = parcel.readInt();
		if (parcel.readInt() == 1)
			vendorId = parcel.readInt();
		if (parcel.readInt() == 1)
			username = parcel.readString();
		if (parcel.readInt() == 1)
			firstName = parcel.readString();
		if (parcel.readInt() == 1)
			lastName = parcel.readString();
		if (parcel.readInt() == 1)
			email = parcel.readString();
		if (parcel.readInt() == 1)
			accountType = UserAccountType.parseInt(parcel.readInt());
		
		if (parcel.readInt() == 1){
			try{
				dateCreated = Timestamp.valueOf(parcel.readString());
			}catch(IllegalArgumentException e){
				dateCreated = null;
			}
		}
		
		if (parcel.readInt() == 1){
			try{
				dateLastModified= Timestamp.valueOf(parcel.readString());
			}catch(IllegalArgumentException e){
				dateLastModified = null;
			}
		}
		
		if (parcel.readInt() == 1)
			isDeleted = parcel.readInt() == 1;
	}
	@Override
	public Model[] newArray(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void parseJson(JSONObject json) {		
		try {
			id = json.isNull("id") ? null : json.getInt("id");
			vendorId =  json.isNull("vendor_id") ? null : json.getInt("vendor_id");
			username = json.getString("username");
			firstName = json.getString("first_name");
			lastName = json.getString("last_name");
			email = json.getString("email");
			accountType = UserAccountType.parseInt(json.getInt("account_type"));
			dateCreated = Timestamp.valueOf(json.getString("date_created"));
			dateLastModified = Timestamp.valueOf(json.getString("date_last_modified"));
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

		if(id != null) postData.put("id", String.valueOf(id));
		if(vendorId != null) postData.put("id", String.valueOf(vendorId));
		if(firstName != null) postData.put("first_name", firstName);
		if(lastName != null) postData.put("last_name", lastName);
		if(email != null) postData.put("email", email);
		postData.put("account_type", String.valueOf(accountType.getValue()));
		if(dateCreated != null) postData.put("date_created", dateCreated.toString());
		if(dateLastModified != null) postData.put("date_last_modified", dateLastModified.toString());
		if(isDeleted != null) postData.put("deleted", isDeleted ? "true" : "false");
		
		return postData;
	}
	
	public static Intent getUsersIntent(Integer userId, String username, Activity activity, RestResponseReceiver receiver){
		
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, activity, RestService.class);
		intent.putExtra(IntentExtraKeys.RECEIVER, receiver);
		intent.putExtra(IntentExtraKeys.ACTION, Action.READSINGLE);
		intent.putExtra(IntentExtraKeys.MODEL_CLASS, User.class);
		
		// -1's must be used rather than NULL's
		if(userId == null)
			intent.putExtra(IntentExtraKeys.PARAMETER1, "-1");
		else
			intent.putExtra(IntentExtraKeys.PARAMETER1, String.valueOf(userId));
		
		if(username != null)
			intent.putExtra(IntentExtraKeys.PARAMETER2, username);

		
		return intent;
	}
}
