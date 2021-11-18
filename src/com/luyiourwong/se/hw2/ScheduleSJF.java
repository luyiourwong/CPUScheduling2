package com.luyiourwong.se.hw2;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ScheduleSJF extends Schedule{

	public ScheduleSJF(List<Process> listPro) {
		super(listPro);
		setAlgName(MainCPUScheduling.SJF);
	}

	@Override
	public Map<Integer, Process> createScheduling(){
		MainCPUScheduling.getInstance().logDEBUG("[sch SJF] ========================");
		
		Map<Integer, Process> mapSch = new TreeMap<Integer, Process>();
		Map<Process, Integer> mapPro = new TreeMap<Process, Integer>();
		for(Process p : getListPro()) {
			mapPro.put(p, p.getBurst());
		}
		
		//如果第一個p的到達時間不是0, 前面補idle
		Process nowP = null;
		int next = 0;
		
		for(int count = 0; count <= 999; count++) {
			next -= 1;
			if(next <= 0) {
				next = 0;
				if(nowP != null && nowP != MainCPUScheduling.getInstance().getpIdle() && nowP != MainCPUScheduling.getInstance().getpEND()) {
					mapPro.remove(nowP);
				}
				if(mapPro.isEmpty()) {
					mapSch.put(count, MainCPUScheduling.getInstance().getpEND());
					MainCPUScheduling.getInstance().logDEBUG("[sch SJF] " + count + " END ");
					break;
				}
				for(Process p : mapPro.keySet()) {
					if(count < p.getArrival()) {
						mapSch.put(count, MainCPUScheduling.getInstance().getpIdle());
						MainCPUScheduling.getInstance().logDEBUG("[sch SJF] " + count + " : " + MainCPUScheduling.getInstance().getpIdle().getName());
						nowP = MainCPUScheduling.getInstance().getpIdle();
						next = p.getArrival() - count;
					} else {
						mapSch.put(count, p);
						MainCPUScheduling.getInstance().logDEBUG("[sch SJF] " + count + " : " + p.getName());
						nowP = p;
						next += p.getBurst();
					}
					break;
				}
			} else {
				boolean first = true;
				for(Process p : mapPro.keySet()) {
					if(first) {
						first = false;
						continue;
					}
					MainCPUScheduling.getInstance().logDEBUG("[sch SJF] to cut " + next + "/" + count + " : " + p.getName());
					if(count > p.getArrival()) {
						if(next > p.getBurst()) {
							count -= 1;
							mapPro.put(nowP, next);
							mapSch.put(count, p);
							MainCPUScheduling.getInstance().logDEBUG("[sch SJF] cut " + count + " : " + p.getName());
							nowP = p;
							next = p.getBurst();
						}
					}
					break;
				}
			}
		}
		
		MainCPUScheduling.getInstance().logDEBUG("[sch SJF] ========================");
		
		return mapSch;
	}
}
