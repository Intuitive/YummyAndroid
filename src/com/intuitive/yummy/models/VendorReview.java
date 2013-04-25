package com.intuitive.yummy.models;

import java.sql.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

//contain comment and star rating for each review
public class VendorReview implements Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String modelName = "VendorReview";
	private int id;
	private int userID;
	private String title;
	private String description;
	private int rating;
	private Date dateCreated;
	private int vendorID;
	private Boolean isDeleted;
	
	public VendorReview() {}

	public VendorReview(String title, String description, int rating)
	{
		this.title = title;
		this.description = description;
		this.rating = rating;
	}
	
	public VendorReview(int ID, int userID, String title, int vendorID, String description, int rating)
	{
		this.id = ID;
		this.userID = userID;
		this.title = title;
		this.vendorID = vendorID;
		this.description = description;
		this.rating = rating;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	@Override
	public int getId() 
	{
		return id;
	}
	public void setUserID(int userID)
	{
		this.userID = userID;
	}
	public int getUserID()
	{
		return userID;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getTitle()
	{
		return title;
	}
	public void setVendorID(int vendorID)
	{
		this.vendorID = vendorID;
	}
	public int getVendorID()
	{
		return vendorID;
	}
	public void setComment(String description)
	{
		this.description = description;
	}
	public String getComment()
	{
		return description;
	}
	public void setStar(int rating)
	{
		this.rating = rating;
	}
	public int getStar()
	{
		return rating;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(id);
		out.writeInt(userID);
		if (title == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeString(title);
		}
		if (description == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeString(description);
		}
		out.writeInt(1);
		out.writeInt(rating);
		if(dateCreated == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(dateCreated.toString());
		}
		out.writeInt(vendorID);
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
		// TODO Auto-generated method stub
		return new VendorReview(parcel);
	}
	
	public VendorReview(Parcel parcel)
	{
		id = parcel.readInt();
		userID = parcel.readInt();
		if (parcel.readInt() == 1)
			title = parcel.readString();
		if (parcel.readInt() == 1)
			description = parcel.readString();
		if (parcel.readInt() == 1)
			rating = parcel.readInt();
		if (parcel.readInt() == 1)
			dateCreated = Date.valueOf(parcel.readString());
		vendorID = parcel.readInt();
		if (parcel.readInt() == 1)
			isDeleted = parcel.readInt() == 1;
	}
	
	public static final Parcelable.Creator<VendorReview> CREATOR = new Creator<VendorReview>() {

	    public VendorReview createFromParcel(Parcel source) {
	        return new VendorReview(source);
	    }

	    public VendorReview[] newArray(int size) {
	        return new VendorReview[size];
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
			title = json.getString("title");
			vendorID = json.getInt("vendor_id");
			description = json.getString("description");
			rating = json.getInt("rating");
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
		// TODO Auto-generated method stub
		return null;
	}
}
