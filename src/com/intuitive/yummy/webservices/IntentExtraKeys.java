package com.intuitive.yummy.webservices;

/**
 * Used for keys when attaching extras to any intent passed to the RestService
 * @author Edgar Paz
 *
 */
public class IntentExtraKeys {
	public static final String RECEIVER = "RECEIVER";
	public static final String MODEL_TYPE = "MODEL_TYPE";
	public static final String ACTION = "ACTION";
	
	public static final String PARAMETER(int i){
		// TODO test this
		return "PARAMETER".concat(String.valueOf(i));
	}
}

