package com.luyiourwong.se.hw2.schedules;

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
		setName(name);
		setPriority(priority);
		setBurst(burst);
		setArrival(arrival);
		setMapValue(new HashMap<String, Integer>());
	}
	
	/*
	 * 
	 */
	
	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	private void setPriority(int priority) {
		this.priority = priority;
	}

	public int getBurst() {
		return burst;
	}

	private void setBurst(int burst) {
		this.burst = burst;
	}

	public int getArrival() {
		return arrival;
	}

	private void setArrival(int arrival) {
		this.arrival = arrival;
	}
	
	private Map<String, Integer> getMapValue() {
		return mapValue;
	}

	private void setMapValue(Map<String, Integer> mapValue) {
		this.mapValue = mapValue;
	}
	
	/**
	 * Get algorithm's turnaround/waiting time key name
	 * @param type input by MainCPUScheduling.[type]
	 * @param alg input by Process.[alg]
	 * @return key String
	 */
	public String getKey(int type, String alg) {
		String key = (type == TURN)? "turn" : "wait";
		key += "_" + alg;
		return key;
	}
	
	/**
	 * Get algorithm's turnaround/waiting time value
	 * @param type input by MainCPUScheduling.[type]
	 * @param alg input by Process.[alg]
	 * @return value String
	 */
	public int getValue(int type, String alg) {
		return getMapValue().get(getKey(type, alg));
	}
	
	/**
	 * Set algorithm's turnaround/waiting time value
	 * @param type input by MainCPUScheduling.[type]
	 * @param alg input by Process.[alg]
	 * @param value time
	 */
	public void setValue(int type, String alg, int value) {
		getMapValue().put(getKey(type, alg), value);
	}
	
	/*
	 * compare by arrival time
	 */
	@Override
	public int compareTo(Process o) {
		return this.getArrival() - o.getArrival();
	}

	
}
