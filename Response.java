package com.sf.minesweeper.bean;

import java.util.Map;

/**
 * 响应结构
 */
public class Response {

	//当前系统人数
	private  int localPlayerNum ;
	
	//当前游戏中的用户:1或2
	//默认为NONE
	private String playType_in_Game;
	
	private int score;
	
	private int errornum;
	
	private int reminder_flagNum = -1;
	
	private Map<String, Integer>errornumMap;
	
	private Map<String, Integer>scoreMap;
	
	
	
	public Map<String, Integer> getErrornumMap() {
		return errornumMap;
	}
	public void setErrornumMap(Map<String, Integer> errornumMap) {
		this.errornumMap = errornumMap;
	}
	public Map<String, Integer> getScoreMap() {
		return scoreMap;
	}
	public void setScoreMap(Map<String, Integer> scoreMap) {
		this.scoreMap = scoreMap;
	}
	
	public int getReminder_flagNum() {
		return reminder_flagNum;
	}
	public void setReminder_flagNum(int reminder_flagNum) {
		this.reminder_flagNum = reminder_flagNum;
	}
	
	public int getLocalPlayerNum() {
		return localPlayerNum;
	}

	public void setLocalPlayerNum(int localPlayerNum) {
		this.localPlayerNum = localPlayerNum;
	}


	public String getPlayType_in_Game() {
		return playType_in_Game;
	}

	public void setPlayType_in_Game(String playType_in_Game) {
		this.playType_in_Game = playType_in_Game;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getErrornum() {
		return errornum;
	}

	public void setErrornum(int errornum) {
		this.errornum = errornum;
	}

	
	
}

