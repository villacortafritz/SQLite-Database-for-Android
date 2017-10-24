package com.android;

public class Car {
	private String manufacturer;
	private String model;
	private double price;
	private String agentID;
	
	Car(){}
	Car (String id, String m, String mo, double p) {
		agentID = id;
		manufacturer = m;
		model = mo;
		price = p;
	}
	
	public void setAgentID(String id) {agentID = id;}
	public void setManu(String m) {manufacturer = m;}
	public void setModel(String mo) {model = mo;}
	public void setPrice(double p) {price = p;}
	
	public String getAgentID() {return agentID;}
	public String getManu() {return manufacturer;}
	public String getModel() {return model;}
	public double getPrice() {return price;}
	
}
