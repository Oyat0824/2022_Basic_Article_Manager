package com.koreaIT.java.BAM.service;

import com.koreaIT.java.BAM.container.Container;
import com.koreaIT.java.BAM.dao.MemberDao;
import com.koreaIT.java.BAM.dto.Member;

public class MemberService {
	private MemberDao memberDao;
	
	public MemberService() {
		memberDao = Container.memberDao;
	}
	
	// 회원 번호
	public int setMemberId() {
		return memberDao.setNewId();
	}
	
	// 회원 추가
	public void add(Member member) {
		memberDao.add(member);
	}
	
	// 작성자 이름 찾기
	public String getWriterName(int memberId) {
		return memberDao.getWrtierName(memberId);
	}
	
	// 로그인 아이디에 따른 멤버 정보 가져오기
	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}
	
	// 아이디 중복 체크 메서드
	public boolean isLoginIdChk(String loginId) {
		return memberDao.isLoginIdChk(loginId);
	}
}
