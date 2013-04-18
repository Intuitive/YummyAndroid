package com.intuitive.yummy.activities;

import com.intuitive.yummy.R;
import com.intuitive.yummy.R.layout;
import com.intuitive.yummy.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void login(View v){
    	Intent intent = new Intent(this, LoginActivity.class);
    	startActivity(intent);
    }
    
    public void register(View v){
    	Intent intent = new Intent(this, RegisterActivity.class);
    	startActivity(intent);
    }
    
    public void searchNearby(View v){
    	Intent intent = new Intent(this, SearchResultsActivity.class);
    	intent.putExtra("criteria", "nearby");
    	startActivity(intent);
    }
    
    public void search(View v){
    	Intent intent = new Intent(this, SearchActivity.class);
    	startActivity(intent);
    }
    
}
