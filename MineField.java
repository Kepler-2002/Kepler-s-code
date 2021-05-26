package com.sf.minesweeper.panel;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.sf.minesweeper.bean.MineLabel;
import com.sf.minesweeper.frame.SartFrame;
import com.sf.minesweeper.listener.MouseListener;
import com.sf.minesweeper.tools.Tools;

/**
 * ��Ϸ���

 */
public class MineField extends JPanel {
	
	SartFrame sartFrame;
	MouseListener mouseListener;
	//��Ϸ���
	//�׿����ͨ������洢�ڸö�ά������
	private MineLabel mineLabel[][];
	//��ʱ���浱ǰ�����ֲ�ͼ
	private MineLabel newmineLabel[][];
	
	private int rownum;
	private int colnum;
	
	//restart��Ӧ�İ�ť
	public  MineField(SartFrame sartFrame){
		
		this.sartFrame=sartFrame;
		this.setLayout(new BorderLayout());
		
		//����������Ϸ���׿����
		mineLabel=new MineLabel[Tools.totalx][Tools.totaly];

		
		JPanel jPanel2=new JPanel();
		jPanel2.setLayout(new GridLayout(Tools.totalx,Tools.totaly));

		mouseListener=new MouseListener(sartFrame);

		//��ʼ����Ϸ���
		for(int i=0;i<Tools.totalx;i++){
			for(int j=0;j<Tools.totaly;j++){

				mineLabel[i][j]=new MineLabel(i,j);
				mineLabel[i][j].setIcon(Tools.iiblank);

				jPanel2.add(mineLabel[i][j]);
				//Ϊÿһ���׿鶼����������¼�
				mineLabel[i][j].addMouseListener(mouseListener);

			}
		}

	
		this.add(jPanel2);		
	}

	//reset��Ӧ�İ�ť
	public  MineField(SartFrame sartFrame,MineLabel[][] mineLabel1){
		
		this.sartFrame=sartFrame;
		this.setLayout(new BorderLayout());
		this.newmineLabel = mineLabel1;
		
		JPanel jPanel2=new JPanel();
		jPanel2.setLayout(new GridLayout(Tools.totalx,Tools.totaly));

		//����������Ϸ���׿����
		mineLabel=new MineLabel[Tools.totalx][Tools.totaly];
		
		//reset ʱ��ʱ��Ҫ����������������
		mouseListener=new MouseListener(sartFrame,1);

		//��ʼ����Ϸ���
		for(int i=0;i<Tools.totalx;i++){
			for(int j=0;j<Tools.totaly;j++){

				mineLabel[i][j]=new MineLabel(i,j);
				mineLabel[i][j].setIcon(Tools.iiblank);

				jPanel2.add(mineLabel[i][j]);
				//Ϊÿһ���׿鶼����������¼�
				mineLabel[i][j].addMouseListener(mouseListener);

			}
		}

	
		this.add(jPanel2);		
	}
	
	
	//read��Ӧ�İ�ť
	public  MineField(SartFrame sartFrame,MineLabel[][] mineLabel1,int rownum,int clonum){
		
		this.rownum = rownum;
		this.colnum = clonum;
		
		this.sartFrame=sartFrame;
		this.setLayout(new BorderLayout());
		this.newmineLabel = mineLabel1;
		
		JPanel jPanel2=new JPanel();
		jPanel2.setLayout(new GridLayout(rownum,clonum));

		//����������Ϸ���׿����
		mineLabel=new MineLabel[rownum][clonum];
		
		//read ʱ��Ҫ������������������Ⱦ����
		mouseListener=new MouseListener(sartFrame,2);

		//��ʼ����Ϸ���
		for(int i=0;i<rownum;i++){
			for(int j=0;j<clonum;j++){

				mineLabel[i][j]=new MineLabel(i,j);
				mineLabel[i][j].setIcon(Tools.iiblank);
				
				//��ʼ����Ϸ����
				//��չ������
				if (newmineLabel[i][j].isExpanded()) {
					if (!newmineLabel[i][j].isMine()) {
						mineLabel[i][j].setIcon(Tools.mineCount[newmineLabel[i][j].getMineCount()]);
					}
					//δչ�����飬���ܱ���עΪ����
				}else {
					if (newmineLabel[i][j].isFlag()) {
						mineLabel[i][j].setIcon(Tools.iiflag);
					}
				}
				
				
				
				jPanel2.add(mineLabel[i][j]);
				//Ϊÿһ���׿鶼����������¼�
				mineLabel[i][j].addMouseListener(mouseListener);

			}
		}

	
		this.add(jPanel2);		
	}
	
	
	
	
	/**
	 * ���׼�����Χ����
	 */
	public void buildMine(int rowx, int coly,int flag) {
		/**
		 * ����(ȥ����ǰ���λ�ã�ʹ��һ����Զ����㵽��)
		 */
		//restart��ť����Ҫ��������
		if (flag == 0) {
			//���� Tools.totalMine ����
			for (int i = 0; i < Tools.totalMine; i++) {
				
				/**
				 * ��������ף�
				 * 		1:�����������������
				 * 		2:��Ҫ��������ɵ���������жϣ�
				 * 				���ڵ�һ�ε����˵����Ҫȷ��������׿鲻��������
				 * 				ͬʱ��Ҫ��֤���ɵ�������괦��ָ���ķ�Χ��
				 */
				
				//Math.random()����ϵͳ���ѡȡ���ڵ��� 0.0 ��С�� 1.0 ��α��� double ֵ
				//�����������������
				//����Math.random���Ա�֤���겻Խ��
				int x = (int) (Math.random() * Tools.totalx);
				int y = (int) (Math.random() * Tools.totaly);
				//System.out.println(y);
				//�ж����ɵ������ǲ��ǵ�һ�ε����λ��
				//�ǣ���Ҫ��֤��һ�ε����λ�ò����ó�����
				//ͨ�����ϵ����������µ����������
				if(x==rowx && y==coly){
					i--;
				}
				//��ǰ�����Լ�������Ϊ�����ˣ���Ҫ������������
				else if(mineLabel[x][y].isMine()){
					i--;
					//����ǰ��������Ϊ����
				}else{
					mineLabel[x][y].setMine(true);
				}
			}
			//reset��ť������Ҫ��������,��isMine ����Ϊtrue��������������Ȼ��Ҫ���ֳ�ʼ��
		}else if(flag == 1){
			for (int i = 0; i < Tools.totalx; i++) {
				for (int j = 0; j < Tools.totaly; j++) {
					mineLabel[i][j].setMine(newmineLabel[i][j].isMine()) ;					
				}
			}
			//����
		}else if (flag == 2) {
			for (int i = 0; i < rownum; i++) {
				for (int j = 0; j < colnum; j++) {
					mineLabel[i][j].setMine(newmineLabel[i][j].isMine()) ;
					mineLabel[i][j].setExpanded(newmineLabel[i][j].isExpanded());
					mineLabel[i][j].setFlag(newmineLabel[i][j].isFlag());
					mineLabel[i][j].setMineAndNotflag(newmineLabel[i][j].isMineAndNotflag());
					mineLabel[i][j].setMineCount(newmineLabel[i][j].getMineCount());
					mineLabel[i][j].setRowIndex(newmineLabel[i][j].getRowIndex());
					mineLabel[i][j].setColIndex(newmineLabel[i][j].getColIndex());
					mineLabel[i][j].setExpend(newmineLabel[i][j].getExpend());
					mineLabel[i][j].setRightClickCount(newmineLabel[i][j].getRightClickCount());
				}
			}
		}
	
		/**
		 * ����Χ����
		 */
		for (int i = 0; i < Tools.totalx; i++) {
			for (int j = 0; j < Tools.totaly; j++) {
				 int count = 0;
				if (!mineLabel[i][j].isMine()) {
	              
					// �����׿���Χ�˸���������:����1
					
					
//					/**
//					 * ��
//					 */
//  				if (i > 0) {
//						if (mineLabel[i - 1][j].isMine()) {
//							count++;
//						}
//					}
//
//					/**
//					 * ����
//					 */
//					if (i > 0 && j>0) {
//						if (mineLabel[i - 1][j-1].isMine()) {
//							count++;
//						}
//					}
//					
//					/**
//					 * ����
//					 */
//  				if (i > 0&&j+1< Tools.totaly) {
//						if (mineLabel[i - 1][j+1].isMine()) {
//							count++;
//						}
//					}
//
//					/**
//					 * ��
//					 */
//					if (j>0) {
//						if (mineLabel[i][j-1].isMine()) {
//							count++;
//						}
//					}
//					
//					/**
//					 * ��
//					 */
//  				if (j+1< Tools.totaly) {
//						if (mineLabel[i][j+1].isMine()) {
//							count++;
//						}
//					}
//
//					/**
//					 * ����
//					 */
//					if (i+1< Tools.totalx&&j>0) {
//						if (mineLabel[i + 1][j-1].isMine()) {
//							count++;
//						}
//					}
//					
//					/**
//					 * ��
//					 */
//  				if (i+1< Tools.totalx) {
//						if (mineLabel[i + 1][j].isMine()) {
//							count++;
//						}
//					}
//
//					/**
//					 * ����
//					 */
//					if (i+1< Tools.totalx && j+1< Tools.totaly) {
//						if (mineLabel[i + 1][j+1].isMine()) {
//							count++;
//						}
//					}
//					
					
					
					for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1,
							Tools.totalx - 1); x++) {
						for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1,
								Tools.totaly - 1); y++) {
							if (mineLabel[x][y].isMine())
								count++;
						}
					}
					//��Χ���׿���
					mineLabel[i][j].setMineCount(count);

					
					
				}
			}
		}
		
		
	}




	public MineLabel[][] getMineLabel() {
		return mineLabel;
	}




	public void setMineLabel(MineLabel[][] mineLabel) {
		this.mineLabel = mineLabel;
	}




	public MouseListener getMouseListener() {
		return mouseListener;
	}
	
	

}
