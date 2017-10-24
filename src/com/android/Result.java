package com.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends Activity {
    /** Called when the activity is first created. */
	
	TextView tvAgent,tvCar,tvPrice,tvDown, tvYear, tvSalesTax, tvInterest, tvYearAmortization;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        
        tvAgent = (TextView) findViewById(R.id.tvAgent);
    	tvCar = (TextView) findViewById(R.id.tvCar);
    	tvDown = (TextView) findViewById(R.id.tvDown);
    	tvYear = (TextView) findViewById(R.id.tvYear);
    	tvSalesTax = (TextView) findViewById(R.id.tvSalesTax);
    	tvInterest = (TextView) findViewById(R.id.tvInterest);
    	tvYearAmortization = (TextView) findViewById(R.id.tvYearAmortization);
    	tvPrice = (TextView) findViewById(R.id.tvPrice);
    	
    	Intent i = getIntent();
    	tvAgent.setText(i.getStringExtra("Agent"));
    	tvCar.setText(i.getStringExtra("Car"));
    	tvDown.setText(i.getStringExtra("Down"));
    	tvYear.setText(i.getStringExtra("Year"));
    	tvSalesTax.setText(i.getStringExtra("SalesTax"));
    	tvInterest.setText(i.getStringExtra("Interest"));
    	tvYearAmortization.setText(i.getStringExtra("YearAmortization"));
    	tvPrice.setText(i.getStringExtra("Price"));
    }


}