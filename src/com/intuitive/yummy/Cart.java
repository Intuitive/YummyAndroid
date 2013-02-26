package com.intuitive.yummy;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
	
	private ArrayList<MenuItem> items = new ArrayList<MenuItem>();
	private double totalPrice = 0;
	
	//Update the total price of all items
	private void update() {
		totalPrice = 0;
		
		for (int i = 0; i < items.size(); i++) {
			totalPrice += items.get(i).getPrice();
		}
	}
	//Add an item to the cart
	public void addItem(MenuItem item){
		items.add(item);
		update();
	}
	//Remove an item from the cart
	public void removeItem(MenuItem item) {
		for (int i = 0; i < items.size(); i++)
		{
			if (items.get(i).getName().equals(item.getName()))
				items.remove(i);
		}
		update();
	}
	//Return the items in the cart
	public ArrayList<MenuItem> getItems(){
		return items;
	}
	//Return the total price
	public double getTotalPrice(){
		return totalPrice;
	}
	//Remove all items from the cart
	public void removeAllItems(){
		items.clear();
		totalPrice = 0;
	}
	
	//Constructor
	Cart(ArrayList<MenuItem> items){
		this.items = items;
		update();
	}
}
