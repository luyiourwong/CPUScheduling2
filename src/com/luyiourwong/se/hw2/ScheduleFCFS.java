package com.luyiourwong.se.hw2;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * First Come First Served
 *
 */
public class ScheduleFCFS extends Schedule{

	public ScheduleFCFS(List<Process> listPro) {
		super(listPro);
		setAlg(ScheduleList.FCFS);
	}

	@Override
	public Map<Integer, Process> createScheduling(){
		Logger.logDEBUG("[sch " + getAlg().getNick() + "] ========================");
		
		Map<Integer, Process> mapSch = new TreeMap<Integer, Process>();
		
		//�p�G�Ĥ@��p����F�ɶ����O0, �e����idle
		int next = 0;
		
		for(Process p : getListPro()) {
			//�p�G�U�@�Ӯɶ����F�ɶ���, ��idle
			if(next < p.getArrival()) {
				mapSch.put(next, MainCPUScheduling.getInstance().getpIdle());
				Logger.logDEBUG("[sch " + getAlg().getNick() + "] at " + next + " : " + MainCPUScheduling.getInstance().getpIdle().getName());
				next = p.getArrival();
			}
			mapSch.put(next, p);
			Logger.logDEBUG("[sch " + getAlg().getNick() + "] at " + next + " : " + p.getName());
			next += p.getBurst();
		}
		
		mapSch.put(next, MainCPUScheduling.getInstance().getpEND());
		Logger.logDEBUG("[sch " + getAlg().getNick() + "] at " + next + " END ");
		
		Logger.logDEBUG("[sch " + getAlg().getNick() + "] ========================");
		
		return mapSch;
	}
}
