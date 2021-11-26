package com.luyiourwong.se.hw2;

public class Logger {

	public static void log(String msg) {
		System.out.println(msg);
	}
	
	public static void logDEBUG(String msg) {
		if(MainCPUScheduling.DEBUG)System.out.println(msg);
	}
}
