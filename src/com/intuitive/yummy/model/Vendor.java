package com.intuitive.yummy.model;

import java.io.Serializable;
import java.sql.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.util.Log;

//Contain vendor's information

public class Vendor implements Serializable, Model {
	
	/**
	 * TODO this is just default
	 */
	private static final long serialVersionUID = 1L;
	
	// what the model is referred to on the backend
	private static final String modelName = "Vendor";
	
	private int id;
	private String name;
	private String description;
	private String address;
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
	public Vendor(){}
	
	public Vendor(Parcel parcel){
		id = parcel.readInt();
		name = parcel.readString();
		description = parcel.readString();
		status = parcel.readInt() == 1 ? VendorStatus.OPEN : VendorStatus.CLOSED; 
		menu = parcel.readParcelable(Menu.class.getClassLoader());
		dateCreated = Date.valueOf(parcel.readString());
		dateLastModified = Date.valueOf(parcel.readString());
		isDeleted = parcel.readInt() == 1;
	}
	
	public Vendor (
			int id, 
			String name, 
			String description, 
			String address, 
			int[][] hours, 
			VendorStatus status, 
			String pictureUrl, 
			Menu menu) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.hours = hours;
		this.status = status;
		this.pictureUrl = pictureUrl;
		this.menu = menu;
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
			address = json.getString("address");
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
	public void setAddress(String address) {
		this.address = address;
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
	public int getID() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getAddress() {
		return address;
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
		// TODO Auto-generated method stub
		out.writeInt(id);
		out.writeString(name);
		out.writeString(description);
		out.writeString(address);
		
		if(status == VendorStatus.OPEN)
			out.writeInt(1);
		else
			out.writeInt(0);
		
		out.writeString(pictureUrl);
		out.writeParcelable(menu, 0); // TODO look into flag here
		out.writeString(dateCreated.toString());
		out.writeString(dateLastModified.toString());
		
		if(isDeleted)
			out.writeInt(1);
		else
			out.writeInt(0);
		
	}


	@Override
	public Model createFromParcel(Parcel parcel) {
		return new Vendor(parcel);
	}


	@Override
	public Model[] newArray(int size) {
		return null;
	}	
}
