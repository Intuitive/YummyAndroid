package com.intuitive.yummy.models;

import java.sql.Timestamp;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

//includes menu item information, includes their ingredients' information

public class MenuItem implements Model {
	/**
	 * \
	 */
	private static final long serialVersionUId = 1L;
	
	private static final String modelName = "MenuItem";
	private Integer id;
	private Integer vendorId;
	private String name;
	private Double price;
	private String category;
	private String description;
	private boolean availability;
	private String pictureUrl;
	private Timestamp dateCreated;
	private Timestamp dateLastModified;
	private Boolean isDeleted;
	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureUrl = pictureURL;
	}
	@Override
	public Integer getId() {
		return id;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public String getName() {
		return name;
	}
	public Double getPrice() {
		return price;
	}
	public String getCategory() {
		return category;
	}
	public String getDescription() {
		return description;
	}
	public boolean getAvailability() {
		return availability;
	}
	public String getPictureURL() {
		return pictureUrl;
	}
	
	public MenuItem() {};
	public MenuItem(Integer id, Integer vendorId, String name, Double price, String category, String description, boolean availability, String pictureURL) {
		this.id = id;
		this.vendorId = vendorId;
		this.name = name;
		this.price = price;
		this.category = category;
		this.description = description;
		this.availability = availability;
		this.pictureUrl = pictureURL;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		if (id == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeInt(id);
		}
		if (vendorId == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeInt(vendorId);
		}
		if(name == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeString(name);
		}
		if(category == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeString(category);
		}
		
		if(price == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeDouble(price);
		}
		if(description == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeString(description);
		}
		if(availability)
			out.writeInt(1);
		else
			out.writeInt(0);
		if(pictureUrl == null)
			out.writeInt(0);
		else
		{
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
			out.writeInt(isDeleted ? 1 : 0);
		}
	}
	@Override
	public Model createFromParcel(Parcel parcel) {
		return new MenuItem(parcel);
	}
	
	public MenuItem(Parcel parcel) {
		if(parcel.readInt() == 1)
			id = parcel.readInt();
		if(parcel.readInt() == 1)
			vendorId = parcel.readInt();
		if(parcel.readInt() == 1)
			name = parcel.readString();
		if(parcel.readInt() == 1)
			category = parcel.readString();
		if(parcel.readInt() == 1)
			price = parcel.readDouble();
		if(parcel.readInt() == 1)
			description = parcel.readString();
		if(parcel.readInt() == 1)
			availability = true;
		else
			availability = false;
		if(parcel.readInt() == 1)
			pictureUrl = parcel.readString();
		
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
		
		if(parcel.readInt() == 1)
			isDeleted = parcel.readInt() == 1;
	}
	
	public static final Parcelable.Creator<MenuItem> CREATOR = new Creator<MenuItem> () {
		public MenuItem createFromParcel(Parcel source) {
			return new MenuItem(source);
		}
		public MenuItem[] newArray(int size) {
			return new MenuItem[size];
		}
	};
	
	@Override
	public Model[] newArray(int size) {
		return null;
	}
	@Override
	public void parseJson(JSONObject json) {
		try {
			id = json.getInt("id");
			vendorId = json.getInt("vendor_id");
			name = json.getString("name");
			category = json.getString("category");
			price = json.getDouble("price");
			description = json.getString("description");
			availability = json.getBoolean("available");
			pictureUrl = json.getString("picture_url");
			dateCreated = Timestamp.valueOf(json.getString("date_created"));
			dateLastModified = Timestamp.valueOf(json.getString("date_last_modified"));
			isDeleted = json.getBoolean("deleted");
		} catch (JSONException e) {
			Log.e("Yummy", "JSON object did not map to Vendor object.");
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
		if(vendorId != null) postData.put("vendor_id", String.valueOf(vendorId));
		if(name != null) postData.put("name", name);
		if(category != null) postData.put("category", category);
		if(price != null) postData.put("price", String.valueOf(price));
		if(description!= null) postData.put("description", description);
		postData.put("availability", availability ? "true" : "false");
		if(pictureUrl != null) postData.put("picture_url", pictureUrl);
		if(dateCreated != null) postData.put("date_created", dateCreated.toString());
		if(dateLastModified != null) postData.put("date_last_modified", dateLastModified.toString());
		if(isDeleted != null) postData.put("deleted", isDeleted ? "true" : "false");
		
		return postData;
	}
	
	
}
