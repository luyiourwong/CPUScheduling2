package com.luyiourwong.se.hw2;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ScheduleFCFS extends Schedule{

	public ScheduleFCFS(List<Process> listPro) {
		super(listPro);
	}

	@Override
	public Map<Integer, Process> createScheduling(){
		MainCPUScheduling.getInstance().logDEBUG("[sch FCFS] ========================");
		
		Map<Integer, Process> mapSch = new TreeMap<Integer, Process>();
		
		//如果第一個p的到達時間不是0, 前面補idle
		int next = 0;
		
		for(Process p : getListPro()) {
			//如果下一個時間比到達時間早, 補idle
			if(next < p.getArrival()) {
				mapSch.put(next, MainCPUScheduling.getInstance().getpIdle());
				MainCPUScheduling.getInstance().logDEBUG("[sch FCFS] at " + next + " : " + MainCPUScheduling.getInstance().getpIdle().getName());
				next = p.getArrival();
			}
			mapSch.put(next, p);
			MainCPUScheduling.getInstance().logDEBUG("[sch FCFS] at " + next + " : " + p.getName());
			next += p.getBurst();
		}
		
		mapSch.put(next, MainCPUScheduling.getInstance().getpEND());
		MainCPUScheduling.getInstance().logDEBUG("[sch FCFS] at " + next + " END ");
		
		MainCPUScheduling.getInstance().logDEBUG("[sch FCFS] ========================");
		
		setHasScheduled(true);
		this.setMapSch(mapSch);
		return this.getMapSch();
	}
}
