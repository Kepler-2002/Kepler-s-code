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
 * 游戏面板

 */
public class MineField extends JPanel {
	
	SartFrame sartFrame;
	MouseListener mouseListener;
	//游戏面板
	//雷块和普通方块均存储在该二维数组中
	private MineLabel mineLabel[][];
	//临时保存当前雷区分布图
	private MineLabel newmineLabel[][];
	
	private int rownum;
	private int colnum;
	
	//restart对应的按钮
	public  MineField(SartFrame sartFrame){
		
		this.sartFrame=sartFrame;
		this.setLayout(new BorderLayout());
		
		//设置整个游戏的雷块面板
		mineLabel=new MineLabel[Tools.totalx][Tools.totaly];

		
		JPanel jPanel2=new JPanel();
		jPanel2.setLayout(new GridLayout(Tools.totalx,Tools.totaly));

		mouseListener=new MouseListener(sartFrame);

		//初始化游戏面板
		for(int i=0;i<Tools.totalx;i++){
			for(int j=0;j<Tools.totaly;j++){

				mineLabel[i][j]=new MineLabel(i,j);
				mineLabel[i][j].setIcon(Tools.iiblank);

				jPanel2.add(mineLabel[i][j]);
				//为每一个雷块都添加鼠标监听事件
				mineLabel[i][j].addMouseListener(mouseListener);

			}
		}

	
		this.add(jPanel2);		
	}

	//reset对应的按钮
	public  MineField(SartFrame sartFrame,MineLabel[][] mineLabel1){
		
		this.sartFrame=sartFrame;
		this.setLayout(new BorderLayout());
		this.newmineLabel = mineLabel1;
		
		JPanel jPanel2=new JPanel();
		jPanel2.setLayout(new GridLayout(Tools.totalx,Tools.totaly));

		//设置整个游戏的雷块面板
		mineLabel=new MineLabel[Tools.totalx][Tools.totaly];
		
		//reset 时不时需要重置雷区，构造器
		mouseListener=new MouseListener(sartFrame,1);

		//初始化游戏面板
		for(int i=0;i<Tools.totalx;i++){
			for(int j=0;j<Tools.totaly;j++){

				mineLabel[i][j]=new MineLabel(i,j);
				mineLabel[i][j].setIcon(Tools.iiblank);

				jPanel2.add(mineLabel[i][j]);
				//为每一个雷块都添加鼠标监听事件
				mineLabel[i][j].addMouseListener(mouseListener);

			}
		}

	
		this.add(jPanel2);		
	}
	
	
	//read对应的按钮
	public  MineField(SartFrame sartFrame,MineLabel[][] mineLabel1,int rownum,int clonum){
		
		this.rownum = rownum;
		this.colnum = clonum;
		
		this.sartFrame=sartFrame;
		this.setLayout(new BorderLayout());
		this.newmineLabel = mineLabel1;
		
		JPanel jPanel2=new JPanel();
		jPanel2.setLayout(new GridLayout(rownum,clonum));

		//设置整个游戏的雷块面板
		mineLabel=new MineLabel[rownum][clonum];
		
		//read 时需要根据已有数据重新渲染数据
		mouseListener=new MouseListener(sartFrame,2);

		//初始化游戏面板
		for(int i=0;i<rownum;i++){
			for(int j=0;j<clonum;j++){

				mineLabel[i][j]=new MineLabel(i,j);
				mineLabel[i][j].setIcon(Tools.iiblank);
				
				//初始化游戏界面
				//已展开界面
				if (newmineLabel[i][j].isExpanded()) {
					if (!newmineLabel[i][j].isMine()) {
						mineLabel[i][j].setIcon(Tools.mineCount[newmineLabel[i][j].getMineCount()]);
					}
					//未展开方块，可能被标注为红旗
				}else {
					if (newmineLabel[i][j].isFlag()) {
						mineLabel[i][j].setIcon(Tools.iiflag);
					}
				}
				
				
				
				jPanel2.add(mineLabel[i][j]);
				//为每一个雷块都添加鼠标监听事件
				mineLabel[i][j].addMouseListener(mouseListener);

			}
		}

	
		this.add(jPanel2);		
	}
	
	
	
	
	/**
	 * 布雷及算周围雷数
	 */
	public void buildMine(int rowx, int coly,int flag) {
		/**
		 * 布雷(去除当前点击位置，使第一次永远不会点到雷)
		 */
		//restart按钮，需要重置雷区
		if (flag == 0) {
			//生成 Tools.totalMine 个雷
			for (int i = 0; i < Tools.totalMine; i++) {
				
				/**
				 * 随机生成雷：
				 * 		1:生成雷区的随机坐标
				 * 		2:需要对随机生成的坐标进行判断：
				 * 				对于第一次点击来说，需要确保点击的雷块不是雷区；
				 * 				同时需要保证生成的随机坐标处于指定的范围内
				 */
				
				//Math.random()是令系统随机选取大于等于 0.0 且小于 1.0 的伪随机 double 值
				//生成雷区的随机坐标
				//借助Math.random可以保证坐标不越界
				int x = (int) (Math.random() * Tools.totalx);
				int y = (int) (Math.random() * Tools.totaly);
				//System.out.println(y);
				//判断生成的坐标是不是第一次点击的位置
				//是，需要保证第一次点击的位置不设置成雷区
				//通过不断的重新生成新的坐标来解决
				if(x==rowx && y==coly){
					i--;
				}
				//当前坐标以及被设置为雷区了，需要重新生成坐标
				else if(mineLabel[x][y].isMine()){
					i--;
					//将当前坐标设置为雷区
				}else{
					mineLabel[x][y].setMine(true);
				}
			}
			//reset按钮，不需要重置雷区,即isMine 设置为true，但其他属性仍然需要保持初始化
		}else if(flag == 1){
			for (int i = 0; i < Tools.totalx; i++) {
				for (int j = 0; j < Tools.totaly; j++) {
					mineLabel[i][j].setMine(newmineLabel[i][j].isMine()) ;					
				}
			}
			//读档
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
		 * 算周围雷数
		 */
		for (int i = 0; i < Tools.totalx; i++) {
			for (int j = 0; j < Tools.totaly; j++) {
				 int count = 0;
				if (!mineLabel[i][j].isMine()) {
	              
					// 计算雷块周围八个方向雷数:方法1
					
					
//					/**
//					 * 上
//					 */
//  				if (i > 0) {
//						if (mineLabel[i - 1][j].isMine()) {
//							count++;
//						}
//					}
//
//					/**
//					 * 左上
//					 */
//					if (i > 0 && j>0) {
//						if (mineLabel[i - 1][j-1].isMine()) {
//							count++;
//						}
//					}
//					
//					/**
//					 * 右上
//					 */
//  				if (i > 0&&j+1< Tools.totaly) {
//						if (mineLabel[i - 1][j+1].isMine()) {
//							count++;
//						}
//					}
//
//					/**
//					 * 左
//					 */
//					if (j>0) {
//						if (mineLabel[i][j-1].isMine()) {
//							count++;
//						}
//					}
//					
//					/**
//					 * 右
//					 */
//  				if (j+1< Tools.totaly) {
//						if (mineLabel[i][j+1].isMine()) {
//							count++;
//						}
//					}
//
//					/**
//					 * 左下
//					 */
//					if (i+1< Tools.totalx&&j>0) {
//						if (mineLabel[i + 1][j-1].isMine()) {
//							count++;
//						}
//					}
//					
//					/**
//					 * 下
//					 */
//  				if (i+1< Tools.totalx) {
//						if (mineLabel[i + 1][j].isMine()) {
//							count++;
//						}
//					}
//
//					/**
//					 * 右下
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
					//周围的雷块数
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
