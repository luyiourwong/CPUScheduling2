package com.luyiourwong.se.hw2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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
	private Map<Integer, Process> mapSchFCFS;
	private Map<Process, Integer> mapTimeRunFCFS;
	private Map<Process, Integer> mapTimeWaitFCFS;
	private Map<Integer, Process> mapSchSJF;
	private Map<Process, Integer> mapTimeRunSJF;
	private Map<Process, Integer> mapTimeWaitSJF;
	
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

	public Map<Integer, Process> getMapSchFCFS() {
		return mapSchFCFS;
	}

	public void setMapSchFCFS(Map<Integer, Process> mapSchFCFS) {
		this.mapSchFCFS = mapSchFCFS;
	}

	public Map<Process, Integer> getMapTimeRunFCFS() {
		return mapTimeRunFCFS;
	}

	public void setMapTimeRunFCFS(Map<Process, Integer> mapTimeRunFCFS) {
		this.mapTimeRunFCFS = mapTimeRunFCFS;
	}

	public Map<Process, Integer> getMapTimeWaitFCFS() {
		return mapTimeWaitFCFS;
	}

	public void setMapTimeWaitFCFS(Map<Process, Integer> mapTimeWaitFCFS) {
		this.mapTimeWaitFCFS = mapTimeWaitFCFS;
	}

	public Map<Integer, Process> getMapSchSJF() {
		return mapSchSJF;
	}

	public void setMapSchSJF(Map<Integer, Process> mapSchSJF) {
		this.mapSchSJF = mapSchSJF;
	}

	public Map<Process, Integer> getMapTimeRunSJF() {
		return mapTimeRunSJF;
	}

	public void setMapTimeRunSJF(Map<Process, Integer> mapTimeRunSJF) {
		this.mapTimeRunSJF = mapTimeRunSJF;
	}

	public Map<Process, Integer> getMapTimeWaitSJF() {
		return mapTimeWaitSJF;
	}

	public void setMapTimeWaitSJF(Map<Process, Integer> mapTimeWaitSJF) {
		this.mapTimeWaitSJF = mapTimeWaitSJF;
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
		
		mapSchFCFS = this.createSchedulingFCFS(listPro);
		for(Integer i : mapSchFCFS.keySet()) {
			Process p = mapSchFCFS.get(i);
			log("[FCFS] at " + i + " : " + p.getName());
		}
		
		log("[FCFS] ========================");
		
		mapTimeRunFCFS = this.getTurnaroundTime(listPro, mapSchFCFS);
		for(Process p : mapTimeRunFCFS.keySet()) {
			Integer i = mapTimeRunFCFS.get(p);
			log("[FCFS] " + p.getName() + " Turnaround Time: " + i);
			p.setFCFS_turn(i);
		}
		
		log("[FCFS] ========================");
		
		mapTimeWaitFCFS = this.getWaitingTime(listPro, mapSchFCFS);
		for(Process p : mapTimeWaitFCFS.keySet()) {
			Integer i = mapTimeWaitFCFS.get(p);
			log("[FCFS] " + p.getName() + " Waiting Time: " + i);
			p.setFCFS_wait(i);
		}
		
		log("[FCFS] ========================");
		
		//SJF
		
		log("[SJF] ========================");
		
		mapSchSJF = this.createSchedulingSJF(listPro);
		for(Integer i : mapSchSJF.keySet()) {
			Process p = mapSchSJF.get(i);
			log("[SJF] at " + i + " : " + p.getName());
		}
		
		log("[SJF] ========================");
		
		mapTimeRunSJF = this.getTurnaroundTime(listPro, mapSchSJF);
		for(Process p : mapTimeRunSJF.keySet()) {
			Integer i = mapTimeRunSJF.get(p);
			log("[SJF] " + p.getName() + " Turnaround Time: " + i);
			p.setSJF_turn(i);
		}
		
		log("[SJF] ========================");
		
		mapTimeWaitSJF = this.getWaitingTime(listPro, mapSchSJF);
		for(Process p : mapTimeWaitSJF.keySet()) {
			Integer i = mapTimeWaitSJF.get(p);
			log("[SJF] " + p.getName() + " Waiting Time: " + i);
			p.setSJF_wait(i);
		}
		
		log("[SJF] ========================");
		
		//gui
		
		getGuiMain().clearGui();
		int locY = 50;
		getGuiMain().createGuiPic(locY, "FCFS", mapSchFCFS);
		locY += 100;
		getGuiMain().createGuiPic(locY, "SJF", mapSchSJF);
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

	private Map<Integer, Process> createSchedulingFCFS(List<Process> listPro){
		logDEBUG("[sch FCFS] ========================");
		
		Map<Integer, Process> mapSch = new TreeMap<Integer, Process>();
		
		//如果第一個p的到達時間不是0, 前面補idle
		int next = 0;
		
		for(Process p : listPro) {
			//如果下一個時間比到達時間早, 補idle
			if(next < p.getArrival()) {
				mapSch.put(next, pIdle);
				logDEBUG("[sch FCFS] at " + next + " : " + pIdle.getName());
				next = p.getArrival();
			}
			mapSch.put(next, p);
			logDEBUG("[sch FCFS] at " + next + " : " + p.getName());
			next += p.getBurst();
		}
		
		mapSch.put(next, pEND);
		logDEBUG("[sch FCFS] at " + next + " END ");
		
		logDEBUG("[sch FCFS] ========================");
		return mapSch;
	}
	
	private Map<Integer, Process> createSchedulingSJF(List<Process> listPro){
		logDEBUG("[sch SJF] ========================");
		
		Map<Integer, Process> mapSch = new TreeMap<Integer, Process>();
		Map<Process, Integer> mapPro = new TreeMap<Process, Integer>();
		for(Process p : listPro) {
			mapPro.put(p, p.getBurst());
		}
		
		//如果第一個p的到達時間不是0, 前面補idle
		Process nowP = null;
		int next = 0;
		
		for(int count = 0; count <= 999; count++) {
			next -= 1;
			if(next <= 0) {
				next = 0;
				if(nowP != null && nowP != pIdle && nowP != pEND) {
					mapPro.remove(nowP);
				}
				if(mapPro.isEmpty()) {
					mapSch.put(count, pEND);
					logDEBUG("[sch SJF] " + count + " END ");
					break;
				}
				for(Process p : mapPro.keySet()) {
					if(count < p.getArrival()) {
						mapSch.put(count, pIdle);
						logDEBUG("[sch SJF] " + count + " : " + pIdle.getName());
						nowP = pIdle;
						next = p.getArrival() - count;
					} else {
						mapSch.put(count, p);
						logDEBUG("[sch SJF] " + count + " : " + p.getName());
						nowP = p;
						next += p.getBurst();
					}
					break;
				}
			} else {
				boolean first = true;
				for(Process p : mapPro.keySet()) {
					if(first) {
						first = false;
						continue;
					}
					logDEBUG("[sch SJF] to cut " + next + "/" + count + " : " + p.getName());
					if(count > p.getArrival()) {
						if(next > p.getBurst()) {
							count -= 1;
							mapPro.put(nowP, next);
							mapSch.put(count, p);
							logDEBUG("[sch SJF] cut " + count + " : " + p.getName());
							nowP = p;
							next = p.getBurst();
						}
					}
					break;
				}
			}
		}
		
		logDEBUG("[sch SJF] ========================");
		return mapSch;
	}
	
	private Map<Process, Integer> getTurnaroundTime(List<Process> listPro, Map<Integer, Process> mapSch){
		logDEBUG("[get turn time] ========================");
		
		Map<Process, Integer> mapTimeRun = new TreeMap<Process, Integer>();
		
		boolean next = false;
		for(Process pro : listPro) {
			int lastRunTime = 0;
			for(Integer i : mapSch.keySet()) {
				if(next) {
					next = false;
					lastRunTime = Integer.max(lastRunTime, i);
				} else {
					if(mapSch.get(i) == pro) {
						next = true;
					}
				}
			}
			mapTimeRun.put(pro, lastRunTime - pro.getArrival());
		}
		
		
		logDEBUG("[get turn time] ========================");
		
		return mapTimeRun;
	}
	
	private Map<Process, Integer> getWaitingTime(List<Process> listPro, Map<Integer, Process> mapSch){
		logDEBUG("[get wait time] ========================");
		
		Map<Process, Integer> mapTimeWait = new TreeMap<Process, Integer>();
		
		for(Process pro : listPro) {
			for(Integer i : mapSch.keySet()) {
				if(mapSch.get(i) == pro) {
					mapTimeWait.put(pro, i - pro.getArrival());
				}
			}
		}
		
		
		logDEBUG("[get wait time] ========================");
		
		return mapTimeWait;
	}
}
