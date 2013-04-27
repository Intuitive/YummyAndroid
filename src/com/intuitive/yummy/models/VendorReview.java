package com.intuitive.yummy.models;

import java.sql.Timestamp;
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
	private Integer id;
	private Integer userId;
	private Integer vendorId;
	private String title;
	private String description;
	private Integer rating;
	private Timestamp dateCreated;
	private Boolean isDeleted;
	
	public VendorReview() {}

	public VendorReview(String title, String description, Integer rating)
	{
		this.title = title;
		this.description = description;
		this.rating = rating;
	}
	
	public VendorReview(Integer id, Integer userId, String title, Integer vendorID, String description, Integer rating)
	{
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.vendorId = vendorID;
		this.description = description;
		this.rating = rating;
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	@Override
	public Integer getId() 
	{
		return id;
	}
	public void setUserID(Integer userID)
	{
		this.userId = userID;
	}
	public Integer getUserID()
	{
		return userId;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getTitle()
	{
		return title;
	}
	public void setVendorID(Integer vendorID)
	{
		this.vendorId = vendorID;
	}
	public Integer getVendorID()
	{
		return vendorId;
	}
	public void setComment(String description)
	{
		this.description = description;
	}
	public String getComment()
	{
		return description;
	}
	public void setStar(Integer rating)
	{
		this.rating = rating;
	}
	public Integer getStar()
	{
		return rating;
	}
	
	@Override
	public int describeContents() {
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
		
		if (userId == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeInt(userId);
		}
		
		if(vendorId == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeInt(vendorId);
		}
		
		if (title == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(title);
		}
		
		if (description == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(description);
		}
		
		if (rating == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeInt(rating);
		}
		
		if(dateCreated == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(dateCreated.toString());
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
		return new VendorReview(parcel);
	}
	
	public VendorReview(Parcel parcel)
	{
		if (parcel.readInt() == 1)
			id = parcel.readInt();
		if (parcel.readInt() == 1)
			userId = parcel.readInt();
		if (parcel.readInt() == 1)
			vendorId = parcel.readInt();
		if (parcel.readInt() == 1)
			title = parcel.readString();
		if (parcel.readInt() == 1)
			description = parcel.readString();
		if (parcel.readInt() == 1)
			rating = parcel.readInt();
		if (parcel.readInt() == 1)
			dateCreated = Timestamp.valueOf(parcel.readString());
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
			vendorId = json.getInt("vendor_id");
			userId = json.getInt("user_id");
			title = json.getString("title");
			description = json.getString("description");
			rating = json.getInt("rating");
			dateCreated = Timestamp.valueOf(json.getString("date_created"));
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
		if(userId != null) postData.put("user_id", String.valueOf(userId));
		if(title != null) postData.put("title", title);
		if(description != null) postData.put("description", description);
		if(rating != null) postData.put("rating", String.valueOf(rating));
		if(dateCreated != null) postData.put("date_created", dateCreated.toString());
		if(isDeleted != null) postData.put("deleted", isDeleted ? "true" : "false");
		
		return postData;
	}
}
