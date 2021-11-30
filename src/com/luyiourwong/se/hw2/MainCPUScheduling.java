package com.luyiourwong.se.hw2;

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
		getSystem().initTemplateProcess();
	}
	
	private void setup() {
		setSystem(new SystemSchedule());
		setGuiMain(new GuiMain());
	}
	
	private void showGui() {
		getGuiMain().initGui();
	}
}
