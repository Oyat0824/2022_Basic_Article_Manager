package com.koreaIT.java.BAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.java.BAM.dto.Member;
import com.koreaIT.java.BAM.util.Util;

public class MemberController extends Controller {
	private List<Member> members;
	private Scanner sc;
	private Member loginedMember;
	private String cmd;

//	생성자
	public MemberController(Scanner sc) {
		this.members = new ArrayList<>();
		this.sc = sc;
	}

	@Override
	public void doAction(String cmd, String methodName) {
		this.cmd = cmd;

		switch (methodName.toLowerCase()) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		default:
			System.out.println("[❌] 존재하지 않는 명령어 입니다.");
			break;
		}
	}

//	회원가입 메서드
	private void doJoin() {
		int id = members.size() + 1;
		String regDate = Util.getNowDataStr();

		// 아이디 조건 루프
		String loginId = null;

		while (true) {
			System.out.printf("가입 아이디 : ");
			loginId = sc.nextLine();

			if (loginId.isEmpty()) {
				System.out.println("[❌] 아이디를 입력해주세요.");
				continue;
			}

			if (isLoginIdChk(loginId)) {
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
			System.out.printf("가입 비밀번호 : ");
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

//	로그인 메서드
	private void doLogin() {
		System.out.printf("로그인 아이디 : ");
		String loginId = sc.nextLine();
		System.out.printf("로그인 비밀번호 : ");
		String loginPw = sc.nextLine();
		
		// 아이디/비밀번호 공란일 경우
		if(loginId.isEmpty()) {
			System.out.println("[❌] 아이디를 입력해주세요.");
			return;
		} else if(loginPw.isEmpty()) {
			System.out.println("[❌] 비밀번호를 입력해주세요.");
			return;
		}
		
		Member member = getMemberByLoginId(loginId);
		
		if(member == null) {
			System.out.println("[❌] 해당 아이디는 존재하지 않습니다.");
			return;
		}
		
		if(member.loginPw.equals(loginPw) == false) {
			System.out.println("[❌] 비밀번호를 확인해주세요.");
			return;
		}
		
		loginedMember = member;
		System.out.printf("[✔️] [ %s ] 회원님 환영합니다!\n", loginedMember.loginId);
	}

//	로그인 아이디에 따른 멤버 정보 가져오기
	private Member getMemberByLoginId(String loginId) {
		for (Member member : members) {
			if(member.loginId.equals(loginId)) {
				return member;
			}
		}
		
		return null;
	}

//	아이디 중복 체크 메서드
	private boolean isLoginIdChk(String loginId) {
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
