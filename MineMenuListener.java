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
 * �˵������¼�������ʵ����Ϸҳ����ת
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
		
		if(e.getActionCommand().equals("����(N)")){
			
		//	sartFrame.timerstop();
		//	sartFrame.setTime();
			Request request = new Request();
			request.setCommand(Tools.login_in);
			request.setRequestType(3+"");
			request.setUserId(GlobalVariable.userId);
			Response response= (Response) Client.operate(request);
			if (response.getLocalPlayerNum() < 2) {
				System.out.println("���Եȣ���ǰϵͳ�û�����2��");
				JOptionPane.showMessageDialog(null, "���Եȣ���ǰϵͳ�û�����2��");
				return;
			}else {
				System.out.println("��ǰϵͳ�ѹ�2�ˣ����Կ�ʼ��Ϸ");
				JOptionPane.showMessageDialog(null, "��ǰϵͳ�ѹ�2�ˣ����Կ�ʼ��Ϸ");
//				return;
//				this.sartFrame.restart();
				FileReader reader = new FileReader("../data/round.txt");
				String result = reader.readString();
				if (result.length()!=0) {
					this.sartFrame.read("../data/round.txt");
				}else {
					JOptionPane.showMessageDialog(null, "���Ѷ�ֻ����һλ���");
					this.sartFrame.restart();
				}
			}
		}
		if(e.getActionCommand().equals("����(B)")){
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
					System.out.println("���Եȣ���ǰϵͳ�û�����2��");
					JOptionPane.showMessageDialog(null, "���Եȣ���ǰϵͳ�û�����2��");
					return;
				}else {
					System.out.println("��ǰϵͳ�ѹ�2�ˣ����Կ�ʼ��Ϸ");
					JOptionPane.showMessageDialog(null, "��ǰϵͳ�ѹ�2�ˣ����Կ�ʼ��Ϸ");
					FileReader reader = new FileReader("../data/round.txt");
					String result = reader.readString();
					if (result.length()!=0) {
						this.sartFrame.read("../data/round.txt");
					}else {
						JOptionPane.showMessageDialog(null, "���Ѷ�ֻ����һλ���");
						this.sartFrame.restart();
					}
				}
				

		}
		if(e.getActionCommand().equals("�м�(I)")){

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
					System.out.println("���Եȣ���ǰϵͳ�û�����2��");
					JOptionPane.showMessageDialog(null, "���Եȣ���ǰϵͳ�û�����2��");
					return;
				}else {
					System.out.println("��ǰϵͳ�ѹ�2�ˣ����Կ�ʼ��Ϸ");
					JOptionPane.showMessageDialog(null, "��ǰϵͳ�ѹ�2�ˣ����Կ�ʼ��Ϸ");
//					return;
					FileReader reader = new FileReader("../data/round.txt");
					String result = reader.readString();
					if (result.length()!=0) {
						this.sartFrame.read("../data/round.txt");
					}else {
						JOptionPane.showMessageDialog(null, "���Ѷ�ֻ����һλ���");
						this.sartFrame.restart();
					}
				}
			}
		if(e.getActionCommand().equals("�߼�(E)")){
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
						System.out.println("���Եȣ���ǰϵͳ�û�����2��");
						JOptionPane.showMessageDialog(null, "���Եȣ���ǰϵͳ�û�����2��");
						return;
					}else {
						System.out.println("��ǰϵͳ�ѹ�2�ˣ����Կ�ʼ��Ϸ");
						JOptionPane.showMessageDialog(null, "��ǰϵͳ�ѹ�2�ˣ����Կ�ʼ��Ϸ");
//						return;
						FileReader reader = new FileReader("../data/round.txt");
						String result = reader.readString();
						if (result.length()!=0) {
							this.sartFrame.read("../data/round.txt");
						}else {
							JOptionPane.showMessageDialog(null, "���Ѷ�ֻ����һλ���");
							this.sartFrame.restart();
						}
					}
				}
		 if(e.getActionCommand().equals("�Զ���(C)")){
			 	
			 	Request request = new Request();
				request.setCommand(Tools.login_in);
				request.setRequestType(3+"");
				request.setUserId(GlobalVariable.userId);
			 	Response response= (Response) Client.operate(request);
				if (response.getLocalPlayerNum() < 2) {
				System.out.println("���Եȣ���ǰϵͳ�û�����2��");
				JOptionPane.showMessageDialog(null, "���Եȣ���ǰϵͳ�û�����2��");
				return;
			}else {
				System.out.println("��ǰϵͳ�ѹ�2�ˣ����Կ�ʼ��Ϸ");
				JOptionPane.showMessageDialog(null, "��ǰϵͳ�ѹ�2�ˣ����Կ�ʼ��Ϸ");
//				return;
				FileReader reader = new FileReader("../data/round.txt");
				String result = reader.readString();
				if (result.length()!=0) {
					this.sartFrame.read("../data/round.txt");
				}else {
					JOptionPane.showMessageDialog(null, "���Ѷ�ֻ����һλ���");
					new About(sartFrame);
				}
			}
				}
		 }
}
		