package com.intuitive.yummy.models;

import java.sql.Timestamp;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.intuitive.yummy.webservices.IntentExtraKeys;
import com.intuitive.yummy.webservices.RestResponseReceiver;
import com.intuitive.yummy.webservices.RestService;
import com.intuitive.yummy.webservices.RestService.Action;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Order implements Model {
	
	/**
	 * TODO this is default UId
	 */
	private static final long serialVersionUID = 1L;
	private static final String modelName = "Order";
	
	private Integer id;
	private Integer userId;
	private Integer vendorId; 	
	private Integer waitTime;
	private Double totalPrice;
	private Integer paymentMethod;
	private Integer duration;
	private Timestamp dateCreated;
	private OrderStatus status = OrderStatus.IN_PROGRESS;
	
	public enum OrderStatus { 
		IN_PROGRESS(0){
			@Override
            public String toString() {
				return "IN_PROGRESS";
			}
		},
		
		FULFILLED(1){
			public String toString() {
				return "FULFILLED";
			}
		};
		
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
		this.waitTime = waitTime;
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
			duration = parcel.readInt();
		if(parcel.readInt() == 1){
			try{
				dateCreated = Timestamp.valueOf(parcel.readString());
			}catch(IllegalArgumentException e){
				dateCreated = null;
			}
		}
		if (parcel.readInt() == 1)
			status = parcel.readInt() == 0 ? OrderStatus.IN_PROGRESS : OrderStatus.FULFILLED;
		
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
	
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
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
		if (paymentMethod == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeInt(duration);
		}
		if(dateCreated == null)
			out.writeInt(0);
		else{
			out.writeInt(1);
			out.writeString(dateCreated.toString());
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
			status = json.getInt("status") == 0 ? OrderStatus.IN_PROGRESS : OrderStatus.FULFILLED;
			duration = json.optInt("duration", 0);
			dateCreated = Timestamp.valueOf(json.getString("date_created"));
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
		if(duration != null) postData.put("payment_method", String.valueOf(duration));
		if(dateCreated != null) postData.put("date_created", dateCreated.toString());
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
	
	/**
	 * Get an intent to instruct the RestService to get an Order based on the criteria passed.
	 * Do NOT use to get all orders. Instead, use the RestService.getReadManyIntent method.
	 * @param vendorId
	 * @param userId
	 * @param orderStatus
	 * @param activity
	 * @param receiver
	 * @return
	 */
	public static Intent getOrdersIntent(Integer vendorId, Integer userId, OrderStatus orderStatus, Activity activity, RestResponseReceiver receiver){
		
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, activity, RestService.class);
		intent.putExtra(IntentExtraKeys.RECEIVER, receiver);
		intent.putExtra(IntentExtraKeys.ACTION, Action.READALL);
		
		intent.putExtra(IntentExtraKeys.MODEL_CLASS, Order.class);
		
		// -1's must be used rather than NULL's
		if(vendorId == null)
			intent.putExtra(IntentExtraKeys.PARAMETER1, "-1");
		else
			intent.putExtra(IntentExtraKeys.PARAMETER1, String.valueOf(vendorId));
		
		if(userId == null)
			intent.putExtra(IntentExtraKeys.PARAMETER2, "-1");
		else
			intent.putExtra(IntentExtraKeys.PARAMETER2, String.valueOf(userId));

		
		if(orderStatus == null)
			intent.putExtra(IntentExtraKeys.PARAMETER3, "-1");
		else
			intent.putExtra(IntentExtraKeys.PARAMETER3, String.valueOf(orderStatus.getValue()));
		
		return intent;
	}
}
