package com.koreaIT.java.BAM.controller;

import com.koreaIT.java.BAM.dto.Member;

public abstract class Controller {
	public static Member loginedMember;
	
	// 로그인 체크 메서드
	public static boolean isLogined() {
		return loginedMember != null;
	}
	
	// 분기 실행 메서드
	public abstract void doAction(String cmd, String methodName);
	
	// 테스트 데이터 생성
	public abstract void makeTestData();
}
