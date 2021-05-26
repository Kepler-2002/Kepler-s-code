package com.sf.minesweeper.frame;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;

import com.alibaba.fastjson.JSONObject;
import com.sf.minesweeper.Client;
import com.sf.minesweeper.bean.GlobalVariable;
import com.sf.minesweeper.bean.MineLabel;
import com.sf.minesweeper.bean.MineLabel_bak;
import com.sf.minesweeper.bean.Request;
import com.sf.minesweeper.bean.Response;
import com.sf.minesweeper.menu.MineMenu;
import com.sf.minesweeper.panel.MineField;
import com.sf.minesweeper.panel.MineState;
import com.sf.minesweeper.timer.Timers;
import com.sf.minesweeper.tools.CountMineNumUtil;
import com.sf.minesweeper.tools.Tools;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import java.applet.AudioClip;
import java.io.*;
import java.applet.Applet;
import java.awt.Frame;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javax.swing.JFrame;

public class SartFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1585043387266273492L;
	/**
	 */

	private MineState mineState; // ������
	private MineField mineField; // ����labble������
	private MineMenu mineMenu;
	private Timer timer;
	private Timers timers;
	/**
	 * ��Ϸ�Ƿ�ʼ
	 */
	private boolean isStart;
	JLabel jLabel_start = new JLabel(); // ��ʼͼƬ
	class Music extends JFrame{
		File f;
		URI uri;
		URL url;


		Music(){
			try {
				f = new File("C:\\Users\\RedCrown\\Desktop\\123.mp3");
				uri = f.toURI();
				url = uri.toURL();  //������ַ
				AudioClip aau;
				aau = Applet.newAudioClip(url);
				aau.loop();  //ѭ������
			} catch (Exception e)
			{ e.printStackTrace();
			}
		}
		public void main(String args[]) {
			new Music();
		}
	}

	public SartFrame() {
		String filepath = "123.wav";
		musicStuff musicObject = new musicStuff();
		musicObject.playMusic("C:\\Users\\RedCrown\\Desktop\\123.wav");
		//�ı�ϵͳĬ������
		Font font = new Font("Dialog", Font.PLAIN, 12);
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}
		this.setTitle("ɨ��");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setIconImage(Tools.iicon); // ����tools����������

		this.setResizable(false); // �����ô��ڲ���ɷŴ�
		
		// ..................״̬��.....................
		mineState = new MineState(this);
		this.add(mineState, BorderLayout.NORTH);

		// ...................����......................
		mineField = new MineField(this);
		this.add(mineField, BorderLayout.CENTER);

		jLabel_start.setIcon(Tools.start);
		this.add(jLabel_start, BorderLayout.CENTER);

		// ....................�˵���................
		mineMenu = new MineMenu(this);
		this.setJMenuBar(mineMenu);
		
		// .....................ʱ��................
//		Tools.time = 0;
//		timers = new Timers(mineState);
//		timer = new Timer(1000, timers);

		//ע����Ϣ
		Request request = new Request();
		request.setCommand(Tools.login_in);
		request.setRequestType(2+"");
		request.setUserId(GlobalVariable.userId);
		Response response= (Response) Client.operate(request);
		if (response.getLocalPlayerNum() < 2) {
			System.out.println("���Եȣ���ǰϵͳ�û�����2��");
			JOptionPane.showMessageDialog(null, "���Եȣ���ǰϵͳ�û�����2��");
//			return;
		}else {
			System.out.println("��ǰϵͳ�ѹ�2�ˣ����Կ�ʼ��Ϸ");
			JOptionPane.showMessageDialog(null, "��ǰϵͳ�ѹ�2�ˣ����Կ�ʼ��Ϸ");
//			return;
		}




		pack();
		this.setVisible(true);
		

		
	}

	/**
	 * ����
	 */
	public void read(String filepath) {
		//��ȡ�浵�ļ��е���Ϸ����
		FileReader reader = new FileReader(filepath);

		
		//����תΪjava����
		//���Ƚ����ݶ�ȡ����
		String result = reader.readString();
		String[] strings = result.split("\n");
		
		int rownum = Integer.parseInt(strings[0]);
		int colnum = Integer.parseInt(strings[1]);
		
		Tools.totalx = rownum;
		Tools.totaly = colnum;
		
		MineLabel[][] mineLabels = new MineLabel[rownum][colnum];
		
		int n=2;
		for(int i=0;i<rownum;i++){
			for(int j=0;j<colnum;j++){
				
				//����ת��Ϊjava����
				MineLabel_bak mineLabel_bak = JSONObject.parseObject(strings[n++],MineLabel_bak.class);
				MineLabel mineLabel = new MineLabel(mineLabel_bak.getRowIndex(), mineLabel_bak.getColIndex());
				mineLabel.setRightClickCount(mineLabel_bak.getRightClickCount());
				mineLabel.setMine(mineLabel_bak.isMine());
				mineLabel.setFlag(mineLabel_bak.isFlag());
				mineLabel.setMineAndNotflag(mineLabel_bak.isMineAndNotflag());
				mineLabel.setMineCount(mineLabel_bak.getMineCount());
				mineLabel.setExpanded(mineLabel_bak.isExpanded());
				mineLabel.setExpend(mineLabel_bak.getExpend());
				mineLabels[i][j] = mineLabel;
			}
		}
		
		//��ȡʣ������,������ǰ���������ڷ�������
		int reminderMineNum = CountMineNumUtil.countMineNum(mineLabels);
		Request request = new Request();
		request.setCommand(Tools.login_in);
		request.setRequestType(3+"");
		request.setUserId(GlobalVariable.userId);
		request.setReminder_flagNum(reminderMineNum);
		Response response = (Response) Client.operate(request);
		
		//�õ��˴浵�ļ��е��������֣���������ʼ���³�ʼ����Ϸ����
		//�Ƴ��ɵ���壬������µ����
		
		this.remove(mineState);

		this.remove(mineField);

		this.remove(jLabel_start);
		
		// ..................״̬��.....................
		mineState = new MineState(this);
		this.add(mineState, BorderLayout.NORTH);
		
		
		// ...................����......................
		//���� 1 �������ֹ��췽����û�о��庬��
		mineField = new MineField(this,mineLabels,rownum,colnum);
		this.add(mineField, BorderLayout.CENTER);

		
		// .....................ʱ��................
		Tools.time = 0;
		Timers timers = new Timers(mineState);
		timer = new Timer(1000, timers);
		
		
		pack();
		validate();//ˢ�´���
		
	}
	
	/**
	 * �浵
	 */
	public void save(String filepath) {
		//��ȡ����ǰ�������ֲ�ͼ
		MineLabel[][] mineLabel = this.mineField.getMineLabel();
		
		int rownum = mineLabel.length;
		int colnum = mineLabel[0].length;

		//����ǰ�����ֲ�ͼд�뵽�ļ���
//		FileWriter writer = new FileWriter("../data/save.txt");
		FileWriter writer = new FileWriter(filepath);
		
		//����ǰ�浵�����д����ļ�
//		writer.append(rownum+"\n");
//		writer.append(colnum+"\n");
		String content = rownum + "\n" + colnum+"\n";
		for (MineLabel[] mineLabels : mineLabel) {
			for (MineLabel mineLabels2 : mineLabels) {
				
				MineLabel_bak mineLabel_bak = new MineLabel_bak();
				mineLabel_bak.setRowIndex(mineLabels2.getRowIndex());
				mineLabel_bak.setColIndex(mineLabels2.getColIndex());
				mineLabel_bak.setRightClickCount(mineLabels2.getRightClickCount());
				mineLabel_bak.setMine(mineLabels2.isMine());
				mineLabel_bak.setFlag(mineLabels2.isFlag());
				mineLabel_bak.setMineAndNotflag(mineLabels2.isMineAndNotflag());
				mineLabel_bak.setMineCount(mineLabels2.getMineCount());
				mineLabel_bak.setExpanded(mineLabels2.isExpanded());
				mineLabel_bak.setExpend(mineLabels2.getExpend());
				
				String string = JSONObject.toJSONString(mineLabel_bak);
				
				content += string + "\n";
				writer.write(content);
			}
		}
		
	}
	
	/**
	 * �ָ���Ϸ���棨�����²���������
	 */
	public void reset() {

		
		//��ȡ����ǰ�������ֲ�ͼ
		MineLabel[][] mineLabel = this.mineField.getMineLabel();
		
		//���³�ʼ����Ϸ����
		//��ʼ����Ϸ��壬��ÿ�������ͼ���ʼ��Ϊ�տ�ʼ������
		for(int i=0;i<Tools.totalx;i++){
			for(int j=0;j<Tools.totaly;j++){
				mineLabel[i][j].setIcon(Tools.iiblank);
			}
		}
		
		//�Ƴ��ɵ���壬������µ����
		
		this.remove(mineState);

		this.remove(mineField);

		this.remove(jLabel_start);
		
		// ..................״̬��.....................
		mineState = new MineState(this);
		this.add(mineState, BorderLayout.NORTH);
		

		
		// ...................����......................
		mineField = new MineField(this,mineLabel);
		this.add(mineField, BorderLayout.CENTER);

		
		// .....................ʱ��................
		Tools.time = 0;
		Timers timers = new Timers(mineState);
		timer = new Timer(1000, timers);
		
		
		pack();
		validate();//ˢ�´���
	}
	

	// ���²���
	public void restart() {

		this.remove(mineState);

		this.remove(mineField);

		this.remove(jLabel_start);
		
		// ..................״̬��.....................
		mineState = new MineState(this);
		this.add(mineState, BorderLayout.NORTH);
		

		
		// ...................����......................
		mineField = new MineField(this);
		this.add(mineField, BorderLayout.CENTER);

		
		// .....................ʱ��................
		Tools.time = 0;
		Timers timers = new Timers(mineState);
		timer = new Timer(1000, timers);
		
		
		pack();
		validate();//ˢ�´���
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public MineState getMineState() {
		return mineState;
	}



	public MineField getMineField() {
		return mineField;
	}



	public MineMenu getMineMenu() {
		return mineMenu;
	}



	public Timer getTimer() {
		return timer;
	}



	public Timers getTimers() {
		return timers;
	}

	
	
	

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	public static void main(String[] args) {
		new SartFrame();
		
	}
	
	
	
	

	
	
}
