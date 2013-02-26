package com.intuitive.yummy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ReviewAdapter extends ArrayAdapter<VendorReview> {
	Context context;
	int layoutResourceId;
	VendorReview data[] = null;
	
	public ReviewAdapter (Context context, int layoutResourceId, VendorReview[] data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ReviewHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ReviewHolder();
			holder.rating = (ImageView)row.findViewById(R.id.rating);
			holder.comment = (TextView)row.findViewById(R.id.comment);
			row.setTag(holder);
		} else {
			holder = (ReviewHolder)row.getTag();
		}
		VendorReview review = data[position];
		holder.comment.setText(review.getComment());
		holder.rating.setImageDrawable(context.getResources().getDrawable(getStarPic(review.getStar())));
		
		return row;
	}
	
	static class ReviewHolder
	{
		ImageView rating;
		TextView comment;
	}
	
	private int getStarPic(int rating)
	{
		if (rating == 5)
			return R.drawable.fivestar;
		else if (rating == 4)
			return R.drawable.fourstar;
		else if (rating == 3)
			return R.drawable.threestar;
		else if (rating == 2)
			return R.drawable.twostar;
		else if (rating == 1)
			return R.drawable.onestar;
		else
			return R.drawable.zerostar;	
	}

}
