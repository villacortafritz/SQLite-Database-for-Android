package com.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHandler extends SQLiteOpenHelper{
	public static  final String DATABASE_NAME="User.db";
	public static final String CITY_TABLE = "city_table";
	public static final String AGENT_TABLE = "agent_table";
	public static final String CAR_TABLE = "car_table";

	public static final String cityTable_SALESTAX = "SALESTAX";
	public static final String cityTable_CITY = "CITY";
	
	public static final String agentTable_AGENTID = "AGENTID";
	public static final String agentTable_INTEREST = "INTEREST";
	public static final String agentTable_NAME = "NAME";
	public static final String agentTable_CITY = "CITY";
	
	public static final String carTable_MANUFACTURER = "MANUFACTURER";
	public static final String carTable_MODEL = "MODEL";
	public static final String carTable_PRICE = "PRICE";
	public static final String carTable_NAME = "NAME";

	

	
	
	private static int version =1;
	public DBHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}	
	public DBHandler(Context context){
		super(context,DATABASE_NAME,null,version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql=" CREATE TABLE " +CITY_TABLE + " (CITY TEXT, SALESTAX FLOAT, PRIMARY KEY(CITY))";
		db.execSQL(sql);
		sql=" CREATE TABLE " +AGENT_TABLE+ " (AGENTID TEXT, NAME TEXT, INTEREST FLOAT, CITY TEXT, PRIMARY KEY(AGENTID), CONSTRAINT FK_CITY FOREIGN KEY(CITY) REFERENCES CITY_TABLE(CITY) )";
		db.execSQL(sql);
		sql=" CREATE TABLE " +CAR_TABLE + " (MANUFACTURER TEXT, MODEL TEXT, PRICE FLOAT, NAME TEXT, PRIMARY KEY(MODEL), CONSTRAINT FK_NAME FOREIGN KEY(NAME) REFERENCES AGENT_TABLE(NAME) )";
		
		
		
		db.execSQL(sql);
		
	}
	public boolean insertCity(String cityName, float salesTax){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(cityTable_CITY, cityName);
		contentValues.put(cityTable_SALESTAX, salesTax);
		long result = db.insert(CITY_TABLE, null, contentValues);
		db.close();
		if(result ==-1 ){
			return false;
		}else{
			return true;
		}
		
	}
	
	public boolean insertCar(String manufacturer, String model, float price, String agentName){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(carTable_MANUFACTURER, manufacturer);
		contentValues.put(carTable_MODEL, model);
		contentValues.put(carTable_PRICE, price);
		contentValues.put(carTable_NAME, agentName);
		long result = db.insert(CAR_TABLE, null, contentValues);
		db.close();
		if(result ==-1 ){
			return false;
		}else{
			return true;
		}
		
	}
	
	public boolean insertAgent(String agentID, String agentName, float interest, String cityName){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(agentTable_AGENTID, agentID);
		contentValues.put(agentTable_NAME, agentName);
		contentValues.put(agentTable_INTEREST, interest);
		contentValues.put(agentTable_CITY, cityName);
		long result = db.insert(AGENT_TABLE, null, contentValues);
		db.close();
		if(result ==-1 ){
			return false;
		}else{
			return true;
		}
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
			db.execSQL("drop table if exists CITY_TABLE");
			db.execSQL("drop table if exists CAR_TABLE");
			db.execSQL("drop table if exists AGENT_TABLE");
			
			this.onCreate(db);
	}
	
	public Cursor getCity(){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery("SELECT * FROM "+CITY_TABLE,null);
		return res;
	}
	
	public Cursor getAgent(String cityName){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery("SELECT * FROM "+AGENT_TABLE+" WHERE CITY="+"'"+cityName+"'",null);
		return res;
	}
	
	public Cursor getAgentInterest(String agentName){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery("SELECT INTEREST FROM "+AGENT_TABLE+" WHERE NAME="+"'"+agentName+"'",null);
		return res;
	}
	
	public Cursor getCitySalesTax(String cityName){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery("SELECT SALESTAX FROM "+CITY_TABLE+" WHERE CITY="+"'"+cityName+"'",null);
		return res;
	}
	
	public Cursor getCar(String agentName){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery("SELECT * FROM "+CAR_TABLE+" WHERE NAME="+"'"+agentName+"'",null);
		return res;
	}
	
	
}
