package com.luyiourwong.se.hw2;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * To extends this, replace createScheduling().
 * To new this, just input listPro.
 * To run this, just use runSchedule() after input listPro.
 */
public class Schedule {

	private ScheduleList alg;
	
	private List<Process> listPro;
	
	private Map<Integer, Process> mapSch;
	private Map<Process, Integer> mapTimeRun;
	private Map<Process, Integer> mapTimeWait;
	
	/**
	 * @param listPro from MainCPUScheduling
	 */
	public Schedule(List<Process> listPro){
		setListPro(listPro);
	}
	
	public ScheduleList getAlg() {
		return alg;
	}

	protected void setAlg(ScheduleList alg) {
		this.alg = alg;
	}

	protected List<Process> getListPro() {
		return listPro;
	}

	private void setListPro(List<Process> listPro) {
		this.listPro = listPro;
	}

	public Map<Integer, Process> getMapSch() {
		return mapSch;
	}

	private void setMapSch(Map<Integer, Process> mapSch) {
		this.mapSch = mapSch;
	}

	private Map<Process, Integer> getMapTimeRun() {
		return mapTimeRun;
	}

	private void setMapTimeRun(Map<Process, Integer> mapTimeRun) {
		this.mapTimeRun = mapTimeRun;
	}

	private Map<Process, Integer> getMapTimeWait() {
		return mapTimeWait;
	}

	private void setMapTimeWait(Map<Process, Integer> mapTimeWait) {
		this.mapTimeWait = mapTimeWait;
	}
	
	/**
	 * create mapScheduling, create TurnaroundTime, create WaitingTime and print all.
	 * @return false if this.ListPro is empty, else true
	 */
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
		Logger.log("[" + getAlg() + "] ========================");
		
		for(Integer i : getMapSch().keySet()) {
			Process p = getMapSch().get(i);
			Logger.log("[" + getAlg() + "] at " + i + " : " + p.getName());
		}
		
		Logger.log("[" + getAlg() + "] ========================");
		
		for(Process p : getMapTimeRun().keySet()) {
			Integer i = getMapTimeRun().get(p);
			Logger.log("[" + getAlg() + "] " + p.getName() + " Turnaround Time: " + i);
			p.setValue(Process.TURN, getAlg().getNick(), i);
		}
		
		Logger.log("[" + getAlg() + "] ========================");
		
		for(Process p : getMapTimeWait().keySet()) {
			Integer i = getMapTimeWait().get(p);
			Logger.log("[" + getAlg() + "] " + p.getName() + " Waiting Time: " + i);
			p.setValue(Process.WAIT, getAlg().getNick(), i);
		}
		
		Logger.log("[" + getAlg() + "] ========================");
	}

	/**
	 * !! REPLACE THIS !!
	 * @return mapScheduling (default:null)
	 */
	protected Map<Integer, Process> createScheduling(){
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
