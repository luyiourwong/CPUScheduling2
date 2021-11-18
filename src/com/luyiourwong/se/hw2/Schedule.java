package com.luyiourwong.se.hw2;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Schedule {

	private List<Process> listPro;
	
	private boolean hasScheduled;
	private Map<Integer, Process> mapSch;
	private Map<Process, Integer> mapTimeRun;
	private Map<Process, Integer> mapTimeWait;
	
	public Schedule(List<Process> listPro){
		setListPro(listPro);
	}
	
	public List<Process> getListPro() {
		return listPro;
	}

	public void setListPro(List<Process> listPro) {
		this.listPro = listPro;
	}
	
	public boolean isHasScheduled() {
		return hasScheduled;
	}

	public void setHasScheduled(boolean hasScheduled) {
		this.hasScheduled = hasScheduled;
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

	public Map<Integer, Process> createScheduling(){
		return null;
	}
	
	public boolean createTimes() {
		if(!isHasScheduled()) {
			return false;
		} else {
			setMapTimeRun(createTurnaroundTime());
			setMapTimeWait(createWaitingTime());
			return true;
		}
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
