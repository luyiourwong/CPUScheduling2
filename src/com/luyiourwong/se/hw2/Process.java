package com.luyiourwong.se.hw2;

import java.util.HashMap;
import java.util.Map;

public class Process implements Comparable<Process>{
	
	public static final int TURN = 1;
	public static final int WAIT = 2;

	private String name;
	private int priority;
	private int burst;
	private int arrival;
	
	private Map<String, Integer> mapValue;
	
	public Process(String name, int priority, int burst, int arrival) {
		super();
		this.name = name;
		this.priority = priority;
		this.burst = burst;
		this.arrival = arrival;
		this.mapValue = new HashMap<String, Integer>();
	}
	
	/*
	 * 
	 */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getBurst() {
		return burst;
	}

	public void setBurst(int burst) {
		this.burst = burst;
	}

	public int getArrival() {
		return arrival;
	}

	public void setArrival(int arrival) {
		this.arrival = arrival;
	}
	
	/*
	 * 
	 */

	public Map<String, Integer> getMapValue() {
		return mapValue;
	}

	public void setMapValue(Map<String, Integer> mapValue) {
		this.mapValue = mapValue;
	}
	
	public String getKey(int type, String alg) {
		String key = (type == TURN)? "turn" : "wait";
		key += "_" + alg;
		return key;
	}
	
	public int getValue(int type, String alg) {
		return getMapValue().get(getKey(type, alg));
	}
	
	public void setValue(int type, String alg, int arg) {
		getMapValue().put(getKey(type, alg), arg);
	}

	@Override
	public int compareTo(Process o) {
		return this.getArrival() - o.getArrival();
	}

	
}
