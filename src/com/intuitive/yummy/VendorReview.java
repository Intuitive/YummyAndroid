package com.intuitive.yummy;

//contain comment and star rating for each review
public class VendorReview {
	private String comment;
	private int star;
	
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	public String getComment()
	{
		return comment;
	}
	public void setStar(int star)
	{
		this.star = star;
	}
	public int getStar()
	{
		return star;
	}
	
	VendorReview(String comment, int star)
	{
		this.comment = comment;
		this.star = star;
	}
}
