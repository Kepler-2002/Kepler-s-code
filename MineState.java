package com.sf.minesweeper.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.sf.minesweeper.Client;
import com.sf.minesweeper.bean.GlobalVariable;
import com.sf.minesweeper.bean.Request;
import com.sf.minesweeper.bean.Response;
import com.sf.minesweeper.frame.SartFrame;
import com.sf.minesweeper.tools.Tools;

import cn.hutool.core.io.FileUtil;

/**
 * 计时板，雷的剩余数量那块的面板
 */
public class MineState extends JPanel {
	
	//重新开始游戏按钮(就是笑脸按钮)
	private JLabel newGame;
	
	//重新设置游戏
	private JLabel resetGame;
	
	//存档游戏
	private JLabel saveGame;
	
	//读档游戏
	private JLabel readGame;
	
	//同步游戏
	private JLabel synGame;
	
	//计时器的三个展示板
	private JLabel usedtimeG, usedtimeS, usedtimeB;
	//雷的个数的计数板
	private JLabel totalBobmG, totalBobmS, totalBobmB;

	Box b;
	SartFrame sartFrame;
	int localPlayerNum = 0;
	
	public MineState(SartFrame sartFrame){
		
		this.sartFrame=sartFrame;
		
		this.setLayout(new BorderLayout());
		
		b = Box.createHorizontalBox();
		b.setBackground(Color.darkGray);
		Border borderOut = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border borderIn = BorderFactory.createLoweredBevelBorder();
		Border borderGroup = BorderFactory.createCompoundBorder(borderOut,
				borderIn);
		b.setBorder(borderGroup);
		
		setBackground(Color.LIGHT_GRAY);
		
		
		/*********************重置游戏*******************************/
		newGame = new JLabel();
		newGame.setIcon(Tools.iiface0);
		newGame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				newGame.setIcon(Tools.iiface1);
			}

			public void mouseReleased(MouseEvent arg0) {
				
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
					newGame.setIcon(Tools.iiface0);
					MineState.this.sartFrame.restart();
				}
			}
		});
		newGame.setIcon(Tools.iiface0);
		
		
		/*************************恢复游戏界面**********************/
		resetGame = new JLabel();
		resetGame.setIcon(Tools.resetIcon);
		resetGame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
//				resetGame.setIcon(Tools.iiface1);
//				System.out.println(111);

			}

			public void mouseReleased(MouseEvent arg0) {
//				resetGame.setIcon(Tools.iiface0);
//				 MineState.this.sartFrame.restart();
				MineState.this.sartFrame.reset();
			}
		});
//		resetGame.setIcon(Tools.iiface0);
		

		/*************************存档游戏**********************/
		saveGame = new JLabel();
		saveGame.setIcon(Tools.saveIcon);
		saveGame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
//				resetGame.setIcon(Tools.iiface1);
//				System.out.println(111);
				
			}

			public void mouseReleased(MouseEvent arg0) {
//				resetGame.setIcon(Tools.iiface0);
//				 MineState.this.sartFrame.restart();
				
				MineState.this.sartFrame.save("../data/save.txt");
				JOptionPane.showMessageDialog(null, "存档成功");
			}
		});
		
		
		/*************************读档游戏**********************/
		readGame = new JLabel();
		readGame.setIcon(Tools.readIcon);
		readGame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
//				resetGame.setIcon(Tools.iiface1);
//				System.out.println(111);
				
			}

			public void mouseReleased(MouseEvent arg0) {
//				resetGame.setIcon(Tools.iiface0);
//				 MineState.this.sartFrame.restart();
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
					MineState.this.sartFrame.read("../data/save.txt");
					System.out.println(111);
				}
			}
		});
		
		
		/*************************同步游戏**********************/
		synGame = new JLabel();
		synGame.setIcon(Tools.synIcon);
		synGame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
//				resetGame.setIcon(Tools.iiface1);
//				System.out.println();
				
			}

			public void mouseReleased(MouseEvent arg0) {
//				resetGame.setIcon(Tools.iiface0);
//				 MineState.this.sartFrame.restart();
				MineState.this.sartFrame.read("../data/round.txt");
				System.out.println("");
			}
		});
		
		
		
		totalBobmG = new JLabel();
		totalBobmS = new JLabel();
		totalBobmB = new JLabel();
		
		setTotalMineG(Tools.totalMine);
		
		usedtimeS = new JLabel();
		usedtimeS.setIcon(Tools.timeCount[0]);
		
		usedtimeG = new JLabel();
		usedtimeG.setIcon(Tools.timeCount[0]);
		
		usedtimeB = new JLabel();
		usedtimeB.setIcon(Tools.timeCount[0]);
		
		//b.add(Box.createHorizontalStrut(10));
		b.add(totalBobmB);
		b.add(totalBobmS);
		b.add(totalBobmG);
		b.add(Box.createVerticalStrut(35));
		b.add(Box.createGlue());
		
		b.add(newGame);
		b.add(Box.createVerticalStrut(35));
		b.add(Box.createGlue());
		
		
		b.add(resetGame);
		b.add(Box.createVerticalStrut(35));
		b.add(Box.createGlue());
		
		b.add(saveGame);
		b.add(Box.createVerticalStrut(35));
		b.add(Box.createGlue());
		
		b.add(readGame);
		b.add(Box.createVerticalStrut(35));
		b.add(Box.createGlue());
		
		b.add(synGame);
		b.add(Box.createVerticalStrut(35));
		b.add(Box.createGlue());
		
		b.add(usedtimeB);
		b.add(usedtimeS);
		b.add(usedtimeG);
		
		
		this.add(b,BorderLayout.CENTER);
		
	}
	
	//记录失误个数
	public void setErrorAction(int errornum) {
		int g = errornum%10;
		int s = errornum/10%10;
		int b = errornum/100;
		usedtimeG.setIcon(Tools.timeCount[g]);
		usedtimeS.setIcon(Tools.timeCount[s]);
		usedtimeB.setIcon(Tools.timeCount[b]);
	}
	
	/**
	 * 将当前分数在左边的面板上展示出来
	 */
	public void setTotalMineG(int score){
		
		int g = score%10;
		int s = score/10%10;
		int b = score/100;
		if(score<0){
			totalBobmG.setIcon(Tools.timeCount[-g]);
			totalBobmS.setIcon(Tools.timeCount[-s]);
			totalBobmB.setIcon(Tools.timeCount[10]);
			
		}else{
			totalBobmG.setIcon(Tools.timeCount[g]);
			totalBobmS.setIcon(Tools.timeCount[s]);
			totalBobmB.setIcon(Tools.timeCount[b]);
		}
	}

	public JLabel getNewGame() {
		return newGame;
	}

	public JLabel getUsedtimeG() {
		return usedtimeG;
	}

	public JLabel getUsedtimeS() {
		return usedtimeS;
	}

	public JLabel getUsedtimeB() {
		return usedtimeB;
	}

	public JLabel getTotalBobmG() {
		return totalBobmG;
	}

	public JLabel getTotalBobmS() {
		return totalBobmS;
	}

	public JLabel getTotalBobmB() {
		return totalBobmB;
	}

	public Box getB() {
		return b;
	}
	
	
}
