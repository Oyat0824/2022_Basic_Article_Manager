package com.koreaIT.java.BAM.dao;

import java.util.ArrayList;
import java.util.List;

import com.koreaIT.java.BAM.dto.Member;

public class MemberDao extends Dao {
	private List<Member> members;
	
	public MemberDao() {
		members = new ArrayList<>();
	}
	
	// 회원 추가
	public void add(Member member) {
		members.add(member);
		lastId++;
	}
	
	// 작성자 이름 찾기
	public String getWrtierName(int memberId) {
		for (Member member : members) {
			if (memberId == member.id) {
				return member.name;
			}
		}

		return "이름없음";
	}
	
	// 로그인 아이디에 따른 멤버 정보 가져오기
	public Member getMemberByLoginId(String loginId) {
		for (Member member : members) {
			if(member.loginId.equals(loginId)) {
				return member;
			}
		}
		
		return null;
	}

	// 아이디 중복 체크 메서드
	public boolean isLoginIdChk(String loginId) {
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return true;
			}
		}
		// 아니면 말고
		return false;
	}
}
