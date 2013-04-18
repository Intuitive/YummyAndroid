package com.intuitive.yummy.models;

import java.io.Serializable;

import org.json.JSONObject;
import android.os.Parcelable;

public interface Model extends Parcelable, Serializable, Parcelable.Creator<Model>{
 
	public void parseJson(JSONObject json);
	public String getModelName();
}
