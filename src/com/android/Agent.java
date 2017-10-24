package com.android;

public class Agent {
	private String name;
	private String city;
	private double interest;
	private String agentID;
	
	Agent(){}
	Agent (String id, String m, double p, String mo) {
		agentID = id;
		name = m;
		city = mo;
		interest = p;
	}
	
	public void setAgentID(String id) {agentID = id;}
	public void setName(String m) {name = m;}
	public void setCity(String mo) {city = mo;}
	public void setInterestRate(double p) {interest = p;}
	
	public String getAgentID() {return agentID;}
	public String getCity() {return city;}
	public String getName() {return name;}
	public double getInterestRate() {return interest;}
	
}
