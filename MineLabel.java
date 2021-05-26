package com.sf.minesweeper.bean;

import java.io.Serializable;

import javax.swing.JLabel;

	/**
	 * ����һ������С�׿�
	 */
public class MineLabel extends JLabel{

	private static final long serialVersionUID = -7271927020145498558L;

	/**
	 * �ж��Ƿ�����
	 * 
	 * */
	private boolean isMine;
	/**
	 * �ж��׿��Ƿ�չ��
	 * 
	 * */
	private boolean isExpanded;
	/**
	 * �ж��׿��Ƿ�������
	 * 
	 * */
	private boolean isFlag;
	/**
	 * �ж��Ƿ�������δ��������
	 * */
	private boolean isMineAndNotflag;
	/**
	 * count �����׿���Χ������
	 * 
	 * */
	private int mineCount;
	/**
	 * �׿����ڵ���
	 * 
	 * */
	private int rowIndex;
	/**
	 * �׿����ڵ���
	 * 
	 * */
	private int colIndex;
	
	
	private int expend=0;
	
	
	

	public boolean isMineAndNotflag() {
		return isMineAndNotflag;
	}
	public void setMineAndNotflag(boolean isMineAndNotflag) {
		this.isMineAndNotflag = isMineAndNotflag;
	}
	public int getExpend() {
		return expend;
	}
	public void setExpend(int expend) {
		this.expend = expend;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	//�����ϵ���������
	public MineLabel(int rowIndex, int colIndex) {
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
	}
//�����Ƕ�Ӧ��һЩget��set����
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
	/**
	 * rightClickCount �Ҽ��������
	 * 
	 * */
	private int rightClickCount;

	public int getRightClickCount() {
		return rightClickCount;
	}

	public void setRightClickCount(int rightClickCount) {
		this.rightClickCount = rightClickCount;
	}
	@Override
	public String toString() {
		return "MineLabel [isMine=" + isMine + ", isExpanded=" + isExpanded + ", isFlag=" + isFlag
				+ ", isMineAndNotflag=" + isMineAndNotflag + ", mineCount=" + mineCount + ", rowIndex=" + rowIndex
				+ ", colIndex=" + colIndex + ", expend=" + expend + ", rightClickCount=" + rightClickCount + "]";
	}

}



