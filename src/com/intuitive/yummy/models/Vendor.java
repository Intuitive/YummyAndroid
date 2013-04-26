package com.intuitive.yummy.models;

import java.sql.Date;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

//Contain vendor's information

public class Vendor implements Model {
	
	/**
	 * TODO this is  default
	 */
	private static final long serialVersionUID = 1L;
	
	// what the model is referred to on the backend
	private static final String modelName = "Vendor";
	
	private int id = -1;
	private String name;
	private String description;
	private String location;
	private int[][] hours;
	private VendorStatus status;
	private String pictureUrl;
	private Menu menu;
	private Date dateCreated;
	private Date dateLastModified;
	private Boolean isDeleted; // TODO consider renaming to isActive
	
	// used to make open and closed status' clearer
	public enum VendorStatus{
		CLOSED, 
		OPEN;
	}
	
	/* constructors */
	public Vendor (){}
	
	public Vendor (
			int id, 
			String name, 
			String description, 
			String location, 
			int[][] hours, 
			VendorStatus status, 
			String pictureUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
		this.hours = hours;
		this.status = status;
		this.pictureUrl = pictureUrl;
	}
	
	public Vendor (int id, String name, String description, VendorStatus status, String pictureURL) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.pictureUrl = pictureURL;
		this.isDeleted = false;
	}
	
	/**
	 * Creates new Vendor with id = -1. Use to create new Vendor objects to persist.
	 * @param name
	 * @param description
	 * @param status
	 * @param pictureURL
	 */
	public Vendor (String name, String description, VendorStatus status, String pictureURL) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.pictureUrl = pictureURL;
		this.isDeleted = false;
	}
	
	public String toString(){
		return name;
	}
	
	@Override
	public void parseJson(JSONObject json) {
		try {
			// TODO skipping hours & menu for now
			id = json.getInt("id");
			name = json.getString("name");
			description = json.getString("description");
			location = json.getString("location");
			Boolean isOpen = json.getBoolean("status");
			status = isOpen ? VendorStatus.OPEN : VendorStatus.CLOSED;
			pictureUrl = json.getString("picture_url");
		} catch (JSONException e) {
			Log.e("Yummy", "JSON object did not map to Vendor object.");
			e.printStackTrace();
		}
		
	}				
				
	/* setters */
	public void setID(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setHours(int[][] hours) {
		this.hours = hours;
	}
	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public void setStatus(VendorStatus status) {
		this.status = status;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	/* getters */
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getLocation() {
		return location;
	}
	public int[][] getHours() {
		return hours;
	}
	public VendorStatus getStatus() {
		return status;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public Menu getMenu() {
		return menu;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public Date getDateLastModified() {
		return dateLastModified;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public String getModelName(){
		return modelName;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void writeToParcel(Parcel out, int flags) {
		
		out.writeInt(id);
		
		// null values will be preceded by 0, non-null 1
		// in order to facilitate unmarshalling in parcel constructor
		if(name == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(name);
		}
		

		if(description == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(description);
		}

		if(location == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(location);
		}

		if(status == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			if(status == VendorStatus.OPEN)
				out.writeInt(1);
			else
				out.writeInt(0);
		}

		if(pictureUrl == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(pictureUrl);
		}


		if(dateCreated == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(dateCreated.toString());
		}

		if(dateLastModified == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(dateLastModified.toString());
		}

		if(isDeleted == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			if(isDeleted)
				out.writeInt(1);
			else
				out.writeInt(0);
		}
	}


	@Override
	public Model createFromParcel(Parcel parcel) {
		return new Vendor(parcel);
	}

	
	public Vendor(Parcel parcel){
		
		id = parcel.readInt();
		
		if(parcel.readInt() == 1)
			name = parcel.readString();
		
		if(parcel.readInt() == 1)
			description = parcel.readString();

		if(parcel.readInt() == 1)
			location = parcel.readString();
		
		if(parcel.readInt() == 1){
			status = parcel.readInt() == 1 ? VendorStatus.OPEN : VendorStatus.CLOSED;
		}
		
		if(parcel.readInt() == 1){
			pictureUrl = parcel.readString();
		}
		
		if(parcel.readInt() == 1)
			dateCreated = Date.valueOf(parcel.readString());

		if(parcel.readInt() == 1)
			dateLastModified = Date.valueOf(parcel.readString());

		if(parcel.readInt() == 1)
			isDeleted = parcel.readInt() == 1;
	}

	public static final Parcelable.Creator<Vendor> CREATOR = new Creator<Vendor>() {

	    public Vendor createFromParcel(Parcel source) {
	        return new Vendor(source);
	    }

	    public Vendor[] newArray(int size) {
	        return new Vendor[size];
	    }
	};
	
	
	@Override
	public Model[] newArray(int size) {
		return null;
	}

	@Override
	public HashMap<String, String> getPostData() {
		HashMap<String, String> postData = new HashMap<String, String>();
		
		if(id != -1) postData.put("id", String.valueOf(id));
		if(name != null) postData.put("name", name);
		if(description != null) postData.put("description", description);
		if(location != null) postData.put("location", location);
		if(status != null) postData.put("status", status == VendorStatus.OPEN ? "true" : "false");
		// TODO skipping Menu for now
		if(pictureUrl != null) postData.put("picture_url", pictureUrl);
		if(dateCreated != null) postData.put("date_created", dateCreated.toString());
		if(dateLastModified != null) postData.put("date_last_modified", dateLastModified.toString());
		if(isDeleted != null) postData.put("deleted", isDeleted ? "true" : "false");
		
		return postData;
	}	
	
	
}
