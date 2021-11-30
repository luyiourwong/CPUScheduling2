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
	protected Map<Integer, Process> createScheduling(){
		Logger.logAlg(getAlg(), "========================");
		
		Map<Integer, Process> mapSch = new TreeMap<Integer, Process>();
		
		//�p�G�Ĥ@��p����F�ɶ����O0, �e����idle
		int next = 0;
		
		for(Process p : getListPro()) {
			//�p�G�U�@�Ӯɶ����F�ɶ���, ��idle
			if(next < p.getArrival()) {
				mapSch.put(next, MainCPUScheduling.getInstance().getSystem().getpIdle());
				Logger.logAlg(getAlg(), "at " + next + " : " + MainCPUScheduling.getInstance().getSystem().getpIdle().getName());
				next = p.getArrival();
			}
			mapSch.put(next, p);
			Logger.logAlg(getAlg(), "at " + next + " : " + p.getName());
			next += p.getBurst();
		}
		
		mapSch.put(next, MainCPUScheduling.getInstance().getSystem().getpEND());
		Logger.logAlg(getAlg(), "at " + next + " END ");
		
		Logger.logAlg(getAlg(), "========================");
		
		return mapSch;
	}
}
