package com.sf.minesweeper.dialog;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sf.minesweeper.Client;
import com.sf.minesweeper.bean.GlobalVariable;
import com.sf.minesweeper.bean.Own;
import com.sf.minesweeper.bean.Request;
import com.sf.minesweeper.bean.Response;
import com.sf.minesweeper.frame.SartFrame;
import com.sf.minesweeper.tools.Tools;

import cn.hutool.core.io.FileUtil;

import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * 获胜界面
 */
public class Win extends JDialog {
	SartFrame sartFrame;
	TreeSet<Own> LOWER = new TreeSet<Own>();
	TreeSet<Own> MIDDLE = new TreeSet<Own>();
	TreeSet<Own> HEIGHT = new TreeSet<Own>();
	
	String nameid;
	
	public Win(SartFrame sartFrame,String winnerId){
		this.nameid = winnerId;
		this.sartFrame = sartFrame;
		this.setTitle("提示框");
		this.setLocationRelativeTo(null);
		this.setSize(342, 186);
		this.init();
		this.setVisible(true);
	}

	private void init() {
		// TODO Auto-generated method stub
		/**
		 * 存放记入
		 * 
		 */
		JPanel panel = new JPanel();
		JLabel label = new JLabel("\u83B7\u80DC\u73A9\u5BB6");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton butys = new JButton("保存");
		butys.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("game over");
				
				
				FileUtil.del("D:\\南科大\\大一下\\Java A\\pro\\12\\MineSweeper\\data\\round.txt");
				FileUtil.touch("D:\\南科大\\大一下\\Java A\\pro\\12\\MineSweeper\\data\\round.txt");
				//"D:\\document\\workspace\\MakeMoney_OtherWorkspace\\MineSweeper\\data\\round.txt"
				
				//系统中的用户退出系统
				Request request = new Request();
				request.setCommand(Tools.login_out);
				request.setRequestType(0+"");
				request.setUserId(GlobalVariable.userId);
				Response response = (Response) Client.operate(request);
				System.exit(0);
				
				Win.this.dispose();
				
			}
		});
		getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setText(nameid);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(label, GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
						.addComponent(butys, GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(butys, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(gl_panel);
		
	}
	public JTextField getText() {
		return null;
	}
}


