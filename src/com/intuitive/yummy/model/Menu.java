package com.intuitive.yummy.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;

import android.os.Parcel;

//Contain menu information includes a list of menu items

public class Menu implements Serializable, Model {
	private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setMenuItem(ArrayList<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	public void addMenuItem(MenuItem menuItem) {
		menuItems.add(menuItem);
	}
	public void removeMenuItem(MenuItem menuItem) {
		menuItems.remove(menuItem);
	}
	public ArrayList<MenuItem> getMenuItem() {
		return menuItems;
	}
	
	public Menu(){
		
	}
	
	public Menu(ArrayList<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Model createFromParcel(Parcel source) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Model[] newArray(int size) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void parseJson(JSONObject json) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getModelName() {
		// TODO Auto-generated method stub
		return null;
	}
}
