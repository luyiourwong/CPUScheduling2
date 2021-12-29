package com.luyiourwong.se.hw2test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.luyiourwong.se.hw2.schedules.Process;
import com.luyiourwong.se.hw2.schedules.Schedule;
import com.luyiourwong.se.hw2.schedules.ScheduleFCFS;
import com.luyiourwong.se.hw2.schedules.SchedulePF;
import com.luyiourwong.se.hw2.schedules.ScheduleRR;
import com.luyiourwong.se.hw2.schedules.ScheduleSJF;
import com.luyiourwong.se.hw2.schedules.SystemSchedule;

/**
 *
 */
class SystemScheduleTest {
	
	/*for(int i : mapActual.keySet()) {
		Process p = mapActual.get(i);
		System.out.println("mapExpected.put(" + i + ", new Process(\"" + p.getName() + "\", " + p.getPriority() + ", " + p.getBurst() + ", " + p.getArrival() + "));");
	}*/
	
	static SystemSchedule system;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		system = new SystemSchedule();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		system.initSchedules();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		
	}
	
	List<String> getListInput(){
		List<String> listInput = new ArrayList<String>();
		listInput.add("10");
		listInput.add("P1");
		listInput.add("40");
		listInput.add("20");
		listInput.add("0");
		listInput.add("P2");
		listInput.add("30");
		listInput.add("25");
		listInput.add("25");
		listInput.add("P3");
		listInput.add("35");
		listInput.add("25");
		listInput.add("30");
		listInput.add("P4");
		listInput.add("35");
		listInput.add("15");
		listInput.add("60");
		listInput.add("P5");
		listInput.add("5");
		listInput.add("20");
		listInput.add("100");
		listInput.add("P6");
		listInput.add("10");
		listInput.add("10");
		listInput.add("105");
		return listInput;
	}
	
	/*
	 * test
	 */

	/**
	 * A1
	 */
	@Test
	void testA1() {
		/*
		 * values
		 */
		
		/*
		 * actual
		 */
		system.schedulingSetup(getListInput());
		List<Process> listProActual = system.getListPro();
		
		/*
    	 * expected
    	 */
		
		List<Process> listProExpected = new ArrayList<Process>();
		listProExpected.add(new Process("P1", 40, 20, 0));
		listProExpected.add(new Process("P2", 30, 25, 25));
		listProExpected.add(new Process("P3", 35, 25, 30));
		listProExpected.add(new Process("P4", 35, 15, 60));
		listProExpected.add(new Process("P5", 5, 20, 100));
		listProExpected.add(new Process("P6", 10, 10, 105));
		
		/*
		 * test
		 */
		
		for(int i = 0; i < listProExpected.size(); i++) {
			System.out.println("[testA1] " + "testing p " + i);
			assertTrue(listProExpected.get(i).equals(listProActual.get(i)));
		}
	}
	
	/**
	 * A2
	 */
	@Test
	void testA2() {
		/*
		 * values
		 */
		
		/*
		 * actual
		 */
		system.schedulingSetup(getListInput());
		List<Process> listProActual = system.getListPro();
		
		/*
    	 * expected
    	 */
		
		List<Process> listProExpected = new ArrayList<Process>();
		listProExpected.add(new Process("P1", 40, 20, 0));
		listProExpected.add(new Process("P2", 30, 25, 25));
		listProExpected.add(new Process("P3", 35, 25, 30));
		listProExpected.add(new Process("P4", 35, 15, 60));
		listProExpected.add(new Process("P5", 5, 20, 100));
		listProExpected.add(new Process("P6", 10, 10, 105));
		
		/*
		 * test
		 */
		
		for(int i = 0; i < listProExpected.size(); i++) {
			System.out.println("[testA2] " + "testing p " + i);
			assertTrue(listProExpected.get(i).equals(listProActual.get(i)));
		}
	}
	
	/**
	 * A3
	 */
	@Test
	void testA3() {
		/*
		 * values
		 */
		
		/*
		 * actual
		 */
		system.schedulingSetup(getListInput());
		List<Process> listProActual = system.getListPro();
		
		/*
    	 * expected
    	 */
		
		List<Process> listProExpected = new ArrayList<Process>();
		listProExpected.add(new Process("P5", 5, 20, 100));
		listProExpected.add(new Process("P3", 35, 25, 30));
		listProExpected.add(new Process("P1", 40, 20, 0));
		listProExpected.add(new Process("P2", 30, 25, 25));
		listProExpected.add(new Process("P4", 35, 15, 60));
		listProExpected.add(new Process("P6", 10, 10, 105));
		Collections.sort(listProExpected);
		
		/*
		 * test
		 */
		
		for(int i = 0; i < listProExpected.size(); i++) {
			System.out.println("[testA3] " + "testing p " + i);
			assertTrue(listProExpected.get(i).equals(listProActual.get(i)));
		}
	}
	
	/**
	 * B1
	 */
	@Test
	void testB1() {
		/*
		 * values
		 */
		
		/*
		 * actual
		 */
		system.schedulingSetup(getListInput());
		system.schedulingSchedules();
		List<Schedule> listSchActual = system.getListSch();
		Schedule schActual = null;
		for(Schedule sch : listSchActual) {
			if(sch instanceof ScheduleFCFS) {
				schActual = sch;
				break;
			}
		}
		Map<Integer, Process> mapActual = schActual.getMapSch();
		
		/*
    	 * expected
    	 */
		
		Map<Integer, Process> mapExpected = new TreeMap<Integer, Process>();
		mapExpected.put(0, new Process("P1", 40, 20, 0));
		mapExpected.put(20, new Process("/", 0, 0, 0));
		mapExpected.put(25, new Process("P2", 30, 25, 25));
		mapExpected.put(50, new Process("P3", 35, 25, 30));
		mapExpected.put(75, new Process("P4", 35, 15, 60));
		mapExpected.put(90, new Process("/", 0, 0, 0));
		mapExpected.put(100, new Process("P5", 5, 20, 100));
		mapExpected.put(120, new Process("P6", 10, 10, 105));
		mapExpected.put(130, new Process("END", 0, 0, 0));
		
		/*
		 * test
		 */
		
		for(int i : mapExpected.keySet()) {
			System.out.println("[testB1] " + "testing time " + i);
			assertTrue(mapExpected.get(i).equals(mapActual.get(i)));
		}
	}
	
	/**
	 * B2
	 */
	@Test
	void testB2() {
		/*
		 * values
		 */
		
		/*
		 * actual
		 */
		system.schedulingSetup(getListInput());
		system.schedulingSchedules();
		List<Schedule> listSchActual = system.getListSch();
		Schedule schActual = null;
		for(Schedule sch : listSchActual) {
			if(sch instanceof ScheduleSJF) {
				schActual = sch;
				break;
			}
		}
		Map<Integer, Process> mapActual = schActual.getMapSch();
		
		/*
    	 * expected
    	 */
		
		Map<Integer, Process> mapExpected = new TreeMap<Integer, Process>();
		mapExpected.put(0, new Process("P1", 40, 20, 0));
		mapExpected.put(20, new Process("/", 0, 0, 0));
		mapExpected.put(25, new Process("P2", 30, 25, 25));
		mapExpected.put(50, new Process("P3", 35, 25, 30));
		mapExpected.put(75, new Process("P4", 35, 15, 60));
		mapExpected.put(90, new Process("/", 0, 0, 0));
		mapExpected.put(100, new Process("P5", 5, 20, 100));
		mapExpected.put(105, new Process("P6", 10, 10, 105));
		mapExpected.put(115, new Process("P5", 5, 20, 100));
		mapExpected.put(130, new Process("END", 0, 0, 0));
		
		/*
		 * test
		 */
		
		for(int i : mapExpected.keySet()) {
			System.out.println("[testB2] " + "testing time " + i);
			assertTrue(mapExpected.get(i).equals(mapActual.get(i)));
		}
	}
	
	/**
	 * B3
	 */
	@Test
	void testB3() {
		/*
		 * values
		 */
		
		/*
		 * actual
		 */
		system.schedulingSetup(getListInput());
		system.schedulingSchedules();
		List<Schedule> listSchActual = system.getListSch();
		Schedule schActual = null;
		for(Schedule sch : listSchActual) {
			if(sch instanceof SchedulePF) {
				schActual = sch;
				break;
			}
		}
		Map<Integer, Process> mapActual = schActual.getMapSch();
		
		/*
    	 * expected
    	 */
		
		Map<Integer, Process> mapExpected = new TreeMap<Integer, Process>();
		mapExpected.put(0, new Process("P1", 40, 20, 0));
		mapExpected.put(20, new Process("/", 0, 0, 0));
		mapExpected.put(25, new Process("P2", 30, 25, 25));
		mapExpected.put(30, new Process("P3", 35, 25, 30));
		mapExpected.put(55, new Process("P2", 30, 25, 25));
		mapExpected.put(60, new Process("P4", 35, 15, 60));
		mapExpected.put(75, new Process("P2", 30, 25, 25));
		mapExpected.put(90, new Process("/", 0, 0, 0));
		mapExpected.put(100, new Process("P5", 5, 20, 100));
		mapExpected.put(105, new Process("P6", 10, 10, 105));
		mapExpected.put(115, new Process("P5", 5, 20, 100));
		mapExpected.put(130, new Process("END", 0, 0, 0));
		
		/*
		 * test
		 */
		
		for(int i : mapExpected.keySet()) {
			System.out.println("[testB3] " + "testing time " + i);
			assertTrue(mapExpected.get(i).equals(mapActual.get(i)));
		}
	}
	
	/**
	 * B4
	 */
	@Test
	void testB4() {
		/*
		 * values
		 */
		
		/*
		 * actual
		 */
		system.schedulingSetup(getListInput());
		system.schedulingSchedules();
		List<Schedule> listSchActual = system.getListSch();
		Schedule schActual = null;
		for(Schedule sch : listSchActual) {
			if(sch instanceof ScheduleRR) {
				schActual = sch;
				break;
			}
		}
		Map<Integer, Process> mapActual = schActual.getMapSch();
		
		/*
    	 * expected
    	 */
		
		Map<Integer, Process> mapExpected = new TreeMap<Integer, Process>();
		mapExpected.put(0, new Process("P1", 40, 20, 0));
		mapExpected.put(20, new Process("/", 0, 0, 0));
		mapExpected.put(25, new Process("P2", 30, 25, 25));
		mapExpected.put(35, new Process("P3", 35, 25, 30));
		mapExpected.put(45, new Process("P2", 30, 25, 25));
		mapExpected.put(55, new Process("P3", 35, 25, 30));
		mapExpected.put(65, new Process("P2", 30, 25, 25));
		mapExpected.put(70, new Process("P3", 35, 25, 30));
		mapExpected.put(75, new Process("P4", 35, 15, 60));
		mapExpected.put(90, new Process("/", 0, 0, 0));
		mapExpected.put(100, new Process("P5", 5, 20, 100));
		mapExpected.put(110, new Process("P6", 10, 10, 105));
		mapExpected.put(120, new Process("P5", 5, 20, 100));
		mapExpected.put(130, new Process("END", 0, 0, 0));
		
		/*
		 * test
		 */
		
		for(int i : mapExpected.keySet()) {
			System.out.println("[testB4] " + "testing time " + i);
			assertTrue(mapExpected.get(i).equals(mapActual.get(i)));
		}
	}

}
