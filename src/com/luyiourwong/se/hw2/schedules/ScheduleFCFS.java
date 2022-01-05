package com.luyiourwong.se.hw2.schedules;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * First Come First Served
 *
 */
public class ScheduleFCFS extends Schedule{

	public ScheduleFCFS() {
		setAlg(ScheduleList.FCFS);
	}

	@Override
	protected Map<Integer, Process> createScheduling(List<Process> listPro){
		SystemSchedule.logAlg(getAlg(), "========================");
		
		Map<Integer, Process> mapSch = new TreeMap<Integer, Process>();
		
		//init
		int next = 0;
		
		for(Process p : listPro) {
			//如果下一個時間比到達時間早, 補idle
			if(next < p.getArrival()) {
				mapSch.put(next, SystemSchedule.pIdle);
				SystemSchedule.logAlg(getAlg(), "at " + next + " : " + SystemSchedule.pIdle.getName());
				next = p.getArrival();
			}
			mapSch.put(next, p);
			SystemSchedule.logAlg(getAlg(), "at " + next + " : " + p.getName());
			next += p.getBurst();
		}
		
		mapSch.put(next, SystemSchedule.pEND);
		SystemSchedule.logAlg(getAlg(), "at " + next + " END ");
		
		SystemSchedule.logAlg(getAlg(), "========================");
		
		return mapSch;
	}
}
