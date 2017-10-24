package com.android;

public class City {
	private double salesTax;
	private String city;
	
	City(){}
	City (double p, String s) {
		salesTax = p;
		city = s;
	}
	
	public void setCity(String mo) {city = mo;}
	public void setSalesTax(double p) {salesTax = p;}
	
	public String getCity() {return city;}
	public double getSalesTax() {return salesTax;}
	
}