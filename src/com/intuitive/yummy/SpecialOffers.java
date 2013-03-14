package com.intuitive.yummy;

import java.io.Serializable;

public class SpecialOffers implements Serializable {
	private MenuItem menuItem;
	private double Price = 0;
	private int Duration = 0;
	
	//Set MenuItem on a Special Offer
	public void setMenuItem(MenuItem Item){
		menuItem = Item;
	}
	
	//Set price of Special Offer
	public void setPrice(double price){
		Price = price;
	}
	
	//Set duration of Special Offer
	public void setDuration(int duration){
		Duration = duration;
	}
	
	//Return MenuItem of Special Offer
	public MenuItem getMenuItem(){
		return menuItem;
	}
	
	//Return price of Special Offer
	public double getPrice(){
		return Price;
	}
	
	//Return duration of Special Offer
	public int getDuration(){
		return Duration;
	}
	
	//Constructor
	public SpecialOffers(MenuItem Item, double price, int duration){
		menuItem = Item;
		Price = price;
		Duration = duration;
	}
}
