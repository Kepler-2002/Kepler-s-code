package com.sf.minesweeper.bean;

public class Request {

	//LOGIN_IN;LOGIN_OUT
	private String command;
	//�������ͣ�0������ʹ��ϵͳ��1���ƽ�����Ȩ�������û���2��ע���û���Ϣ��3��ѯ�ʵ�ǰϵͳ��û����ʹ��
	private String requestType;
	//�û�id
	private String userId;
	//�ܷ�
	private int score;
	//ʧ����
	private int errornum;
	
	private int reminder_flagNum = -1;
	
	
	
	public int getReminder_flagNum() {
		return reminder_flagNum;
	}
	public void setReminder_flagNum(int reminder_flagNum) {
		this.reminder_flagNum = reminder_flagNum;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = this.score+score;
	}
	public int getErrornum() {
		return errornum;
	}
	public void setErrornum(int errornum) {
		this.errornum = this.errornum+errornum;
	}
}
