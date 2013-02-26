package com.intuitive.yummy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MenuActivity extends Activity {
	private com.intuitive.yummy.Menu menu;
	private ListView listView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
        
        Intent intent = getIntent();
        menu = (com.intuitive.yummy.Menu)intent.getSerializableExtra("Menu");
        
		MenuItem[] itemsArray = new MenuItem[menu.getMenuItem().size()];
		menu.getMenuItem().toArray(itemsArray);

		System.out.println("creating adapter");
		MenuItemAdapter adapter = new MenuItemAdapter(this, R.layout.list_menuitem, itemsArray);
		listView = (ListView)findViewById(R.id.listMenuItem);
		listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search_results, menu);
        return true;
    }
}
