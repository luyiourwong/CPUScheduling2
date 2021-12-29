package com.luyiourwong.se.hw2;

import java.io.File;
import java.util.List;

import com.luyiourwong.se.hw2.gui.GuiMain;
import com.luyiourwong.se.hw2.schedules.Process;
import com.luyiourwong.se.hw2.schedules.Schedule;
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
		getInstance().showGui();
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
	
	private GuiMain getGuiMain() {
		return guiMain;
	}

	private void setGuiMain(GuiMain guiMain) {
		this.guiMain = guiMain;
	}
	
	private SystemSchedule getSystem() {
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
	}
	
	private void setup() {
		setSystem(new SystemSchedule());
		setGuiMain(new GuiMain());
	}
	
	/*
	 * method
	 */
	
	private void showGui() {
		getGuiMain().initGui();
	}
	
	public void scheduling(File file) {
		getGuiMain().clearGui();
		List<Schedule> list = getSystem().scheduling(file);
		getGuiMain().createAlgGuis(list);
	}
	
	public List<Process> getListPro(){
		return getSystem().getListPro();
	}
	
	public Process getpEND() {
		return getSystem().getpEND();
	}
}
