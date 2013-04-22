package com.intuitive.yummy.webservices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;

public class PostParameter implements NameValuePair, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	public PostParameter(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public static ArrayList<PostParameter> hashMapToNameValuePairs(HashMap<String, String> map){
		
		if(map == null) return null;
		
		ArrayList<PostParameter> list = new ArrayList<PostParameter>(); 
		
		for (HashMap.Entry<String, String> entry : map.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    list.add(new PostParameter(key, value));
		}
		
		return list;
	}
}
