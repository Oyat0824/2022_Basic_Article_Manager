package com.koreaIT.java.BAM;

import java.util.Scanner;

import com.koreaIT.java.BAM.controller.ArticleController;
import com.koreaIT.java.BAM.controller.Controller;
import com.koreaIT.java.BAM.controller.MemberController;

public class App {
	public void run() {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);
	
//		컨트롤러 객체 생성
		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);
		
//		테스트 데이터 생성
		articleController.makeTestData();
		memberController.makeTestData();
		System.out.println("");

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


}
