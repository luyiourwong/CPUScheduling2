package com.luyiourwong.se.hw2;

import com.luyiourwong.se.hw2.schedules.ScheduleList;

public class Logger {

	public static void log(String msg) {
		System.out.println(msg);
	}
	
	public static void logDEBUG(String msg) {
		if(MainCPUScheduling.DEBUG)System.out.println(msg);
	}
	
	public static void logAlg(ScheduleList alg, String msg) {
		System.out.println("[sch " + alg.getNick() + "] " + msg);
	}
	
	public static void logAlg(ScheduleList alg, int count, String msg) {
		System.out.println("[sch " + alg.getNick() + "] [" + count + "] " + msg);
	}
}
