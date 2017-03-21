package com.name859.board.model;

public class Board {

	private int boardNo;
	private int boardId;
	private int userNo;
	private String boardWriter;
	private String boardPw;
	private String boardSubject;
	private String boardContent;
	private String boardFileName;
	private String boardFileRename;
	private long boardFileSize;
	private String boardIp;
	private int boardHit;
	private String boardTime;
	private int commentCount;
	
	public Board() {}
	
	public Board(int boardNo, int boardId, int userNo, String boardWriter, String boardPw, String boardSubject, String boardContent, String boardFileName, String boardFileRename, long boardFileSize, String boardIp, int boardHit, String boardTime, int commentCount) {
		this.boardNo = boardNo;
		this.boardId = boardId;
		this.userNo = userNo;
		this.boardWriter = boardWriter;
		this.boardPw = boardPw;
		this.boardSubject = boardSubject;
		this.boardContent = boardContent;
		this.boardFileName = boardFileName;
		this.boardFileRename = boardFileRename;
		this.boardFileSize = boardFileSize;
		this.boardIp = boardIp;
		this.boardHit = boardHit;
		this.boardTime = boardTime;
		this.commentCount = commentCount;
	}
	
	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getBoardWriter() {
		return boardWriter;
	}

	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}

	public String getBoardPw() {
		return boardPw;
	}

	public void setBoardPw(String boardPw) {
		this.boardPw = boardPw;
	}

	public String getBoardSubject() {
		return boardSubject;
	}

	public void setBoardSubject(String boardSubject) {
		this.boardSubject = boardSubject;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getBoardFileName() {
		return boardFileName;
	}

	public void setBoardFileName(String boardFileName) {
		this.boardFileName = boardFileName;
	}

	public String getBoardFileRename() {
		return boardFileRename;
	}

	public void setBoardFileRename(String boardFileRename) {
		this.boardFileRename = boardFileRename;
	}

	public long getBoardFileSize() {
		return boardFileSize;
	}

	public void setBoardFileSize(long boardFileSize) {
		this.boardFileSize = boardFileSize;
	}

	public String getBoardIp() {
		return boardIp;
	}

	public void setBoardIp(String boardIp) {
		this.boardIp = boardIp;
	}

	public int getBoardHit() {
		return boardHit;
	}

	public void setBoardHit(int boardHit) {
		this.boardHit = boardHit;
	}

	public String getBoardTime() {
		return boardTime;
	}

	public void setBoardTime(String boardTime) {
		this.boardTime = boardTime;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
}
