package com.luyiourwong.se.hw2;

public enum ScheduleList {

	FCFS("FCFS", "First Come First Served"),
	SJF("SJF", "Shortest Job First"),
	HRR("HRR", "Highest Response Ratio"),
	PF("PF", "Priority First"),
	RR("RR", "Round Robin"),
	MQ("MQ", "Multilevel Queue"),
	MFQ("MFQ", "Multilevel Feedback Queue");
	
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

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
