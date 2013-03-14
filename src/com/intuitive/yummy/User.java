package com.intuitive.yummy;

//Contain user information

public class User {
	private String username;		//The username of the user
	private String firstName;		//User First Name
	private String lastName;		//User Last Name
	private String email;			//User Email Address
	private int phoneNumber;		//User Phone Number	
	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUsername() {
		return username;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	
	//Constructor
	public User (String username, String firstName, String lastName, String email, int phoneNumber) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
}
