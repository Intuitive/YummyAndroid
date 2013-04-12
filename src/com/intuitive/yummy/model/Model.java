package com.intuitive.yummy.model;

import org.json.JSONObject;
import android.os.Parcelable;

public interface Model extends Parcelable, Parcelable.Creator<Model>{
 
	public void parseJson(JSONObject json);
	public String getModelName();
}
