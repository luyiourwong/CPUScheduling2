package com.luyiourwong.se.hw2.schedules;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Shortest Job First
 *
 */
public class ScheduleSJF extends Schedule{

	public ScheduleSJF() {
		setAlg(ScheduleList.SJF);
	}

	@Override
	protected Map<Integer, Process> createScheduling(List<Process> listPro){
		SystemSchedule.logAlg(getAlg(), "========================");
		
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
				if(nowP != null && nowP != SystemSchedule.pIdle && nowP != SystemSchedule.pEND) {
					mapPro.remove(nowP);
				}
				//�p�G�w�g�S��p�n�], �ɤWEND�õ���
				if(mapPro.isEmpty()) {
					mapSch.put(count, SystemSchedule.pEND);
					SystemSchedule.logAlg(getAlg(), count, "END " + " ==========");
					break;
				}
				//�p�G�٦�p�n�]
				for(Process p : mapPro.keySet()) {
					//�p�G�U�@��p�٨S��, �ɤWIdle
					if(count < p.getArrival()) {
						mapSch.put(count, SystemSchedule.pIdle);
						SystemSchedule.logAlg(getAlg(), count, "switch " + SystemSchedule.pIdle.getName() + " ==========");
						nowP = SystemSchedule.pIdle;
						next = p.getArrival() - count;
					//�p�G�U�@��p��F, ����nowP
					} else {
						mapSch.put(count, p);
						SystemSchedule.logAlg(getAlg(), count, "switch " + p.getName() + " ==========");
						nowP = p;
						next += mapPro.get(p);
					}
					break;
				}
			//�p�GnowP�S�]��, �ˬd���S���H�n����
			} else {
				SystemSchedule.logAlg(getAlg(), count, "run " + nowP.getName() + " : " + next);
				boolean first = true;
				for(Process p : mapPro.keySet()) {
					//�ˬd�O�_���Ĥ@��p
					if(first) {
						first = false;
						continue;
					}
					//�p�G��p��F
					if(count > p.getArrival()) {
						//�åB��p���һݮɶ���nowP�Ѿl�ɶ��u, ���nowP�ɶ�, �ä���nowP����p
						if(next > p.getBurst()) {
							count -= 1;
							mapPro.put(nowP, (next+1));
							mapSch.put(count, p);
							SystemSchedule.logAlg(getAlg(), count, "left " + (next+1) + " : " + nowP.getName());
							SystemSchedule.logAlg(getAlg(), count, "cut " + count + " : " + p.getName() + " ==========");
							nowP = p;
							next = mapPro.get(p);
						} else {
							SystemSchedule.logAlg(getAlg(), count, "" + p.getName() + " waiting for : " + p.getArrival());
						}
					}
					break;
				}
			}
		}
		
		SystemSchedule.logAlg(getAlg(), "========================");
		
		return mapSch;
	}
}
