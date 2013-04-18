package com.intuitive.yummy.models;

import java.io.Serializable;
import java.util.ArrayList;

//includes menu item information, includes their ingredients' information

public class MenuItem implements Serializable {
	private int id;
	private String name;
	private double price;
	private String category;
	private String description;
	private boolean availability;
	private String pictureURL;
	private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
	
	public void setID(int id) {
		this.id = id;
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
		this.pictureURL = pictureURL;
	}
	public void setIngredient(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	public void addIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
	}
	public void removeIngredient(Ingredient ingredient) {
		ingredients.remove(ingredient);
	}
	public int getID() {
		return id;
	}
	public String getName() {
		return name;
	}
	public double getPrice() {
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
		return pictureURL;
	}
	public ArrayList<Ingredient> getIngredient() {
		return ingredients;
	}
	
	public MenuItem(int id, String name, double price, String category, String description, boolean availability, String pictureURL, ArrayList<Ingredient> ingredients) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.description = description;
		this.availability = availability;
		this.pictureURL = pictureURL;
		this.ingredients = ingredients;
	}
	
}
