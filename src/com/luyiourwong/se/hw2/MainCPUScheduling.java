package com.luyiourwong.se.hw2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MainCPUScheduling{

	private static MainCPUScheduling instance;
	
	private static boolean DEBUG = true;
	
	/*
	 * create instance
	 */

	public static void main(String[] args) {
		instance = new MainCPUScheduling();
        instance.init();
	}
	
	public MainCPUScheduling() {
		
	}
	
	public static MainCPUScheduling getInstance() {
		return instance;
	}
	
	/*
	 * logDEBUG
	 */
	
	public void log(String msg) {
    	System.out.println(msg);
    	//pushlogDEBUG(msg);if(DEBUG)
	}
	
	public void logDEBUG(String msg) {
		if(DEBUG)System.out.println(msg);
    	//pushlogDEBUG(msg);
	}
	
	/*
	 * redirect
	 */
	
	private GuiMain guiMain;
	
	public GuiMain getGuiMain() {
		return guiMain;
	}

	public void setGuiMain(GuiMain guiMain) {
		this.guiMain = guiMain;
	}
	
	/*
	 * init
	 */
	
	private List<String> listInput;
	private List<Process> listPro;
	
	private void init() {
		setGuiMain(new GuiMain());
		getGuiMain().initGui();
	}
	
	public List<String> getListInput() {
		return listInput;
	}

	public void setListInput(List<String> listInput) {
		this.listInput = listInput;
	}

	public List<Process> getListPro() {
		return listPro;
	}

	public void setListPro(List<Process> listPro) {
		this.listPro = listPro;
	}

	public void scheduling(File file) {
		//排程
		listInput = this.readFileFromFile(file);
		listPro = this.createListPro(listInput);
		
		//sort by arrival
		Collections.sort(listPro);
		for(Process p : listPro) {
			log("[after sort] process " + p.getName() + " : " + p.getPriority() + ", " + p.getBurst() + ", " + p.getArrival());
		}
		
		//FCFS
		
		log("[FCFS] ========================");
		Schedule fcfs = new ScheduleFCFS(listPro);
		fcfs.createScheduling();
		fcfs.createTimes();
		
		for(Integer i : fcfs.getMapSch().keySet()) {
			Process p = fcfs.getMapSch().get(i);
			log("[FCFS] at " + i + " : " + p.getName());
		}
		
		log("[FCFS] ========================");
		
		for(Process p : fcfs.getMapTimeRun().keySet()) {
			Integer i = fcfs.getMapTimeRun().get(p);
			log("[FCFS] " + p.getName() + " Turnaround Time: " + i);
			p.setFCFS_turn(i);
		}
		
		log("[FCFS] ========================");
		
		for(Process p : fcfs.getMapTimeWait().keySet()) {
			Integer i = fcfs.getMapTimeWait().get(p);
			log("[FCFS] " + p.getName() + " Waiting Time: " + i);
			p.setFCFS_wait(i);
		}
		
		log("[FCFS] ========================");
		
		//SJF
		
		log("[SJF] ========================");
		Schedule sjf = new ScheduleSJF(listPro);
		sjf.createScheduling();
		sjf.createTimes();
		
		for(Integer i : sjf.getMapSch().keySet()) {
			Process p = sjf.getMapSch().get(i);
			log("[SJF] at " + i + " : " + p.getName());
		}
		
		log("[SJF] ========================");
		
		for(Process p : sjf.getMapTimeRun().keySet()) {
			Integer i = sjf.getMapTimeRun().get(p);
			log("[SJF] " + p.getName() + " Turnaround Time: " + i);
			p.setSJF_turn(i);
		}
		
		log("[SJF] ========================");
		
		for(Process p : sjf.getMapTimeWait().keySet()) {
			Integer i = sjf.getMapTimeWait().get(p);
			log("[SJF] " + p.getName() + " Waiting Time: " + i);
			p.setSJF_wait(i);
		}
		
		log("[SJF] ========================");
		
		//gui
		
		getGuiMain().clearGui();
		int locY = 50;
		getGuiMain().createGuiPic(locY, "FCFS", fcfs.getMapSch());
		locY += 100;
		getGuiMain().createGuiPic(locY, "SJF", sjf.getMapSch());
		locY += 100;
		getGuiMain().createGuiTable(locY);
	}
	
	/*
	 * read file from string location to list string
	 */
	
	private List<String> readFileFromFile(File file) {
		logDEBUG("[read file] ========================");
		
		List<String> listInputs = new ArrayList<String>();
		/*
		 * 讀取檔案
		 */
		FileReader reader = null;
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader buffer = new BufferedReader(reader);
		Scanner scan = new Scanner(buffer);
		
		/*
		 * 讀取字元
		 */
		while(scan.hasNext()){
			String next = scan.next();
			listInputs.add(next);
			logDEBUG("[read file] read: " + next);
		}
		
		/*
		 * 結束讀取
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
		
		logDEBUG("[read file] ========================");
		
		return listInputs;
	}
	
	private int changeTime = -1;
	
	public int getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(int changeTime) {
		this.changeTime = changeTime;
	}

	private List<Process> createListPro(List<String> listInput){
		logDEBUG("[create list pro] ========================");
		
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
			log("[create list pro] set change time: " + this.getChangeTime());
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
				logDEBUG("[create list pro] new process " + name + " : " + priority + ", " + burst + ", " + arrival);
				
				//init
				name = "";
				priority = -1;
				burst = -1;
				arrival = -1;
				
				continue;
			}
		}
		
		logDEBUG("[create list pro] ========================");
		
		return list;
	}
	
	/*
	 * scheduling
	 */
	
	private Process pIdle = new Process("/", 0, 0, 0);
	private Process pEND = new Process("END", 0, 0, 0);
	
	public Process getpIdle() {
		return pIdle;
	}

	public void setpIdle(Process pIdle) {
		this.pIdle = pIdle;
	}

	public Process getpEND() {
		return pEND;
	}

	public void setpEND(Process pEND) {
		this.pEND = pEND;
	}
}
