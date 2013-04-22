package com.intuitive.yummy.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.intuitive.yummy.webservices.PostParameter;

import android.os.Parcelable;

public interface Model extends Parcelable, Serializable, Parcelable.Creator<Model>{
 
	public void parseJson(JSONObject json);
	public String getModelName();
	public HashMap<String, String> getPostData();
}
