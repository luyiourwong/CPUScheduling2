package com.luyiourwong.se.hw2.schedules;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.luyiourwong.se.hw2.Logger;
import com.luyiourwong.se.hw2.Process;

/**
 * To extends this, replace createScheduling().
 * To new this, just input listPro.
 * To run this, just use runSchedule() after input listPro.
 */
public class Schedule {
	
	protected final static int RUNTIME = 999;

	private ScheduleList alg;
	
	private Map<Integer, Process> mapSch;
	private Map<Process, Integer> mapTimeRun;
	private Map<Process, Integer> mapTimeWait;
	
	/**
	 * @param listPro from MainCPUScheduling
	 */
	public Schedule(){
		
	}
	
	public ScheduleList getAlg() {
		return alg;
	}

	protected void setAlg(ScheduleList alg) {
		this.alg = alg;
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
	public boolean runSchedule(List<Process> listPro) {
		if(listPro == null) {
			return false;
		} else {
			setMapSch(createScheduling(listPro));
			setMapTimeRun(createTurnaroundTime(listPro));
			setMapTimeWait(createWaitingTime(listPro));
			
			printSchedule();
			return true;
		}
	}
	
	private void printSchedule() {
		Logger.logAlg(getAlg(), "========================");
		
		for(Integer i : getMapSch().keySet()) {
			Process p = getMapSch().get(i);
			Logger.logAlg(getAlg(), "at " + i + " : " + p.getName());
		}
		
		Logger.logAlg(getAlg(), "========================");
		
		for(Process p : getMapTimeRun().keySet()) {
			Integer i = getMapTimeRun().get(p);
			Logger.logAlg(getAlg(), "" + p.getName() + " Turnaround Time: " + i);
			p.setValue(Process.TURN, getAlg().getNick(), i);
		}
		
		Logger.logAlg(getAlg(), "========================");
		
		for(Process p : getMapTimeWait().keySet()) {
			Integer i = getMapTimeWait().get(p);
			Logger.logAlg(getAlg(), "" + p.getName() + " Waiting Time: " + i);
			p.setValue(Process.WAIT, getAlg().getNick(), i);
		}
		
		Logger.logAlg(getAlg(), "========================");
	}

	/**
	 * !! REPLACE THIS !!
	 * @return mapScheduling (default:null)
	 */
	protected Map<Integer, Process> createScheduling(List<Process> listPro){
		return null;
	}
	
	private Map<Process, Integer> createTurnaroundTime(List<Process> listPro){
		Map<Process, Integer> mapTimeRun = new TreeMap<Process, Integer>();
		
		boolean next = false;
		for(Process pro : listPro) {
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
	
	private Map<Process, Integer> createWaitingTime(List<Process> listPro){
		Map<Process, Integer> mapTimeWait = new TreeMap<Process, Integer>();
		
		for(Process pro : listPro) {
			for(Integer i : getMapSch().keySet()) {
				if(getMapSch().get(i) == pro) {
					mapTimeWait.put(pro, i - pro.getArrival());
				}
			}
		}
		
		return mapTimeWait;
	}
}
