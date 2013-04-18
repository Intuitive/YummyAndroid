package com.intuitive.yummy.models;

public @interface Column {

	String columnName() default "";

	boolean exclude() default false; 

}
