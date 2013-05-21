package com.intuitive.yummy.models;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

//includes menu item information, includes their ingredients' information

public class OrderItem implements Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String modelName = "OrderItem";
	
	private Integer id;
	private Integer menuItemId;
	private String name;
	private Double price;
	private String specialInstructions;
	private Integer quantity;
	
	@Override
	public Integer getId() {
		return id;
	}
	public Integer getMenuItemId() {
		return menuItemId;
	}
	public String getName() {
		return name;
	}
	public Double getPrice() {
		return price;
	}
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setMenuItemId(Integer menuItemId) {
		this.menuItemId = menuItemId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
	public OrderItem() {};
	public OrderItem(Integer id, Integer menuItemId, String name, 
			Double price, String specialInstructions, Integer quantity) {
		this.id = id;
		this.menuItemId = menuItemId;
		this.name = name;
		this.price = price;
		this.specialInstructions = specialInstructions;
		this.quantity = quantity;
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
		if (menuItemId == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeInt(menuItemId);
		}
		if(name == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeString(name);
		}
		if(price == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeDouble(price);
		}
		if(specialInstructions == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeString(specialInstructions);
		}
		if (quantity == null)
			out.writeInt(0);
		else
		{
			out.writeInt(1);
			out.writeInt(quantity);
		}
	}
	@Override
	public Model createFromParcel(Parcel parcel) {
		return new OrderItem(parcel);
	}
	
	public OrderItem(Parcel parcel) {
		if(parcel.readInt() == 1)
			id = parcel.readInt();
		if(parcel.readInt() == 1)
			menuItemId = parcel.readInt();
		if(parcel.readInt() == 1)
			name = parcel.readString();
		if(parcel.readInt() == 1)
			price = parcel.readDouble();
		if(parcel.readInt() == 1)
			specialInstructions = parcel.readString();
		if(parcel.readInt() == 1)
			quantity = parcel.readInt();
	}
	
	public static final Parcelable.Creator<OrderItem> CREATOR = new Creator<OrderItem> () {
		public OrderItem createFromParcel(Parcel source) {
			return new OrderItem(source);
		}
		public OrderItem[] newArray(int size) {
			return new OrderItem[size];
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
			menuItemId = json.getInt("menu_item_id");
			name = json.getString("name");
			price = json.getDouble("price");
			specialInstructions = json.getString("special_instructions");
			quantity = json.getInt("quantity");
		} catch (JSONException e) {
			Log.e("Yummy", "JSON object did not map to OrderItem object.");
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
		if(menuItemId != null) postData.put("menu_item_id", String.valueOf(menuItemId));
		if(name != null) postData.put("name", name);
		if(price != null) postData.put("price", String.valueOf(price));
		if(specialInstructions!= null) postData.put("special_instructions", specialInstructions);
		if(quantity != null) postData.put("quantity", String.valueOf(quantity));
		
		return postData;
	}
	
}
