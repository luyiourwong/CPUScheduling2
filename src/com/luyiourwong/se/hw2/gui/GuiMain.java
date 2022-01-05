package com.luyiourwong.se.hw2.gui;

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
import com.luyiourwong.se.hw2.MainCPUScheduling;
import com.luyiourwong.se.hw2.schedules.Process;
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
					MainCPUScheduling.getInstance().log("[select file] " + selectedFile.getName());
					clearGui();
					List<Schedule> list = MainCPUScheduling.getInstance().getSystem().scheduling(selectedFile);
					createAlgGuis(list);
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
		
		JScrollPane sp = new JScrollPane(jp_algs);
        containerMain.add(sp, BorderLayout.CENTER);
	}
	
	private int color = 0;
	
	private int getMapX(Map<Integer, Process> map) {
		int x = 100;
		int lasti = -1;
		for(Integer i : map.keySet()) {
			if(lasti != -1) {
				int length = (i - lasti);
				x += (length * picmult);
			}
			lasti = i;
		}
		
		return x;
	}
	
	private JPanel createAlgGui(Schedule sch) {
		JPanel jp = new JPanel();
		int jpX = getMapX(sch.getMapSch());
		int jpY = (MainCPUScheduling.getInstance().getSystem().getListPro().size() * 20) + 75;
		jp.setPreferredSize(new Dimension(jpX, jpY));
		jp.setLayout(new BorderLayout());
		if (color == 0) {
			color = 1;
			jp.setBackground(Color.GREEN);
		} else {
			color = 0;
			jp.setBackground(Color.YELLOW);
		}
		
		jp.add(addJLabel(sch.getAlg().getFullName(), 15, 20), BorderLayout.NORTH);
		
		JPanel jpg = createGuiPic(sch.getAlg().getNick(), sch.getMapSch(), jpX, jpY);
		jp.add(jpg, BorderLayout.CENTER);
		
		JTable jta = createGuiTable(sch.getAlg().getNick());
		jp.add(jta, BorderLayout.SOUTH);
		
		return jp;
	}

	private JPanel createGuiPic(String name, Map<Integer, Process> map, int X, int Y) {
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(X, Y));
		jp.setLayout(null);
		
		int locX = 10;
		int locY = 0;
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
		
		return jp;
	}
	
	private JTable createGuiTable(String name) {
		String[] columns = {"Process", "priority", "burst", "arrival", "Turnaround", "Waiting"};
		Object[][] list = new Object[MainCPUScheduling.getInstance().getSystem().getListPro().size() + 1][6];
		
		int count = 0;
		list[count][0] = columns[0];
		list[count][1] = columns[1];
		list[count][2] = columns[2];
		list[count][3] = columns[3];
		list[count][4] = columns[4];
		list[count][5] = columns[5];
		count++;
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
		
		/*JScrollPane scrollPane = new JScrollPane(jt);  
		jt.setFillsViewportHeight(true);
		
		int y = ((count) * 17) + 20;
		scrollPane.setBounds(0, locY, layoutX, y);
		locY += y;
		jp.add(scrollPane);*/
		
		return jt;
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
