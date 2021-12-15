package com.luyiourwong.se.hw2.schedules;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.luyiourwong.se.hw2.Logger;
import com.luyiourwong.se.hw2.MainCPUScheduling;
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
		
		//init
		Map<Integer, Process> mapSch = new TreeMap<Integer, Process>();
		Map<Process, Integer> mapPro = new TreeMap<Process, Integer>();
		for(Process p : listPro) {
			mapPro.put(p, p.getBurst());
		}
		
		//init
		Process nowP = null;
		int next = 0;
		int robin = 0;
		
		for(int count = 0; count <= RUNTIME; count++) {
			//run
			next -= 1;
			robin += 1;
			
			//�p�GnowP�w�g�]��, ���U�@��
			if(next <= 0) {
				//���m�p�ƾ�
				next = 0;
				robin = 0;
				//����nowP
				if(nowP != null && nowP != MainCPUScheduling.getInstance().getSystem().getpIdle() && nowP != MainCPUScheduling.getInstance().getSystem().getpEND()) {
					mapPro.remove(nowP);
				}
				//�p�G�w�g�S��p�n�], �ɤWEND�õ���
				if(mapPro == null || mapPro.isEmpty()) {
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
				for(Process p : mapPro.keySet()) {
					//�ˬd�O�_���Ĥ@��p
					if(p == nowP) {
						continue;
					}
					//�p�G��p��F
					if(count > p.getArrival()) {
						//�åB��p���ϥήɶ��w�g�j��ChangeTime
						if(robin > getChangeTime()) {
							//switch
							count -= 1;
							mapPro.put(nowP, (next+1));
							mapSch.put(count, p);
							Logger.logAlg(getAlg(), count, "[" + robin + "]" + "left " + (next+1) + " : " + nowP.getName());
							Logger.logAlg(getAlg(), count, "[" + robin + "]" + "cut " + count + " : " + p.getName() + " ==========");
							nowP = p;
							next = mapPro.get(p);
							
							//reset
							robin = 0;
						} else {
							Logger.logAlg(getAlg(), count, "[" + robin + "]" + p.getName() + " waiting from : " + p.getArrival());
						}
					}
					break;
				}
			}
		}
		
		Logger.logAlg(getAlg(), "========================");
		
		return mapSch;
	}
	
	private int changeTime = -1;
	
	private int getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(int changeTime) {
		this.changeTime = changeTime;
	}
}