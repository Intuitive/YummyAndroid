package com.intuitive.yummy;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
	private int id;
	private int duration = 0;  
	private ArrayList<MenuItem> items = new ArrayList<MenuItem>();
	private double totalPrice = 0;
	private int status;
	
	//Set the order ID
	public void setId(int id) {
		this.id = id;
	}
	//Return the order ID
	public int getOrderId(){
		return id;
	}
	//Set the wait time of the order
	public void setDuration(int duration){
		this.duration = duration;
	}
	//Return how long the order will take
	public int getDuration(){
		return duration;
	}
	//Return the items of the order
	public ArrayList<MenuItem> getOrderItems(){
		return items;
	}
	//Set the order status
	public void setStatus(int status) {
		this.status = status;
	}
	//Return the status of the order
	public int getStatus(){
		return status;
	}
	//Return the total price of the order
	public double getTotalOrderPrice(){
		return totalPrice;
	}
	
	//Constructor
	Order(int id, int duration, int status,ArrayList<MenuItem> items){
		this.id = id;
		this.duration = duration;
		this.status = status;
		this.items = items;
	}
}
