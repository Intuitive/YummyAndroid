package com.intuitive.yummy.sqlitedb;

import java.util.ArrayList;
import java.util.List;

import com.intuitive.yummy.models.MenuItem;
import com.intuitive.yummy.models.OrderItem;
import com.intuitive.yummy.models.OrderItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {

	private static final int dbVersion = 1;
	private static final String dbName = "YummyDatabase";
	private static final String shoppingCartTableName = "shoppingCart";

	// OrderItems Table Columns names
	private static final String shoppingCart_Id= "order_item_id";
	private static final String shoppingCart_MenuItemId = "menu_item_id";
	private static final String shoppingCart_Name= "name";
	private static final String shoppingCart_Quantity = "quantity";
	private static final String shoppingCart_SpecialInstructions = "special_instructions";
	private static final String shoppingCart_Price = "price";
	
	public SQLiteDB(Context context) {
		super(context, dbName, null, dbVersion);
	}
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = 
				"CREATE TABLE " + shoppingCartTableName 
				+ "("
						+ shoppingCart_Id + " INTEGER PRIMARY KEY,"
						+ shoppingCart_MenuItemId + "INTEGER,"
						+ shoppingCart_Name + " TEXT,"
						+ shoppingCart_Quantity + " INTEGER,"
						+ shoppingCart_SpecialInstructions + " TEXT,"
						+ shoppingCart_Price + " INTEGER"
						
				+ ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + shoppingCartTableName);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new orderItem
	public void addOrderItem(MenuItem menuItem, int quantity, String specialInstructions) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(shoppingCart_Name, menuItem.getName());
		values.put(shoppingCart_MenuItemId, menuItem.getId());
		values.put(shoppingCart_Price, menuItem.getPrice() * 100.0);
		values.put(shoppingCart_Quantity, quantity);
		values.put(shoppingCart_SpecialInstructions, specialInstructions);

		db.insert(shoppingCartTableName, null, values);
		db.close();
	}

	// Getting single orderItem
	public OrderItem getOrderItem(int orderItemId) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(
				shoppingCartTableName, 
				new String[] { 	shoppingCart_Id,
								shoppingCart_MenuItemId,
								shoppingCart_Name,
								shoppingCart_Quantity,
								shoppingCart_Price,
								
				}, shoppingCart_Id + "=?",
				new String[] { String.valueOf(orderItemId) }, 
				null, null, null, null
		);
		
		if (cursor != null)
			cursor.moveToFirst();

		
		OrderItem orderItem = new OrderItem();
		orderItem.setId(cursor.isNull(0) ? null : cursor.getInt(0));
		orderItem.setMenuItemId(cursor.isNull(1) ? null : cursor.getInt(1));
		orderItem.setName(cursor.isNull(2) ? null : cursor.getString(2));
		orderItem.setQuantity(cursor.isNull(3) ? null : cursor.getInt(3));
		orderItem.setPrice(cursor.isNull(0) ? null : cursor.getInt(4)/100.0);
				
		return orderItem;
	}

	// Getting All OrderItems
	public List<OrderItem> getAllOrderItems() {
		
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		String selectQuery = "SELECT  * FROM " + shoppingCartTableName;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// loop through results and add to list
		if (cursor.moveToFirst()) {
			do {
				OrderItem orderItem = new OrderItem();
				orderItem.setId(cursor.isNull(0) ? null : cursor.getInt(0));
				orderItem.setMenuItemId(cursor.isNull(1) ? null : cursor.getInt(1));
				orderItem.setName(cursor.isNull(2) ? null : cursor.getString(2));
				orderItem.setQuantity(cursor.isNull(3) ? null : cursor.getInt(3));
				orderItem.setPrice(cursor.isNull(0) ? null : cursor.getInt(4)/100.0);
				
				orderItemList.add(orderItem);
			} while (cursor.moveToNext());
		}

		// return orderItem list
		return orderItemList;
	}

	// Updating single orderItem
	public int updateOrderItem(OrderItem orderItem) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(shoppingCart_MenuItemId, orderItem.getMenuItemId());
		values.put(shoppingCart_Name, orderItem.getName());
		values.put(shoppingCart_Quantity, orderItem.getQuantity());
		values.put(shoppingCart_Price, ((Double) (orderItem.getPrice()*100.0)).intValue() );

		// updating row
		return db.update(shoppingCartTableName, values, 
				shoppingCart_Id + " = ?",
				new String[] { String.valueOf(orderItem.getId()) });
	}

	public void deleteOrderItem(OrderItem orderItem) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(shoppingCartTableName, shoppingCart_Id + " = ?",
				new String[] { String.valueOf(orderItem.getId()) });
		db.close();
	}


	public int getOrderItemsCount() {
		String countQuery = "SELECT  * FROM " + shoppingCartTableName;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
