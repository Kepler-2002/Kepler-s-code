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
 * ��ʱ�壬�׵�ʣ�������ǿ�����
 */
public class MineState extends JPanel {
	
	//���¿�ʼ��Ϸ��ť(����Ц����ť)
	private JLabel newGame;
	
	//����������Ϸ
	private JLabel resetGame;
	
	//�浵��Ϸ
	private JLabel saveGame;
	
	//������Ϸ
	private JLabel readGame;
	
	//ͬ����Ϸ
	private JLabel synGame;
	
	//��ʱ��������չʾ��
	private JLabel usedtimeG, usedtimeS, usedtimeB;
	//�׵ĸ����ļ�����
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
		
		
		/*********************������Ϸ*******************************/
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
					System.out.println("���Եȣ���ǰϵͳ�û�����2��");
					JOptionPane.showMessageDialog(null, "���Եȣ���ǰϵͳ�û�����2��");
					return;
				}else {
					newGame.setIcon(Tools.iiface0);
					MineState.this.sartFrame.restart();
				}
			}
		});
		newGame.setIcon(Tools.iiface0);
		
		
		/*************************�ָ���Ϸ����**********************/
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
		

		/*************************�浵��Ϸ**********************/
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
				JOptionPane.showMessageDialog(null, "�浵�ɹ�");
			}
		});
		
		
		/*************************������Ϸ**********************/
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
					System.out.println("���Եȣ���ǰϵͳ�û�����2��");
					JOptionPane.showMessageDialog(null, "���Եȣ���ǰϵͳ�û�����2��");
					return;
				}else {
					MineState.this.sartFrame.read("../data/save.txt");
					System.out.println(111);
				}
			}
		});
		
		
		/*************************ͬ����Ϸ**********************/
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
	
	//��¼ʧ�����
	public void setErrorAction(int errornum) {
		int g = errornum%10;
		int s = errornum/10%10;
		int b = errornum/100;
		usedtimeG.setIcon(Tools.timeCount[g]);
		usedtimeS.setIcon(Tools.timeCount[s]);
		usedtimeB.setIcon(Tools.timeCount[b]);
	}
	
	/**
	 * ����ǰ��������ߵ������չʾ����
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
