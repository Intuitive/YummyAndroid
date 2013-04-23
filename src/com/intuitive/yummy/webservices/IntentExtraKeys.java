package com.intuitive.yummy.webservices;

/**
 * Used for keys when attaching extras to any intent passed to the RestService
 * @author Edgar Paz
 *
 */
public class IntentExtraKeys {
	public static final String RECEIVER = "RECEIVER";
	public static final String MODEL_CLASS = "MODEL_CLASS";
	public static final String ACTION = "ACTION";
	public static final String POST_DATA= "POST_DATA";
	public static final String ERROR = "ERROR";
	public static final String MODEL_ID = "MODEL_ID";
	public static final String SUCCESS = "SUCCESS";
	public static final String MODEL = "MODEL";
	
	public static final String PARAMETER(int i){
		// TODO test this
		return "PARAMETER".concat(String.valueOf(i));
	}
}

