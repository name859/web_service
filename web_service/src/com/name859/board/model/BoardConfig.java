package com.name859.board.model;

public class BoardConfig {

	private int boardId;
	private String boardName;
	private String boardConfigTime;
	
	public BoardConfig() {}
	
	public BoardConfig(int boardId, String boardName, String boardConfigTime) {
		this.boardId = boardId;
		this.boardName = boardName;
		this.boardConfigTime = boardConfigTime;
	}
	
	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public String getBoardConfigTime() {
		return boardConfigTime;
	}

	public void setBoardConfigTime(String boardConfigTime) {
		this.boardConfigTime = boardConfigTime;
	}
	
}
