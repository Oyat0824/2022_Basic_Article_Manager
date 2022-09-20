package com.koreaIT.java.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.java.BAM.dto.Article;
import com.koreaIT.java.BAM.dto.Member;
import com.koreaIT.java.BAM.util.Util;

public class App {
	// Main 전역 변수
	private List<Article> articles; // 게시글 목록
	private List<Member> members; // 회원 목록

	App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void run() {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);

		// 더미 데이터 생성
		makeTestData();

		while (true) {
			System.out.printf("명령어 ) ");
			String cmd = sc.nextLine().trim();

			// 프로그램 종료
			if (cmd.equals("exit")) {
				break;
			}

			// 명령어를 입력 안했을 경우
			if (cmd.equals("")) {
				System.out.println("명령어를 입력해주세요.");
				continue;
			}

//			회원가입 기능
			if (cmd.equals("join")) {
//				아이디가 중복또는 입력이 없는 경우 아이디부터 재입력하는 기능
//				비밀번호 일치하면 넘어가고, 아닌 경우 비번부터 재입력하는 기능
				int id = members.size() + 1;
				String regDate = Util.getNowDataStr();

				String loginId = null;

				while (true) {
					System.out.printf("로그인할 아이디 : ");
					loginId = sc.nextLine();

					if (loginId.isEmpty()) {
						System.out.println("아이디를 입력해주세요.");
						continue;
					}

					boolean idChk = false;
					for (Member member : members) {
						if (member.loginId.equals(loginId)) {
							idChk = true;
						}
					}

					if (idChk == true) {
						System.out.printf(">> %s 아이디는 이미 존재합니다.\n", loginId);
						continue;
					}

					break;
				}

				String loginPw = null;
				String loginPwChk = null;

				while (true) {
					System.out.printf("로그인할 비밀번호 : ");
					loginPw = sc.nextLine();
					System.out.printf("비밀번호 확인 : ");
					loginPwChk = sc.nextLine();

					if (!loginPw.equals(loginPwChk)) {
						System.out.println(">> 비밀번호가 일치하지 않습니다.");
						continue;
					}

					break;
				}

				System.out.printf("이름 : ");
				String name = sc.nextLine();

				Member member = new Member(id, regDate, loginId, loginPw, name);
				members.add(member);

				System.out.printf("[ %s ] 회원님 가입을 축하드립니다!\n", loginId);

//			게시글 작성
			} else if (cmd.equals("write")) {
				int id = articles.size() + 1;
				String regDate = Util.getNowDataStr();

				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, regDate, title, body);
				articles.add(article);

				System.out.printf("%d번 글이 생성되었습니다.\n", id);

//			게시글 리스트
			} else if (cmd.startsWith("list")) {
				if (articles.isEmpty()) {
					System.out.println("게시글이 존재하지 않습니다.");
					continue;
				}

//				게시글 검색 기능
				List<Article> forPrintArticles = articles;

				String searchKeyword = cmd.substring("list".length()).trim();

				if (searchKeyword.length() > 0) {
					System.out.println("검색어 : " + searchKeyword);

//					필터 기능을 통해 구현도 가능하다.
//					forPrintArticles = articles.stream().filter(a -> a.title.contains(searchKeyword)).collect(Collectors.toList());

					forPrintArticles = new ArrayList<>();

					for (Article article : articles) {
						if (article.title.contains(searchKeyword)) {
							forPrintArticles.add(article);
						}
					}

					if (forPrintArticles.size() == 0) {
						System.out.println("검색결과가 없습니다.\n");
						continue;
					}
				}

				System.out.println("번호		|		제목		|		작성일			|		조회수");
				for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
					Article article = forPrintArticles.get(i);
					System.out.printf("%d		|		%s		|		%s		|		%s\n", article.id,
							article.title, article.regDate.substring(0, 10), article.viewCnt);
				}

//			게시글 내용 확인
			} else if (cmd.startsWith("detail ")) {
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[1]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
					continue;
				}

				foundArticle.addViewCnt();

				System.out.println("번호 : " + foundArticle.id);
				System.out.println("날짜 : " + foundArticle.regDate);
				System.out.println("조회 : " + foundArticle.viewCnt);
				System.out.println("제목 : " + foundArticle.title);
				System.out.println("내용 : " + foundArticle.body);

//			게시글 삭제
			} else if (cmd.startsWith("delete ")) {
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[1]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
					continue;
				}

				articles.remove(foundArticle);
				System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

//			게시글 수정
			} else if (cmd.startsWith("modify ")) {
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[1]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
					continue;
				}

				System.out.printf("수정할 제목 : ");
				String title = sc.nextLine();
				System.out.printf("수정할 내용 : ");
				String body = sc.nextLine();

				foundArticle.title = title;
				foundArticle.body = body;
				System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

//			존재하지 않는 명령어
			} else {
				System.out.println("존재하지 않는 명령어 입니다.");
			}
		}

		System.out.println("== 프로그램 끝 ==");
		sc.close();
	}

	// 게시글 찾기 메서드
	private Article getArticleById(int id) {
		for (Article article : articles) {
			if (article.id == id) {
				return article;
			}
		}

		return null;
	}

	// 테스트 게시글 생성 메소드
	private void makeTestData() {
		System.out.println("테스트를 위한 게시물 데이터를 생성합니다.\n");
		articles.add(new Article(1, Util.getNowDataStr(), "제목 1", "내용 1", 111));
		articles.add(new Article(2, Util.getNowDataStr(), "제목 2", "내용 2", 222));
		articles.add(new Article(3, Util.getNowDataStr(), "제목 3", "내용 3", 333));
	}
}
