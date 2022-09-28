package com.koreaIT.java.BAM.dao;

public class Dao {
	public int lastId;
	
	// 회원 번호
	public int getNewId() {
		return lastId + 1;
	}
}
