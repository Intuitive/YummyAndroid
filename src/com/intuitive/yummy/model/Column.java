package com.intuitive.yummy.model;

public @interface Column {

	String columnName() default "";

	boolean exclude() default false; 

}
