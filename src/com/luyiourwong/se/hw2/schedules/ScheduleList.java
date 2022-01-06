package com.luyiourwong.se.hw2.schedules;

public enum ScheduleList {

	FCFS("FCFS", "First Come First Served"),
	SJF("SJF", "Shortest Job First"),
	PF("PF", "Priority First"),
	RR("RR", "Round Robin"),
	;
	
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
