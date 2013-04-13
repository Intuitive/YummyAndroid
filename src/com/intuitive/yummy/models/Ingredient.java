package com.intuitive.yummy.models;

// Contain ingredient information
public class Ingredient {
	private String name;
	private double price;
	
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}

	// Constructor
	Ingredient(String name, double price) {
		this.name = name;
		this.price = price;
	}

}
