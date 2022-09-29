package com.koreaIT.java.BAM.service;

import com.koreaIT.java.BAM.container.Container;
import com.koreaIT.java.BAM.dto.Member;

public class MemberService {
	// 회원 번호
	public int getNewId() {
		return Container.memberDao.getNewId();
	}
	
	// 회원 추가
	public void add(Member member) {
		Container.memberDao.add(member);
	}
	
	// 작성자 이름 찾기
	public String getWriterName(int memberId) {
		return Container.memberDao.getWrtierName(memberId);
	}
	
	// 로그인 아이디에 따른 멤버 정보 가져오기
	public Member getMemberByLoginId(String loginId) {
		return Container.memberDao.getMemberByLoginId(loginId);
	}
	
	// 아이디 중복 체크 메서드
	public boolean isLoginIdChk(String loginId) {
		return Container.memberDao.isLoginIdChk(loginId);
	}
}
