package com.sf.minesweeper.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sf.minesweeper.Client;
import com.sf.minesweeper.bean.GlobalVariable;
import com.sf.minesweeper.bean.Request;
import com.sf.minesweeper.bean.Response;
import com.sf.minesweeper.dialog.About;
import com.sf.minesweeper.dialog.ShowWin;
import com.sf.minesweeper.frame.SartFrame;
import com.sf.minesweeper.tools.Tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;

/**
 * 菜单监听事件，用于实现游戏页面跳转
 */
public class MineMenuListener implements ActionListener {
	JMenuItem jMenuItem;
	JOptionPane jo1= new JOptionPane();
//	JOptionPane jo2=new JOptionPane();
	JTextField jTextField=new JTextField();
	SartFrame sartFrame;
	
	public MineMenuListener(SartFrame sartFrame){
		this.sartFrame=sartFrame;
	}
	
	
public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("开局(N)")){
			
		//	sartFrame.timerstop();
		//	sartFrame.setTime();
			Request request = new Request();
			request.setCommand(Tools.login_in);
			request.setRequestType(3+"");
			request.setUserId(GlobalVariable.userId);
			Response response= (Response) Client.operate(request);
			if (response.getLocalPlayerNum() < 2) {
				System.out.println("请稍等，当前系统用户不足2人");
				JOptionPane.showMessageDialog(null, "请稍等，当前系统用户不足2人");
				return;
			}else {
				System.out.println("当前系统已够2人，可以开始游戏");
				JOptionPane.showMessageDialog(null, "当前系统已够2人，可以开始游戏");
//				return;
//				this.sartFrame.restart();
				FileReader reader = new FileReader("../data/round.txt");
				String result = reader.readString();
				if (result.length()!=0) {
					this.sartFrame.read("../data/round.txt");
				}else {
					JOptionPane.showMessageDialog(null, "改难度只有你一位玩家");
					this.sartFrame.restart();
				}
			}
		}
		if(e.getActionCommand().equals("初级(B)")){
				Tools.totalx = 9;
				Tools.totaly = 9;
				Tools.totalMine = 10;
				Tools.currentLevel = Tools.LOWER_LEVEL;
				
				Request request = new Request();
				request.setCommand(Tools.login_in);
				request.setRequestType(3+"");
				request.setUserId(GlobalVariable.userId);
				Response response= (Response) Client.operate(request);
				if (response.getLocalPlayerNum() < 2) {
					System.out.println("请稍等，当前系统用户不足2人");
					JOptionPane.showMessageDialog(null, "请稍等，当前系统用户不足2人");
					return;
				}else {
					System.out.println("当前系统已够2人，可以开始游戏");
					JOptionPane.showMessageDialog(null, "当前系统已够2人，可以开始游戏");
					FileReader reader = new FileReader("../data/round.txt");
					String result = reader.readString();
					if (result.length()!=0) {
						this.sartFrame.read("../data/round.txt");
					}else {
						JOptionPane.showMessageDialog(null, "改难度只有你一位玩家");
						this.sartFrame.restart();
					}
				}
				

		}
		if(e.getActionCommand().equals("中级(I)")){

				Tools.totalx = 16;
				Tools.totaly = 16;
				Tools.totalMine = 40;
				Tools.currentLevel = Tools.MIDDLE_LEVEL;
				
				Request request = new Request();
				request.setCommand(Tools.login_in);
				request.setRequestType(3+"");
				request.setUserId(GlobalVariable.userId);
				Response response= (Response) Client.operate(request);
				if (response.getLocalPlayerNum() < 2) {
					System.out.println("请稍等，当前系统用户不足2人");
					JOptionPane.showMessageDialog(null, "请稍等，当前系统用户不足2人");
					return;
				}else {
					System.out.println("当前系统已够2人，可以开始游戏");
					JOptionPane.showMessageDialog(null, "当前系统已够2人，可以开始游戏");
//					return;
					FileReader reader = new FileReader("../data/round.txt");
					String result = reader.readString();
					if (result.length()!=0) {
						this.sartFrame.read("../data/round.txt");
					}else {
						JOptionPane.showMessageDialog(null, "改难度只有你一位玩家");
						this.sartFrame.restart();
					}
				}
			}
		if(e.getActionCommand().equals("高级(E)")){
					Tools.totalx = 16;
					Tools.totaly = 30;
					Tools.totalMine = 99;
					Tools.currentLevel = Tools.HEIGHT_LEVEL;
					
					Request request = new Request();
					request.setCommand(Tools.login_in);
					request.setRequestType(3+"");
					request.setUserId(GlobalVariable.userId);
					Response response= (Response) Client.operate(request);
					if (response.getLocalPlayerNum() < 2) {
						System.out.println("请稍等，当前系统用户不足2人");
						JOptionPane.showMessageDialog(null, "请稍等，当前系统用户不足2人");
						return;
					}else {
						System.out.println("当前系统已够2人，可以开始游戏");
						JOptionPane.showMessageDialog(null, "当前系统已够2人，可以开始游戏");
//						return;
						FileReader reader = new FileReader("../data/round.txt");
						String result = reader.readString();
						if (result.length()!=0) {
							this.sartFrame.read("../data/round.txt");
						}else {
							JOptionPane.showMessageDialog(null, "改难度只有你一位玩家");
							this.sartFrame.restart();
						}
					}
				}
		 if(e.getActionCommand().equals("自定义(C)")){
			 	
			 	Request request = new Request();
				request.setCommand(Tools.login_in);
				request.setRequestType(3+"");
				request.setUserId(GlobalVariable.userId);
			 	Response response= (Response) Client.operate(request);
				if (response.getLocalPlayerNum() < 2) {
				System.out.println("请稍等，当前系统用户不足2人");
				JOptionPane.showMessageDialog(null, "请稍等，当前系统用户不足2人");
				return;
			}else {
				System.out.println("当前系统已够2人，可以开始游戏");
				JOptionPane.showMessageDialog(null, "当前系统已够2人，可以开始游戏");
//				return;
				FileReader reader = new FileReader("../data/round.txt");
				String result = reader.readString();
				if (result.length()!=0) {
					this.sartFrame.read("../data/round.txt");
				}else {
					JOptionPane.showMessageDialog(null, "改难度只有你一位玩家");
					new About(sartFrame);
				}
			}
				}
		 }
}
		