package com.koreaIT.java.BAM.controller;

import java.util.List;
import java.util.Scanner;

import com.koreaIT.java.BAM.dto.Member;
import com.koreaIT.java.BAM.util.Util;

public class MemberController {
	List<Member> members;
	Scanner sc;

//	생성자
	public MemberController(List<Member> members, Scanner sc) {
		this.members = members;
		this.sc = sc;
	}

//	회원가입 메서드
	public void doJoin() {
		int id = members.size() + 1;
		String regDate = Util.getNowDataStr();

		// 아이디 조건 루프
		String loginId = null;

		while (true) {
			System.out.printf("로그인할 아이디 : ");
			loginId = sc.nextLine();

			if (loginId.isEmpty()) {
				System.out.println("[❌] 아이디를 입력해주세요.");
				continue;
			}

			if (loginIdChk(loginId)) {
				System.out.printf("[❌] 해당 아이디 < %s > 는 이미 사용중인 아이디입니다.\n", loginId);
				continue;
			}

			System.out.printf("[✔️] 해당 아이디 < %s > 는 사용가능한 아이디입니다.\n", loginId);
			break;
		}

		// 비밀번호 조건 루프
		String loginPw = null;
		String loginPwChk = null;

		while (true) {
			System.out.printf("로그인할 비밀번호 : ");
			loginPw = sc.nextLine();
			System.out.printf("비밀번호 확인 : ");
			loginPwChk = sc.nextLine();

			if (loginPw.isEmpty()) {
				System.out.println("[❌] 비밀번호를 입력해주세요.");
				continue;
			}

			if (loginPw.equals(loginPwChk) == false) {
				System.out.println("[❌] 비밀번호가 일치하지 않습니다, 다시 입력해주세요.");
				continue;
			}

			break;
		}

		System.out.printf("이름 : ");
		String name = sc.nextLine();

		Member member = new Member(id, regDate, loginId, loginPw, name);
		members.add(member);

		System.out.printf("[✔️] [ %s ] 회원님 가입을 축하드립니다!\n", loginId);
	}

//	아이디 중복 체크 메서드
	private boolean loginIdChk(String loginId) {
		// 아이디가 중복인 경우
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return true;
			}
		}
		// 아니면 말고
		return false;
	}

}
