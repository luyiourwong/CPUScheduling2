package com.luyiourwong.se.hw2.schedules;

public enum ScheduleList {

	FCFS("FCFS", "First Come First Served"),   //Done
	SJF("SJF", "Shortest Job First"),          //Done
	PF("PF", "Priority First"),                //Done
	RR("RR", "Round Robin"),                   //Done
	MQ("MQ", "Multilevel Queue"),              //Framework
	MFQ("MFQ", "Multilevel Feedback Queue");   //Framework
	
	/*
	 * 
	 */
	private String nick;
	private String fullName;
	
	ScheduleList(String nick, String fullName){
		setNick(nick);
		setFullName(fullName);
	}

	public String getNick() {
		return nick;
	}

	private void setNick(String nick) {
		this.nick = nick;
	}

	public String getFullName() {
		return fullName;
	}

	private void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
