package com.intuitive.yummy;

import java.io.Serializable;
import java.util.ArrayList;

//Contain menu information includes a list of menu items

public class Menu implements Serializable {
	private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	
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

}
