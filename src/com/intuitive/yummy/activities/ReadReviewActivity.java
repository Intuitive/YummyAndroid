package com.intuitive.yummy.activities;

import java.util.ArrayList;

import com.intuitive.yummy.R;
import com.intuitive.yummy.models.ReviewAdapter;
import com.intuitive.yummy.models.VendorReview;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ReadReviewActivity extends Activity {
	private VendorReview r5 = new VendorReview("Great Place", "This place is great.  I love the food!", 5);
	private VendorReview r4 = new VendorReview("Good Value", "Price is good for this place.", 4);
	private VendorReview r3 = new VendorReview("SoSo", "Just an everyday food truck.", 3);
	private VendorReview r2 = new VendorReview("Not So Good", "Need more selections.", 2);
	private VendorReview r1 = new VendorReview("Bad", "Food make me sick!!!", 1);
	private VendorReview r5l = new VendorReview("Best Food in Town", "This truck has the best food in town.  I been to a lot of trucks and this one taste the best!  Love it!", 5);
	private VendorReview r4l = new VendorReview("Worth the Money", "If you don't like your previous food truck, try this one.  You might love it.  I did.", 4);
	private VendorReview r3l = new VendorReview("Same As All Others", "There is nothing special about this food truck.  Its same as others around.", 3);
	private VendorReview r2l = new VendorReview("Need More Selections", "I like the food, but the option is very limited.  I wish they have more.", 2);
	private VendorReview r1l = new VendorReview("I Will Never Come Again", "The food make me almost go to the hospital.  I will never go to this truck again", 1);
	
	private ArrayList<VendorReview> reviews = new ArrayList<VendorReview> ();
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_review);
        Intent intent = getIntent();
        TextView vendorName = (TextView)findViewById(R.id.read_review_vendor_name);
        vendorName.setText("Reviews for " + (String)intent.getSerializableExtra("VendorName"));
        
		reviews.add(r1);
		reviews.add(r5);
		reviews.add(r2);
		reviews.add(r4);
		reviews.add(r3);
		reviews.add(r1l);
		reviews.add(r5l);
		reviews.add(r2l);
		reviews.add(r4l);
		reviews.add(r3l);
		
        ImageView stars = (ImageView)findViewById(R.id.read_review_vendor_rating);
		int averageRating = 0;
		for (int i = 0; i < reviews.size(); i++)
			averageRating += reviews.get(i).getStar();
		averageRating = averageRating/reviews.size();
		stars.setImageDrawable(getResources().getDrawable(getStarPic(averageRating)));
		
		VendorReview[] reviewsArray = new VendorReview[reviews.size()];
		reviews.toArray(reviewsArray);
		
		ReviewAdapter adapter = new ReviewAdapter(this, R.layout.list_review, reviewsArray);
		listView = (ListView)findViewById(R.id.listReview);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_read_review, menu);
		return true;
	}
	
	private int getStarPic(int rating) {
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
