package com.luyiourwong.se.hw2;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Schedule {

	private String algName;
	
	private List<Process> listPro;
	
	private Map<Integer, Process> mapSch;
	private Map<Process, Integer> mapTimeRun;
	private Map<Process, Integer> mapTimeWait;
	
	public Schedule(List<Process> listPro){
		setListPro(listPro);
	}
	
	public String getAlgName() {
		return algName;
	}

	public void setAlgName(String algName) {
		this.algName = algName;
	}

	public List<Process> getListPro() {
		return listPro;
	}

	public void setListPro(List<Process> listPro) {
		this.listPro = listPro;
	}

	public Map<Integer, Process> getMapSch() {
		return mapSch;
	}

	public void setMapSch(Map<Integer, Process> mapSch) {
		this.mapSch = mapSch;
	}

	public Map<Process, Integer> getMapTimeRun() {
		return mapTimeRun;
	}

	public void setMapTimeRun(Map<Process, Integer> mapTimeRun) {
		this.mapTimeRun = mapTimeRun;
	}

	public Map<Process, Integer> getMapTimeWait() {
		return mapTimeWait;
	}

	public void setMapTimeWait(Map<Process, Integer> mapTimeWait) {
		this.mapTimeWait = mapTimeWait;
	}
	
	public boolean runSchedule() {
		if(getListPro() == null) {
			return false;
		} else {
			setMapSch(createScheduling());
			setMapTimeRun(createTurnaroundTime());
			setMapTimeWait(createWaitingTime());
			
			printSchedule();
			return true;
		}
	}
	
	private void printSchedule() {
		MainCPUScheduling.getInstance().log("[" + getAlgName() + "] ========================");
		
		for(Integer i : getMapSch().keySet()) {
			Process p = getMapSch().get(i);
			MainCPUScheduling.getInstance().log("[" + getAlgName() + "] at " + i + " : " + p.getName());
		}
		
		MainCPUScheduling.getInstance().log("[" + getAlgName() + "] ========================");
		
		for(Process p : getMapTimeRun().keySet()) {
			Integer i = getMapTimeRun().get(p);
			MainCPUScheduling.getInstance().log("[" + getAlgName() + "] " + p.getName() + " Turnaround Time: " + i);
			p.setValue(Process.TURN, getAlgName(), i);
		}
		
		MainCPUScheduling.getInstance().log("[" + getAlgName() + "] ========================");
		
		for(Process p : getMapTimeWait().keySet()) {
			Integer i = getMapTimeWait().get(p);
			MainCPUScheduling.getInstance().log("[" + getAlgName() + "] " + p.getName() + " Waiting Time: " + i);
			p.setValue(Process.WAIT, getAlgName(), i);
		}
		
		MainCPUScheduling.getInstance().log("[" + getAlgName() + "] ========================");
	}

	public Map<Integer, Process> createScheduling(){
		return null;
	}
	
	private Map<Process, Integer> createTurnaroundTime(){
		Map<Process, Integer> mapTimeRun = new TreeMap<Process, Integer>();
		
		boolean next = false;
		for(Process pro : getListPro()) {
			int lastRunTime = 0;
			for(Integer i : getMapSch().keySet()) {
				if(next) {
					next = false;
					lastRunTime = Integer.max(lastRunTime, i);
				} else {
					if(getMapSch().get(i) == pro) {
						next = true;
					}
				}
			}
			mapTimeRun.put(pro, lastRunTime - pro.getArrival());
		}
		
		return mapTimeRun;
	}
	
	private Map<Process, Integer> createWaitingTime(){
		Map<Process, Integer> mapTimeWait = new TreeMap<Process, Integer>();
		
		for(Process pro : getListPro()) {
			for(Integer i : getMapSch().keySet()) {
				if(getMapSch().get(i) == pro) {
					mapTimeWait.put(pro, i - pro.getArrival());
				}
			}
		}
		
		return mapTimeWait;
	}
}
