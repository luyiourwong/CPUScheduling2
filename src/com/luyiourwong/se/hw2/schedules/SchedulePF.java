package com.luyiourwong.se.hw2.schedules;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.luyiourwong.se.hw2.Logger;
import com.luyiourwong.se.hw2.MainCPUScheduling;
import com.luyiourwong.se.hw2.Process;

/**
 * Priority First
 *
 */
public class SchedulePF extends Schedule{

	public SchedulePF() {
		setAlg(ScheduleList.PF);
	}

	@Override
	protected Map<Integer, Process> createScheduling(List<Process> listPro){
		Logger.logAlg(getAlg(), "========================");
		
		//init
		Map<Integer, Process> mapSch = new TreeMap<Integer, Process>();
		Map<Process, Integer> mapPro = new TreeMap<Process, Integer>();
		for(Process p : listPro) {
			mapPro.put(p, p.getBurst());
		}
		
		//init
		Process nowP = null;
		int next = 0;
		
		for(int count = 0; count <= RUNTIME; count++) {
			next -= 1;
			//如果nowP已經跑完, 換下一個
			if(next <= 0) {
				//重置計數器
				next = 0;
				//移除nowP
				if(nowP != null && nowP != MainCPUScheduling.getInstance().getSystem().getpIdle() && nowP != MainCPUScheduling.getInstance().getSystem().getpEND()) {
					mapPro.remove(nowP);
				}
				//如果已經沒有p要跑, 補上END並結束
				if(mapPro.isEmpty()) {
					mapSch.put(count, MainCPUScheduling.getInstance().getSystem().getpEND());
					Logger.logAlg(getAlg(), count, "END " + " ==========");
					break;
				}
				//如果還有p要跑
				for(Process p : mapPro.keySet()) {
					//如果下一個p還沒到, 補上Idle
					if(count < p.getArrival()) {
						mapSch.put(count, MainCPUScheduling.getInstance().getSystem().getpIdle());
						Logger.logAlg(getAlg(), count, "switch " + MainCPUScheduling.getInstance().getSystem().getpIdle().getName() + " ==========");
						nowP = MainCPUScheduling.getInstance().getSystem().getpIdle();
						next = p.getArrival() - count;
					//如果下一個p到了, 切換nowP
					} else {
						mapSch.put(count, p);
						Logger.logAlg(getAlg(), count, "switch " + p.getName() + " ==========");
						nowP = p;
						next += mapPro.get(p);
					}
					break;
				}
			//如果nowP沒跑完, 檢查有沒有人要插隊
			} else {
				Logger.logAlg(getAlg(), count, "run " + nowP.getName() + " : " + next);
				boolean first = true;
				for(Process p : mapPro.keySet()) {
					//檢查是否為第一個p
					if(first) {
						first = false;
						continue;
					}
					//如果有p到達
					if(count > p.getArrival()) {
						//並且該p的優先度比nowP優先度高, 減少nowP時間, 並切換nowP為該p
						if(p.getPriority() > nowP.getPriority()) {
							count -= 1;
							mapPro.put(nowP, (next+1));
							mapSch.put(count, p);
							Logger.logAlg(getAlg(), count, "left " + (next+1) + " : " + nowP.getName() + " (p=" + nowP.getPriority() + ")");
							Logger.logAlg(getAlg(), count, "cut " + count + " : " + p.getName() + " (p=" + p.getPriority() + ")" + " ==========");
							nowP = p;
							next = p.getBurst();
						} else {
							Logger.logAlg(getAlg(), count, "" + p.getName() + " waiting for : " + p.getArrival());
						}
					}
					break;
				}
			}
		}
		
		Logger.logAlg(getAlg(), "========================");
		
		return mapSch;
	}
}
