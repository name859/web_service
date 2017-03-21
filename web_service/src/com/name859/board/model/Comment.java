package com.name859.board.model;

public class Comment {

	private int commentNo;
	private int boardNo;
	private int userNo;
	private String commentWriter;
	private String commentContent;
	private String commentIp;
	private String commentTime;
	
	public Comment() {}
	
	public Comment(int commentNo, int boardNo, int userNo, String commentWriter, String commentContent, String commentIp, String commentTime) {
		this.commentNo = commentNo;
		this.boardNo = boardNo;
		this.userNo = userNo;
		this.commentWriter = commentWriter;
		this.commentContent = commentContent;
		this.commentIp = commentIp;
		this.commentTime = commentTime;
	}
	
	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getCommentWriter() {
		return commentWriter;
	}

	public void setCommentWriter(String commentWriter) {
		this.commentWriter = commentWriter;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentIp() {
		return commentIp;
	}

	public void setCommentIp(String commentIp) {
		this.commentIp = commentIp;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	
}
