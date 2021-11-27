package com.luyiourwong.se.hw2;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Shortest Job First
 *
 */
public class ScheduleSJF extends Schedule{

	public ScheduleSJF(List<Process> listPro) {
		super(listPro);
		setAlg(ScheduleList.SJF);
	}

	@Override
	public Map<Integer, Process> createScheduling(){
		Logger.logDEBUG("[sch " + getAlg().getNick() + "] ========================");
		
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
					Logger.logDEBUG("[sch " + getAlg().getNick() + "] " + count + " END ");
					break;
				}
				for(Process p : mapPro.keySet()) {
					if(count < p.getArrival()) {
						mapSch.put(count, MainCPUScheduling.getInstance().getpIdle());
						Logger.logDEBUG("[sch " + getAlg().getNick() + "] " + count + " : " + MainCPUScheduling.getInstance().getpIdle().getName());
						nowP = MainCPUScheduling.getInstance().getpIdle();
						next = p.getArrival() - count;
					} else {
						mapSch.put(count, p);
						Logger.logDEBUG("[sch " + getAlg().getNick() + "] " + count + " : " + p.getName());
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
					Logger.logDEBUG("[sch " + getAlg().getNick() + "] to cut " + next + "/" + count + " : " + p.getName());
					if(count > p.getArrival()) {
						if(next > p.getBurst()) {
							count -= 1;
							mapPro.put(nowP, next);
							mapSch.put(count, p);
							Logger.logDEBUG("[sch " + getAlg().getNick() + "] cut " + count + " : " + p.getName());
							nowP = p;
							next = p.getBurst();
						}
					}
					break;
				}
			}
		}
		
		Logger.logDEBUG("[sch " + getAlg().getNick() + "] ========================");
		
		return mapSch;
	}
}
