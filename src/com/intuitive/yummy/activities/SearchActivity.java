package com.intuitive.yummy.activities;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.layout;
import com.intuitive.yummy.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SearchActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
        /** TODO fill layout */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search, menu);
        return true;
    }
}
