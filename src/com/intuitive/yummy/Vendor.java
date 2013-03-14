package com.intuitive.yummy;

import java.io.Serializable;

//Contain vendor's information

public class Vendor implements Serializable {
	private int id;
	private String name;
	private String description;
	private String address;
	private int[][] hours;
	private boolean status;
	private String pictureURL;
	private Menu menu;
	
	public void setID(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setHours(int[][] hours) {
		this.hours = hours;
	}
	public void openTruck() {
		status = true;
	}
	public void closeTruck() {
		status = false;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public int getID() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getAddress() {
		return address;
	}
	public int[][] getHours() {
		return hours;
	}
	public boolean getStatus() {
		return status;
	}
	public String getPictureURL() {
		return pictureURL;
	}
	public Menu getMenu() {
		return menu;
	}
	
	//constructors
	public Vendor(){}
	
	public Vendor (int id, String name, String description, String address, int[][] hours, boolean status, String pictureURL, Menu menu) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.hours = hours;
		this.status = status;
		this.pictureURL = pictureURL;
		this.menu = menu;
	}
	
	public String toString(){
		return name;
	}
}
