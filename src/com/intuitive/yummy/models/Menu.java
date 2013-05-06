package com.intuitive.yummy.models;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.os.Parcel;

//Contain menu information includes a list of menu items
public class Menu implements Model {
	/**
	 * 
	 */
	private static final long serialVersionUId = 1L;

	private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();

	private int id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public ArrayList<MenuItem> getMenuItems() {
		return menuItems;
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

	public MenuItem get(int position) {
		return menuItems.get(position);
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
	@Override
	public HashMap<String, String> getPostData() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

