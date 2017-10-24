package com.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SQLite extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	private Intent i;
	private Spinner spCity, spAgent, spCar, spYears;
	private EditText txtDown;
	private TextView tvPrice;
	private Button btnCalc;
	private DBHandler db;
	private String[] cities;
	private String[] agents;
	private String[] cars;
	private String[] years = {
		"2", "3", "4"	
	};
	String price;
	private ArrayList<String> alCities;
	private ArrayList<String> alAgents;
	private ArrayList<String> alCars;
	private String citySelected = "Cebu";
	private String agentSelected = "Fritz";
	private String carSelected = "Honda";
	private String yearSelected = "2";
	private Cursor res;
	private ArrayAdapter<String> cityAdapter;
	private ArrayAdapter<String> agentAdapter;
	private ArrayAdapter<String> carAdapter;
	private ArrayAdapter<String> yearAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        alCities = new ArrayList<String>();
        alAgents = new ArrayList<String>();
        alCars = new ArrayList<String>();
        cities = new String[3];
        try{
	    	db = new DBHandler(this);
	    	db.insertCity("Cebu", 0.09f);
	    	db.insertCity("Davao", 0.09f);
	    	db.insertCity("Manila", 0.07f);
	    	db.insertAgent("0001", "Fritz", 0.2f, "Cebu");
	    	db.insertAgent("0003", "Oplas", 0.2f, "Cebu");
	    	db.insertAgent("0005", "Jee", 0.1f, "Manila");
	    	db.insertAgent("0005", "Jess", 0.1f, "Manila");
	    	db.insertAgent("0006", "James", 0.3f, "Davao");
	    	db.insertAgent("0006", "Patrick", 0.3f, "Davao");
	    	db.insertCar("Toyota", "Meoooooww", 699.99f, "Jee");
	    	db.insertCar("Toyota", "Ruuuuuuuf", 699.99f, "Jess");
	    	db.insertCar("Toyota", "Twweeeet", 699.99f, "Oplas");
	    	db.insertCar("Honda", "Awwwwww", 899.99f, "Fritz");
	    	db.insertCar("Toyota", "Neeeeigh", 699.99f, "James");
	    	db.insertCar("Suzuki", "Oiiinkk", 799.99f, "Patrick");

	        spAgent = (Spinner)findViewById(R.id.spAgent);
	        spCar = (Spinner)findViewById(R.id.spModel);
	        spYears = (Spinner)findViewById(R.id.spYears);
	        tvPrice = (TextView)findViewById(R.id.tvPrice);
	        txtDown = (EditText)findViewById(R.id.txtDown);
	        btnCalc = (Button)findViewById(R.id.btnCalc);
	        spCity = (Spinner)findViewById(R.id.spCity);
	        spCity.setOnItemSelectedListener(new OnItemSelectedListener(){
	        	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
	        		citySelected = cities[position];
	        		loadAgents();
	        	}

	        	public void onNothingSelected(AdapterView<?> parent) {
	        		citySelected = "";
	        	}
	        });
	        spAgent.setOnItemSelectedListener(new OnItemSelectedListener(){

	        	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
	        		agentSelected = agents[position];
	        		loadCars();
	        	}

	        	public void onNothingSelected(AdapterView<?> parent) {
	        		agentSelected = "";
	        	}
	        	
	        });
	        spCar.setOnItemSelectedListener(new OnItemSelectedListener(){

	        	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
	        		carSelected = cars[position];
	        		loadPrice();
	        	}

	        	public void onNothingSelected(AdapterView<?> parent) {
	        		carSelected = "";
	        	}
	        	
	        });
	        spYears.setOnItemSelectedListener(new OnItemSelectedListener(){

	        	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
	        		yearSelected = years[position];
	        	}

	        	public void onNothingSelected(AdapterView<?> parent) {
	        		yearSelected = "";
	        	}
	        	
	        });
	        
	        
		    loadCity();
		    loadYears();
	        
		    btnCalc.setOnClickListener(this);
	        
        }catch(Exception e){
        	Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
        }
      
       
    }

	public void onClick(View view) {
		// TODO Auto-generated method stub
		try{
			res = db.getAgentInterest(agentSelected);
			float interestRate = 0.0f;
	        if(res!=null && res.getCount()>0){
	        	while(res.moveToNext()){
	        		interestRate = Float.parseFloat(res.getString(0));
	        	}
	        }
	        
	        res = db.getCitySalesTax(citySelected);
			float salesTaxRate = 0.0f;
	        if(res!=null && res.getCount()>0){
	        	while(res.moveToNext()){
	        		salesTaxRate = Float.parseFloat(res.getString(0));
	        	}
	        }
	        
	        float price =  Float.parseFloat(tvPrice.getText().toString());
	        float downpayment = Float.parseFloat(txtDown.getText().toString());
	        float year = Float.parseFloat(yearSelected);
	        float taxablePrice;
	        float sales;
	        float interest;
	        float total;
	        float yearAmortization;
	        
	        
	        taxablePrice = price - downpayment;
	        sales = taxablePrice * salesTaxRate;
	        interest = taxablePrice * interestRate * year;
	        total = taxablePrice + sales + interest;
	        yearAmortization = total / year;
	        	       
	        Intent i = new Intent(SQLite.this, Result.class);
	        i.putExtra("Agent", agentSelected);
	        i.putExtra("Car", carSelected);
	        i.putExtra("Down", Float.toString(downpayment));
	        i.putExtra("Year", Float.toString(year));
	        i.putExtra("SalesTax", Float.toString(sales));
	        i.putExtra("Interest", Float.toString(interest));
	        i.putExtra("YearAmortization", Float.toString(yearAmortization));
	        i.putExtra("Price", Float.toString(price));
	        startActivity(i);
	   
	        
		}catch(Exception e){
        	Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
		}
	}

	
	public void loadCity(){
		try{
			res = db.getCity();
	        if(res!=null && res.getCount()>0){
	        	while(res.moveToNext()){
	        		alCities.add(res.getString(0));
	        	}
	        }
	        
		    for(int i=0;i<alCities.size();i++){
		    	cities[i] = alCities.get(i);
		    }
		    		    
		    cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities); 
	        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spCity.setAdapter(cityAdapter);
		}catch(Exception e){
	    	Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
		}
        
	}
	
	public void loadAgents(){
		try{
			res = db.getAgent(citySelected);
			
			if(!alAgents.isEmpty())
				alAgents.clear();
			
	        if(res!=null && res.getCount()>0){
	        	while(res.moveToNext()){
	        		alAgents.add(res.getString(1));
	        	}
	        }
	       
	        agents = new String[alAgents.size()];
	        
	        for(int i=0;i<alAgents.size();i++){
	        	agents[i] = alAgents.get(i);
		    }
	        
	        agentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, agents); 
	        agentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spAgent.setAdapter(agentAdapter);
		}catch(Exception e){
        	Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
		}
	}
	
	public void loadPrice(){
		tvPrice.setText(price);
	}
	public void loadCars(){
		try{
			res = db.getCar(agentSelected);
			price = "0.00";
			if(!alCars.isEmpty())
				alCars.clear();
				        if(res!=null && res.getCount()>0){
	        	while(res.moveToNext()){
	        		alCars.add(res.getString(1));
	        		price = res.getString(2);
	        	}
	        }
	       
	        cars = new String[alCars.size()];
	        
	        for(int i=0;i<alCars.size();i++){
	        	cars[i] = alCars.get(i);
		    }
	        
	        carAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cars); 
	        carAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spCar.setAdapter(carAdapter);
		}catch(Exception e){
        	Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
		}
	}
	
	public void loadYears(){
		try{    
		    yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years); 
		    yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spYears.setAdapter(yearAdapter);
		}catch(Exception e){
	    	Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
		}
        
	}

}