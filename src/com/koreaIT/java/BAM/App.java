package com.koreaIT.java.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.java.BAM.controller.ArticleController;
import com.koreaIT.java.BAM.controller.Controller;
import com.koreaIT.java.BAM.controller.MemberController;
import com.koreaIT.java.BAM.dto.Article;
import com.koreaIT.java.BAM.dto.Member;
import com.koreaIT.java.BAM.util.Util;

public class App {
	// Main 전역 변수
	private List<Article> articles; // 게시글 목록
	private List<Member> members; 	// 회원 목록

	App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void run() {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);

//		더미 데이터 생성
		makeTestData();
		
//		컨트롤러 객체 생성
		MemberController memberController = new MemberController(members, sc);
		ArticleController articleController = new ArticleController(articles, sc);

		while (true) {
			System.out.printf("명령어 ) ");
			String cmd = sc.nextLine().trim();

//			명령어를 입력 안했을 경우
			if (cmd.equals("")) {
				System.out.println("[❌] 명령어를 입력해주세요.");
				continue;
			}
			
//			프로그램 종료
			if (cmd.equals("exit")) {
				break;
			}

			String[] cmdBits = cmd.split(" ");
			
			if(cmdBits.length == 1) {
				System.out.println("[❌] 명령어를 확인해주세요.");
				continue;
			}
			
			String controllerName = cmdBits[0];
			String methodName = cmdBits[1];
			
			Controller controller = null;
			
			if(controllerName.equals("member")) {
				controller = memberController;
			} else if(controllerName.equals("article")) {
				controller = articleController;
			} else {
				System.out.println("[❌] 존재하지 않는 명령어 입니다.");
				continue;
			}
			
			controller.doAction(cmd, methodName);
		}

		System.out.println("== 프로그램 끝 ==");
		sc.close();
	}

//	테스트 게시글 생성 메소드
	private void makeTestData() {
		System.out.println(">> 테스트를 위한 게시물 데이터를 생성합니다.\n");
		articles.add(new Article(1, Util.getNowDataStr(), "제목 1", "내용 1", 111));
		articles.add(new Article(2, Util.getNowDataStr(), "제목 2", "내용 2", 222));
		articles.add(new Article(3, Util.getNowDataStr(), "제목 3", "내용 3", 333));
	}
}
