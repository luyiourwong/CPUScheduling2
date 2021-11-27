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
	
	/*
	 * values
	 */
	public static final String FCFS = "FCFS";
	public static final String SJF = "SJF";

	/*
	 * 
	 */
	private static MainCPUScheduling instance;
	
	public static boolean DEBUG = true;
	
	/*
	 * create instance
	 */

	public static void main(String[] args) {
		instance = new MainCPUScheduling();
		instance.init();
		instance.showGui();
	}
	
	public MainCPUScheduling() {
		
	}
	
	public static void setInstance(MainCPUScheduling newinstance) {
		instance = newinstance;
	}
	
	public static MainCPUScheduling getInstance() {
		return instance;
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
	}
	
	private void showGui() {
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
		//get input from file and create list process
		listInput = this.readFileFromFile(file);
		listPro = this.createListPro(listInput);
		
		//sort by arrival
		Collections.sort(listPro);
		for(Process p : listPro) {
			Logger.log("[after sort] process " + p.getName() + " : " + p.getPriority() + ", " + p.getBurst() + ", " + p.getArrival());
		}
		
		//FCFS
		
		Schedule fcfs = new ScheduleFCFS(listPro);
		fcfs.runSchedule();
		
		//SJF
		Schedule sjf = new ScheduleSJF(listPro);
		sjf.runSchedule();
		
		//gui
		
		getGuiMain().clearGui();
		getGuiMain().createAlgGui(FCFS, fcfs.getMapSch());
		getGuiMain().createAlgGui(SJF, sjf.getMapSch());
	}
	
	/*
	 * read file from string location to list string
	 */
	
	public List<String> readFileFromFile(File file) {
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
	
	private int changeTime = -1;
	
	public int getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(int changeTime) {
		this.changeTime = changeTime;
	}

	public List<Process> createListPro(List<String> listInput){
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
