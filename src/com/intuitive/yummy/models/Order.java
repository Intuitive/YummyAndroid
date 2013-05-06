package com.intuitive.yummy.models;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Order implements Model {
	
	/**
	 * TODO this is default UId
	 */
	private static final long serialVersionUId = 1L;
	private static final String modelName = "Order";
	
	private Integer id;
	private Integer userId;
	private Integer vendorId; 	
	private Integer waitTime;
	private Double totalPrice;
	private Integer paymentMethod;
	private OrderStatus status = OrderStatus.IN_PROGRESS;
	
	public enum OrderStatus { 
		IN_PROGRESS(0), FULLFILLED(1) ;
		
		private final int value;
	    private OrderStatus(int value) {
	        this.value = value;
	    }
	    public int getValue() {
	        return value;
	    }
	};

	public Order(){}
	public Order(Integer id, Integer duration, OrderStatus status, Integer waitTime){
		this.id = id;
		this.waitTime = waitTime;
		this.status = status;
	}
	
	public Order(Parcel parcel) {
		// properties must be read in the same order they're written out in writeToParcel
		if (parcel.readInt() == 1)
			id = parcel.readInt();
		if (parcel.readInt() == 1)
			userId = parcel.readInt();
		if (parcel.readInt() == 1)
			vendorId = parcel.readInt();
		if (parcel.readInt() == 1)
			waitTime = parcel.readInt();
		if (parcel.readInt() == 1)
			totalPrice = parcel.readDouble();
		if (parcel.readInt() == 1)
			paymentMethod = parcel.readInt();
		if (parcel.readInt() == 1)
			status = parcel.readInt() == 0 ? OrderStatus.IN_PROGRESS : OrderStatus.FULLFILLED;
		
	}

	@Override
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(Integer waitTime) {
		this.waitTime = waitTime;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
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
		if (userId == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeInt(userId);
		}
		if (vendorId == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeInt(vendorId);
		}
		if (waitTime == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeInt(waitTime);
		}
		if (totalPrice == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeDouble(totalPrice);
		}
		if (paymentMethod == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeInt(paymentMethod);
		}
		if (status == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeInt(status == OrderStatus.IN_PROGRESS ? 0 : 1);
		}
	}
	
	@Override
	public Model createFromParcel(Parcel parcel) {
		return new Order(parcel);
	}
	
	@Override
	public Model[] newArray(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void parseJson(JSONObject json) {
		try {
			// 
			id = json.getInt("id");
			userId = json.getInt("user_id");
			vendorId = json.getInt("vendor_id");
			waitTime = json.getInt("wait_time");
			totalPrice = json.getDouble("total_price");
			paymentMethod = json.getInt("payment_method");
			status = json.getInt("status") == 0 ? OrderStatus.IN_PROGRESS : OrderStatus.FULLFILLED;
			
		} catch (JSONException e) {
			Log.e("Yummy", "JSON object did not map to Order object.");
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
		if(userId != null) postData.put("user_id", String.valueOf(userId));
		if(vendorId != null) postData.put("vendor_id", String.valueOf(vendorId));
		if(waitTime != null) postData.put("wait_time", String.valueOf(waitTime));
		if(totalPrice != null) postData.put("total_price", String.valueOf(totalPrice));
		if(paymentMethod != null) postData.put("payment_method", String.valueOf(paymentMethod));
		
		String s_status = status == OrderStatus.IN_PROGRESS ? "0" : "1";
		postData.put("status", s_status);
		
		return postData;
	}
	
	public static final Parcelable.Creator<Order> CREATOR = new Creator<Order>() {

	    public Order createFromParcel(Parcel source) {
	        return new Order(source);
	    }

	    public Order[] newArray(int size) {
	        return new Order[size];
	    }

	};
	
}
