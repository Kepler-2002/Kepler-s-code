package com.sf.minesweeper.bean;

public class Request {

	//LOGIN_IN;LOGIN_OUT
	private String command;
	//请求类型：0：申请使用系统，1：移交控制权给其他用户，2；注册用户信息，3：询问当前系统有没有人使用
	private String requestType;
	//用户id
	private String userId;
	//总分
	private int score;
	//失误数
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
