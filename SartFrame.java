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

	private MineState mineState; // 记雷数
	private MineField mineField; // 布置labble和雷数
	private MineMenu mineMenu;
	private Timer timer;
	private Timers timers;
	/**
	 * 游戏是否开始
	 */
	private boolean isStart;
	JLabel jLabel_start = new JLabel(); // 开始图片
	class Music extends JFrame{
		File f;
		URI uri;
		URL url;


		Music(){
			try {
				f = new File("C:\\Users\\RedCrown\\Desktop\\123.mp3");
				uri = f.toURI();
				url = uri.toURL();  //解析地址
				AudioClip aau;
				aau = Applet.newAudioClip(url);
				aau.loop();  //循环播放
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
		//改变系统默认字体
		Font font = new Font("Dialog", Font.PLAIN, 12);
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}
		this.setTitle("扫雷");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setIconImage(Tools.iicon); // 利用tools来做的作法

		this.setResizable(false); // 这样让窗口不会可放大
		
		// ..................状态栏.....................
		mineState = new MineState(this);
		this.add(mineState, BorderLayout.NORTH);

		// ...................雷区......................
		mineField = new MineField(this);
		this.add(mineField, BorderLayout.CENTER);

		jLabel_start.setIcon(Tools.start);
		this.add(jLabel_start, BorderLayout.CENTER);

		// ....................菜单栏................
		mineMenu = new MineMenu(this);
		this.setJMenuBar(mineMenu);
		
		// .....................时间................
//		Tools.time = 0;
//		timers = new Timers(mineState);
//		timer = new Timer(1000, timers);

		//注册信息
		Request request = new Request();
		request.setCommand(Tools.login_in);
		request.setRequestType(2+"");
		request.setUserId(GlobalVariable.userId);
		Response response= (Response) Client.operate(request);
		if (response.getLocalPlayerNum() < 2) {
			System.out.println("请稍等，当前系统用户不足2人");
			JOptionPane.showMessageDialog(null, "请稍等，当前系统用户不足2人");
//			return;
		}else {
			System.out.println("当前系统已够2人，可以开始游戏");
			JOptionPane.showMessageDialog(null, "当前系统已够2人，可以开始游戏");
//			return;
		}




		pack();
		this.setVisible(true);
		

		
	}

	/**
	 * 读档
	 */
	public void read(String filepath) {
		//读取存档文件中的游戏进度
		FileReader reader = new FileReader(filepath);

		
		//重新转为java对象
		//首先将内容读取出来
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
				
				//重新转换为java对象
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
		
		//获取剩余雷数,并将当前雷数保存在服务器上
		int reminderMineNum = CountMineNumUtil.countMineNum(mineLabels);
		Request request = new Request();
		request.setCommand(Tools.login_in);
		request.setRequestType(3+"");
		request.setUserId(GlobalVariable.userId);
		request.setReminder_flagNum(reminderMineNum);
		Response response = (Response) Client.operate(request);
		
		//拿到了存档文件中的雷区布局，接下来开始重新初始化游戏界面
		//移除旧的面板，并添加新的面板
		
		this.remove(mineState);

		this.remove(mineField);

		this.remove(jLabel_start);
		
		// ..................状态栏.....................
		mineState = new MineState(this);
		this.add(mineState, BorderLayout.NORTH);
		
		
		// ...................雷区......................
		//参数 1 用于区分构造方法，没有具体含义
		mineField = new MineField(this,mineLabels,rownum,colnum);
		this.add(mineField, BorderLayout.CENTER);

		
		// .....................时间................
		Tools.time = 0;
		Timers timers = new Timers(mineState);
		timer = new Timer(1000, timers);
		
		
		pack();
		validate();//刷新窗体
		
	}
	
	/**
	 * 存档
	 */
	public void save(String filepath) {
		//获取到当前的雷区分布图
		MineLabel[][] mineLabel = this.mineField.getMineLabel();
		
		int rownum = mineLabel.length;
		int colnum = mineLabel[0].length;

		//将当前雷区分布图写入到文件中
//		FileWriter writer = new FileWriter("../data/save.txt");
		FileWriter writer = new FileWriter(filepath);
		
		//将当前存档的行列存入文件
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
	 * 恢复游戏界面（不重新布置雷区）
	 */
	public void reset() {

		
		//获取到当前的雷区分布图
		MineLabel[][] mineLabel = this.mineField.getMineLabel();
		
		//重新初始化游戏界面
		//初始化游戏面板，将每个方格的图标初始化为刚开始的样子
		for(int i=0;i<Tools.totalx;i++){
			for(int j=0;j<Tools.totaly;j++){
				mineLabel[i][j].setIcon(Tools.iiblank);
			}
		}
		
		//移除旧的面板，并添加新的面板
		
		this.remove(mineState);

		this.remove(mineField);

		this.remove(jLabel_start);
		
		// ..................状态栏.....................
		mineState = new MineState(this);
		this.add(mineState, BorderLayout.NORTH);
		

		
		// ...................雷区......................
		mineField = new MineField(this,mineLabel);
		this.add(mineField, BorderLayout.CENTER);

		
		// .....................时间................
		Tools.time = 0;
		Timers timers = new Timers(mineState);
		timer = new Timer(1000, timers);
		
		
		pack();
		validate();//刷新窗体
	}
	

	// 重新布局
	public void restart() {

		this.remove(mineState);

		this.remove(mineField);

		this.remove(jLabel_start);
		
		// ..................状态栏.....................
		mineState = new MineState(this);
		this.add(mineState, BorderLayout.NORTH);
		

		
		// ...................雷区......................
		mineField = new MineField(this);
		this.add(mineField, BorderLayout.CENTER);

		
		// .....................时间................
		Tools.time = 0;
		Timers timers = new Timers(mineState);
		timer = new Timer(1000, timers);
		
		
		pack();
		validate();//刷新窗体
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
