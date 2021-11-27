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
	protected Map<Integer, Process> createScheduling(){
		Logger.logDEBUG("[sch " + getAlg().getNick() + "] ========================");
		
		//init
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
			//如果nowP已經跑完, 換下一個
			if(next <= 0) {
				//重置計數器
				next = 0;
				//移除nowP
				if(nowP != null && nowP != MainCPUScheduling.getInstance().getpIdle() && nowP != MainCPUScheduling.getInstance().getpEND()) {
					mapPro.remove(nowP);
				}
				//如果已經沒有p要跑, 補上END並結束
				if(mapPro.isEmpty()) {
					mapSch.put(count, MainCPUScheduling.getInstance().getpEND());
					Logger.logDEBUG("[sch " + getAlg().getNick() + "] [" + count + "] END " + " ==========");
					break;
				}
				//如果還有p要跑
				for(Process p : mapPro.keySet()) {
					//如果下一個p還沒到, 補上Idle
					if(count < p.getArrival()) {
						mapSch.put(count, MainCPUScheduling.getInstance().getpIdle());
						Logger.logDEBUG("[sch " + getAlg().getNick() + "] [" + count + "] : " + MainCPUScheduling.getInstance().getpIdle().getName() + " ==========");
						nowP = MainCPUScheduling.getInstance().getpIdle();
						next = p.getArrival() - count;
					//如果下一個p到了, 切換nowP
					} else {
						mapSch.put(count, p);
						Logger.logDEBUG("[sch " + getAlg().getNick() + "] [" + count + "] : " + p.getName() + " ==========");
						nowP = p;
						next += mapPro.get(p);
					}
					break;
				}
			//如果nowP沒跑完, 檢查有沒有人要插隊
			} else {
				Logger.logDEBUG("[sch " + getAlg().getNick() + "] [" + count + "] run " + nowP.getName() + " : " + next);
				boolean first = true;
				for(Process p : mapPro.keySet()) {
					//檢查是否為第一個p
					if(first) {
						first = false;
						continue;
					}
					//如果有p到達
					if(count > p.getArrival()) {
						//並且該p的所需時間比nowP剩餘時間短, 減少nowP時間, 並切換nowP為該p
						if(next > p.getBurst()) {
							count -= 1;
							mapPro.put(nowP, (next+1));
							mapSch.put(count, p);
							Logger.logDEBUG("[sch " + getAlg().getNick() + "] [" + count + "] left " + (next+1) + " : " + nowP.getName());
							Logger.logDEBUG("[sch " + getAlg().getNick() + "] [" + count + "] cut " + count + " : " + p.getName() + " ==========");
							nowP = p;
							next = p.getBurst();
						} else {
							Logger.logDEBUG("[sch " + getAlg().getNick() + "] [" + count + "] " + p.getName() + " waiting for : " + p.getArrival());
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
