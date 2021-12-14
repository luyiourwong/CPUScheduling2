package com.luyiourwong.se.hw2.schedules;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.luyiourwong.se.hw2.Logger;
import com.luyiourwong.se.hw2.Process;

/**
 * Round Robin
 *
 */
public class ScheduleRR extends Schedule{

	public ScheduleRR() {
		setAlg(ScheduleList.RR);
	}

	@Override
	protected Map<Integer, Process> createScheduling(List<Process> listPro){
		Logger.logAlg(getAlg(), "========================");
		
		Map<Integer, Process> mapSch = new TreeMap<Integer, Process>();
		
		Logger.logAlg(getAlg(), "========================");
		
		return mapSch;
	}
}
