package com.luyiourwong.se.hw2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import com.luyiourwong.se.hw2.schedules.Schedule;

public class GuiMain extends JFrame{

	private static final long serialVersionUID = 1L;
	private static String frameName = "CPU排程工具 M1007888呂宥融 M0920574林威成";
	
	/*
	 * create gui
	 */
	
	private int layoutX, layoutY;
	private Container containerMain;
	
	private int picmult = 10;
	
	private static final int locYdefault = 20;

	public void initGui() {
		/*
		 * main frame
		 */
		layoutX = 1600;
		layoutY = 800;
		setSize(layoutX, layoutY);
		setTitle(frameName);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		BorderLayout layoutMain = new BorderLayout();
		containerMain = getContentPane();
		containerMain.setLayout(layoutMain);
		
		/*
		 * Basic
		 */
		addChooseFileBtn();
		
		/*
		 * finish
		 */
		
		setVisible(true);
	}
	
	public void clearGui() {
		containerMain.removeAll();
		addChooseFileBtn();
		containerMain.revalidate();
		containerMain.repaint();
	}

	private void addChooseFileBtn() {
		JButton btn_cf = new JButton("選擇檔案");
		btn_cf.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a){
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION){
					File selectedFile = fileChooser.getSelectedFile();
					Logger.log("[select file] " + selectedFile.getName());
					MainCPUScheduling.getInstance().getSystem().scheduling(selectedFile);
				}
			}
		});
		btn_cf.setBounds(0, 0, 75, 25);
		containerMain.add(btn_cf, BorderLayout.NORTH);
	}
	
	public void createAlgGuis(List<Schedule> listSch) {
		JPanel jp_algs = new JPanel();
		BoxLayout layoutNotes = new BoxLayout(jp_algs, BoxLayout.PAGE_AXIS);
		jp_algs.setLayout(layoutNotes);
		
		for(Schedule sch : listSch) {
			JPanel p = createAlgGui(sch);
			jp_algs.add(p);
		}
		
		JScrollPane sp = new JScrollPane(jp_algs, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        containerMain.add(sp, BorderLayout.CENTER);
	}
	
	private int color = 0;
	
	private JPanel createAlgGui(Schedule sch) {
		int locY = locYdefault;
		
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(1000, 300));
		jp.setLayout(null);
		if (color == 0) {
			color = 1;
			jp.setBackground(Color.GREEN);
		} else {
			color = 0;
			jp.setBackground(Color.YELLOW);
		}
		
		jp.add(addJLabel(sch.getAlg().getFullName(), 15, locY));
		locY += 30;
		
		createGuiPic(jp, locY, sch.getAlg().getNick(), sch.getMapSch());
		locY += 70;
		
		createGuiTable(jp, locY, sch.getAlg().getNick());
		
		return jp;
	}

	private void createGuiPic(JPanel jp, int locY, String name, Map<Integer, Process> map) {
		int locX = 10;
		jp.add(addJLabel(name, locX - 5, locY + 25));
		locX += 30;
		
		String lastn = "";
		int lasti = -1;
		for(Integer i : map.keySet()) {
			Process p = map.get(i);
			if(lasti != -1) {
				jp.add(addJLabel(String.valueOf(lasti), locX, locY));
				int length = (i - lasti);
				jp.add(addJButton(lastn, locX, locY + 25, length));
				locX += (length * picmult);
			}
			lastn = p.getName();
			lasti = i;
			if(p == MainCPUScheduling.getInstance().getSystem().getpEND()) {
				jp.add(addJLabel(String.valueOf(i), locX - 5, locY));
			}
		}
	}
	
	private void createGuiTable(JPanel jp, int locY, String name) {
		String[] columns = {"Process", "priority", "burst", "arrival", "Turnaround", "Waiting"};
		Object[][] list = new Object[MainCPUScheduling.getInstance().getSystem().getListPro().size()][6];
		int count = 0;
		for(Process p : MainCPUScheduling.getInstance().getSystem().getListPro()) {
			list[count][0] = p.getName();
			list[count][1] = p.getPriority();
			list[count][2] = p.getBurst();
			list[count][3] = p.getArrival();
			list[count][4] = p.getValue(Process.TURN, name);
			list[count][5] = p.getValue(Process.WAIT, name);
			count++;
		}
		JTable jt = new JTable(list, columns);
		
		JScrollPane scrollPane = new JScrollPane(jt);  
		jt.setFillsViewportHeight(true);
		
		int y = ((count) * 17) + 20;
		scrollPane.setBounds(0, locY, layoutX, y);
		locY += y;
		jp.add(scrollPane);
	}
	
	private JLabel addJLabel(String title, int x, int y) {
		int length = title.length() * 8;
		
		JLabel j = new JLabel(title);
		j.setBounds(x, y, length, 25);
		
		return j;
	}
	
	private JButton addJButton(String title, int x, int y, int length) {
		JButton j = new JButton(title);
		j.setBounds(x, y, (length * picmult), 25);
		
		return j;
	}
}
