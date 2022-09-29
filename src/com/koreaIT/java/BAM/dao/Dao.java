package com.koreaIT.java.BAM.dao;

public class Dao {
	public int lastId;
	
	// ID 번호 세팅
	public int setNewId() {
		return lastId + 1;
	}
}
