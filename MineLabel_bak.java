package com.sf.minesweeper.bean;

import java.io.Serializable;

import javax.swing.JLabel;

	/**
	 * 代表一个个的小雷块
	 */
public class MineLabel_bak implements Serializable{

	/**
	 * 判断是否是雷
	 * 
	 * */
	private boolean isMine;
	/**
	 * 判断雷块是否展开
	 * 
	 * */
	private boolean isExpanded;
	/**
	 * 判断雷块是否是旗子
	 * 
	 * */
	private boolean isFlag;
	/**
	 * 判断是否是雷且未标上旗子
	 * */
	private boolean isMineAndNotflag;
	/**
	 * count 计算雷块周围的雷数
	 * 
	 * */
	private int mineCount;
	/**
	 * 雷块所在的行
	 * 
	 * */
	private int rowIndex;
	/**
	 * 雷块所在的列
	 * 
	 * */
	private int colIndex;
	
	
	private int expend=0;
	
	
	/**
	 * rightClickCount 右键点击次数
	 * 
	 * */
	private int rightClickCount;


	public boolean isMine() {
		return isMine;
	}


	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}


	public boolean isExpanded() {
		return isExpanded;
	}


	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}


	public boolean isFlag() {
		return isFlag;
	}


	public void setFlag(boolean isFlag) {
		this.isFlag = isFlag;
	}


	public boolean isMineAndNotflag() {
		return isMineAndNotflag;
	}


	public void setMineAndNotflag(boolean isMineAndNotflag) {
		this.isMineAndNotflag = isMineAndNotflag;
	}


	public int getMineCount() {
		return mineCount;
	}


	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}


	public int getRowIndex() {
		return rowIndex;
	}


	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}


	public int getColIndex() {
		return colIndex;
	}


	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}


	public int getExpend() {
		return expend;
	}


	public void setExpend(int expend) {
		this.expend = expend;
	}


	public int getRightClickCount() {
		return rightClickCount;
	}


	public void setRightClickCount(int rightClickCount) {
		this.rightClickCount = rightClickCount;
	}


	@Override
	public String toString() {
		return "MineLabel_bak [isMine=" + isMine + ", isExpanded=" + isExpanded + ", isFlag=" + isFlag
				+ ", isMineAndNotflag=" + isMineAndNotflag + ", mineCount=" + mineCount + ", rowIndex=" + rowIndex
				+ ", colIndex=" + colIndex + ", expend=" + expend + ", rightClickCount=" + rightClickCount + "]";
	}
	
	

}



