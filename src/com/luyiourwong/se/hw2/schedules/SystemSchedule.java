package com.luyiourwong.se.hw2.schedules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.luyiourwong.se.hw2.Logger;

public class SystemSchedule {

	private List<String> listInput;
	private List<Process> listPro;
	private List<Schedule> listSch;
	
	public SystemSchedule() {
		initSchedules();
	}
	
	private List<String> getListInput() {
		return listInput;
	}

	private void setListInput(List<String> listInput) {
		this.listInput = listInput;
	}

	public List<Process> getListPro() {
		return listPro;
	}

	private void setListPro(List<Process> listPro) {
		this.listPro = listPro;
	}
	
	public List<Schedule> getListSch() {
		return listSch;
	}

	public void setListSch(List<Schedule> listSch) {
		this.listSch = listSch;
	}

	public void initSchedules() {
		setListSch(new ArrayList<Schedule>());
		
		getListSch().add(new ScheduleFCFS());
		getListSch().add(new ScheduleSJF());
		getListSch().add(new SchedulePF());
		getListSch().add(new ScheduleRR());
		
		for(Schedule sch : getListSch()) {
			Logger.log("[initSchedules] load Schedule: " + sch.getAlg().getFullName());
		}
	}
	
	public List<Schedule> scheduling(File file) {
		//scheduling setup
		List<String> listInput = this.readFileFromFile(file);
		schedulingSetup(listInput);
		
		//print
		for(Process p : getListPro()) {
			Logger.log("[after sort] process " + p.getName() + " : " + p.getPriority() + ", " + p.getBurst() + ", " + p.getArrival());
		}
		
		//run
		for(Schedule sch : getListSch()) {
			//special setup
			if(sch instanceof ScheduleRR) {
				((ScheduleRR)sch).setChangeTime(getChangeTime());
			}
			
			//normal
			sch.runSchedule(getListPro());
		}
		return getListSch();
	}
	
	/**
	 * get input from file, create list process, sort by arrival
	 * @param listInput
	 */
	private void schedulingSetup(List<String> listInput) {
		setListInput(listInput);
		setListPro(this.createListPro(getListInput()));
		Collections.sort(getListPro());
	}
	
	/**
	 * read file from string location to list string
	 * @param file
	 * @return
	 */
	private List<String> readFileFromFile(File file) {
		Logger.logDEBUG("[read file] ========================");
		
		/*
		 * load file
		 */
		
		List<String> listInputs = new ArrayList<String>();
		FileReader reader = null;
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader buffer = new BufferedReader(reader);
		Scanner scan = new Scanner(buffer);
		
		/*
		 * read string
		 */
		while(scan.hasNext()){
			String next = scan.next();
			listInputs.add(next);
			Logger.logDEBUG("[read file] read: " + next);
		}
		
		/*
		 * end
		 */
		scan.close();
		try {
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Logger.logDEBUG("[read file] ========================");
		
		return listInputs;
	}
	
	/*
	 * createListPro
	 */
	
	private int changeTime = -1;
	
	private int getChangeTime() {
		return changeTime;
	}

	private void setChangeTime(int changeTime) {
		this.changeTime = changeTime;
	}

	private List<Process> createListPro(List<String> listInput){
		Logger.logDEBUG("[create list pro] ========================");
		
		List<Process> list = new ArrayList<Process>();
		
		//get change time
		int count = 0;
		while(this.getChangeTime() == -1) {
			String input = listInput.get(count);
			int in = 0;
			try {
				in = Integer.parseInt(input);
			} catch(NumberFormatException e) {
				count++;
				continue;
			}
			this.setChangeTime(in);
			Logger.log("[create list pro] set change time: " + this.getChangeTime());
		}
		
		//remove change time from list
		listInput.remove(count);
		
		//format process
		String name = "";
		int priority = -1;
		int burst = -1;
		int arrival = -1;
		for(String s : listInput) {
			if(name.isEmpty() || name.isBlank()) {
				name = s;
				
				continue;
			} else if(priority == -1) {
				int in = 0;
				try {
					in = Integer.parseInt(s);
				} catch(NumberFormatException e) {
					continue;
				}
				priority = in;
				continue;
			} else if(burst == -1) {
				int in = 0;
				try {
					in = Integer.parseInt(s);
				} catch(NumberFormatException e) {
					continue;
				}
				burst = in;
				continue;
			} else if(arrival == -1) {
				int in = 0;
				try {
					in = Integer.parseInt(s);
				} catch(NumberFormatException e) {
					continue;
				}
				arrival = in;
				
				//create new process
				list.add(new Process(name, priority, burst, arrival));
				Logger.logDEBUG("[create list pro] new process " + name + " : " + priority + ", " + burst + ", " + arrival);
				
				//init
				name = "";
				priority = -1;
				burst = -1;
				arrival = -1;
				
				continue;
			}
		}
		
		Logger.logDEBUG("[create list pro] ========================");
		
		return list;
	}
	
	/*
	 * scheduling template Process
	 */
	protected static Process pIdle = new Process("/", 0, 0, 0);
	protected static Process pEND = new Process("END", 0, 0, 0);
	
	public Process getpIdle() {
		return pIdle;
	}

	public Process getpEND() {
		return pEND;
	}
}
