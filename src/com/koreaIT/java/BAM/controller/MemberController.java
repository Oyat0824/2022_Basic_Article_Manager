package com.koreaIT.java.BAM.controller;

import java.util.List;
import java.util.Scanner;

import com.koreaIT.java.BAM.container.Container;
import com.koreaIT.java.BAM.dto.Member;
import com.koreaIT.java.BAM.util.Util;

public class MemberController extends Controller {
	private List<Member> members;
	private Scanner sc;

//	생성자
	public MemberController(Scanner sc) {
		this.members = Container.memberDao.members;
		this.sc = sc;
	}

//	명령어 분기 실행문
	@Override
	public void doAction(String cmd, String methodName) {
		switch (methodName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "profile":
			showProfile();
			break;
		case "logout":
			doLogout();
			break;
		default:
			System.out.println("[❌] 존재하지 않는 명령어 입니다.");
			break;
		}
	}

	//	회원가입 메서드
	private void doJoin() {
		int id = Container.memberDao.getNewId();
		String regDate = Util.getNowDateStr();

		// 아이디 조건 루프
		String loginId = null;

		while (true) {
			System.out.printf("가입 아이디 : ");
			loginId = sc.nextLine().trim();

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
			loginPw = sc.nextLine().trim();
			System.out.printf("비밀번호 확인 : ");
			loginPwChk = sc.nextLine().trim();

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
		Container.memberDao.add(member);

		System.out.printf("[✔️] [ %s ] 회원님 가입을 축하드립니다!\n", loginId);
	}

//	로그인 메서드
	private void doLogin() {
		
		Member member = null;
		String loginId = null;
		String loginPw = null;
		
		while(true) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine().trim();
			
			if(loginId.isEmpty()) {
				System.out.println("[❌] 아이디를 입력해주세요.");
				continue;
			}
			
			while(true) {
				System.out.printf("로그인 비밀번호 : ");
				loginPw = sc.nextLine().trim();	
				
				if(loginPw.isEmpty()) {
					System.out.println("[❌] 비밀번호를 입력해주세요.");
					continue;
				}
				
				break;
			}
			
			member = getMemberByLoginId(loginId);
			
			if(member == null) {
				System.out.println("[❌] 해당 아이디는 존재하지 않습니다.");
				return;
			}
			
			if(member.loginPw.equals(loginPw) == false) {
				System.out.println("[❌] 비밀번호를 확인해주세요.");
				return;
			}
			
			break;
		}

		loginedMember = member;
		System.out.printf("[✔️] [ %s ]님 환영합니다!\n", loginedMember.name);
	}
	
//	로그아웃 기능 메서드
	private void doLogout() {
		loginedMember = null;
		System.out.println("[✔️] 로그아웃 되었습니다.");
	}
	
//	프로필 보기 메서드
	private void showProfile() {
		System.out.println("== 내 정보 ==");
		System.out.printf("> 회원번호 : %d", loginedMember.id);
		System.out.printf("> 로그인 아이디 : %s\n", loginedMember.loginId);
		System.out.printf("> 이름 : %s\n", loginedMember.name);
	}

	
// ----------------------------------------------------------------------------------- 
	
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

//	테스트 데이터 생성
	@Override
	public void makeTestData() {
		System.out.println(">> 테스트를 위한 회원 데이터를 생성합니다.");
		Container.memberDao.add(new Member(Container.memberDao.getNewId(), Util.getNowDateStr(), "admin", "admin", "관리자"));
		Container.memberDao.add(new Member(Container.memberDao.getNewId(), Util.getNowDateStr(), "test", "test", "테스트"));
		Container.memberDao.add(new Member(Container.memberDao.getNewId(), Util.getNowDateStr(), "user", "user", "유저"));
	}
}
