package com.name859.user;

public class User {

	private int userNo;
	private String userEmail;
	private String userPw;
	private String userName;
	private String userTime;
	
	public User() {}
	
	public User(int userNo, String userEmail, String userPw, String userName, String userTime) {
		this.userNo = userNo;
		this.userEmail = userEmail;
		this.userPw = userPw;
		this.userName = userName;
		this.userTime = userTime;
	}
	
	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTime() {
		return userTime;
	}

	public void setUserTime(String userTime) {
		this.userTime = userTime;
	}
	
}
