package com.luyiourwong.se.hw2;

import com.luyiourwong.se.hw2.gui.GuiMain;
import com.luyiourwong.se.hw2.schedules.ScheduleList;
import com.luyiourwong.se.hw2.schedules.SystemSchedule;

/**
 * https://sites.google.com/view/luyr-se-final
 */
public class MainCPUScheduling{

	/*
	 * 
	 */
	private static MainCPUScheduling instance;
	
	public static boolean DEBUG = true;
	
	/*
	 * create instance
	 */

	public static void main(String[] args) {
		setInstance(new MainCPUScheduling());
		getInstance().init();
	}
	
	public MainCPUScheduling() {
		
	}
	
	public static void setInstance(MainCPUScheduling newinstance) {
		instance = newinstance;
	}
	
	public static MainCPUScheduling getInstance() {
		return instance;
	}
	
	/*
	 * redirect
	 */
	
	private GuiMain guiMain;
	private SystemSchedule system;
	
	public GuiMain getGuiMain() {
		return guiMain;
	}

	private void setGuiMain(GuiMain guiMain) {
		this.guiMain = guiMain;
	}
	
	public SystemSchedule getSystem() {
		return system;
	}

	private void setSystem(SystemSchedule system) {
		this.system = system;
	}
	
	/*
	 * init
	 */

	private void init() {
		setup();
		getGuiMain().initGui();
	}
	
	private void setup() {
		setSystem(new SystemSchedule());
		setGuiMain(new GuiMain());
	}
	
	/*
	 * Logger
	 */
	
	public void log(String msg) {
		System.out.println(msg);
	}
	
	public void logDEBUG(String msg) {
		if(DEBUG)System.out.println(msg);
	}
	
	public void logAlg(ScheduleList alg, String msg) {
		System.out.println("[sch " + alg.getNick() + "] " + msg);
	}
	
	public void logAlg(ScheduleList alg, int count, String msg) {
		System.out.println("[sch " + alg.getNick() + "] [" + count + "] " + msg);
	}
}
