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
			//�p�GnowP�w�g�]��, ���U�@��
			if(next <= 0) {
				//���m�p�ƾ�
				next = 0;
				//����nowP
				if(nowP != null && nowP != MainCPUScheduling.getInstance().getSystem().getpIdle() && nowP != MainCPUScheduling.getInstance().getSystem().getpEND()) {
					mapPro.remove(nowP);
				}
				//�p�G�w�g�S��p�n�], �ɤWEND�õ���
				if(mapPro.isEmpty()) {
					mapSch.put(count, MainCPUScheduling.getInstance().getSystem().getpEND());
					Logger.logAlg(getAlg(), count, "END " + " ==========");
					break;
				}
				//�p�G�٦�p�n�]
				for(Process p : mapPro.keySet()) {
					//�p�G�U�@��p�٨S��, �ɤWIdle
					if(count < p.getArrival()) {
						mapSch.put(count, MainCPUScheduling.getInstance().getSystem().getpIdle());
						Logger.logAlg(getAlg(), count, "switch " + MainCPUScheduling.getInstance().getSystem().getpIdle().getName() + " ==========");
						nowP = MainCPUScheduling.getInstance().getSystem().getpIdle();
						next = p.getArrival() - count;
					//�p�G�U�@��p��F, ����nowP
					} else {
						mapSch.put(count, p);
						Logger.logAlg(getAlg(), count, "switch " + p.getName() + " ==========");
						nowP = p;
						next += mapPro.get(p);
					}
					break;
				}
			//�p�GnowP�S�]��, �ˬd���S���H�n����
			} else {
				Logger.logAlg(getAlg(), count, "run " + nowP.getName() + " : " + next);
				boolean first = true;
				for(Process p : mapPro.keySet()) {
					//�ˬd�O�_���Ĥ@��p
					if(first) {
						first = false;
						continue;
					}
					//�p�G��p��F
					if(count > p.getArrival()) {
						//�åB��p���u���פ�nowP�u���װ�, ���nowP�ɶ�, �ä���nowP����p
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
