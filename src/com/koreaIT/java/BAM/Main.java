package com.koreaIT.java.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	// Main 전역 변수
	private static List<Article> articles;
	// Static 생성자로 초기화
	static { articles = new ArrayList<>(); }
	
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		
		makeTestData();
		
		Scanner sc = new Scanner(System.in);
		
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

			// 게시글 작성
			if (cmd.equals("write")) {
				int id = articles.size() + 1;
				String regDate = Util.getNowDataStr();

				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, regDate, title, body);
				articles.add(article);

				System.out.printf("%d번 글이 생성되었습니다.\n", id);

			// 게시글 리스트
			} else if (cmd.equals("list")) {
				if (articles.isEmpty()) {
					System.out.println("게시글이 존재하지 않습니다.");
					continue;
				}

				System.out.println("번호		|		제목		|		작성일			|		조회수");
				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);
					System.out.printf("%d		|		%s		|		%s		|		%s\n", article.id, article.title, article.regDate.substring(0, 10), article.viewCnt);
				}

			// 게시글 내용 확인
			} else if (cmd.startsWith("detail ")) {
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[1]);

				Article foundArticle = null;

				for (Article article : articles) {
					if (article.id == id) {
						foundArticle = article;

						break;
					}
				}

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

			// 게시글 삭제
			} else if (cmd.startsWith("delete ")) {
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[1]);

				Article foundArticle = null;

				for (Article article : articles) {
					if (article.id == id) {
						foundArticle = article;

						break;
					}
				}

				if (foundArticle == null) {
					System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
					continue;
				}
				
				articles.remove(foundArticle);
				System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

			// 게시글 수정
			} else if (cmd.startsWith("modify ")) {
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[1]);

				Article foundArticle = null;

				for (Article article : articles) {
					if (article.id == id) {
						foundArticle = article;

						break;
					}
				}

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
				
			// 명령어가 존재하지 않는 경우
			} else {
				System.out.println("존재하지 않는 명령어 입니다.");
			}
		}

		System.out.println("== 프로그램 끝 ==");
		sc.close();
	}

	private static void makeTestData() {
		System.out.println("테스트를 위한 게시물 데이터를 생성합니다.\n");
		articles.add(new Article(1, Util.getNowDataStr() , "제목 1", "내용 1", 111));
		articles.add(new Article(2, Util.getNowDataStr() , "제목 2", "내용 2", 222));
		articles.add(new Article(3, Util.getNowDataStr() , "제목 3", "내용 3", 333));
	}
}

class Article {
	int id;
	String regDate;
	String title;
	String body;
	int viewCnt;

	Article(int id, String regDate, String title, String body) {
		this(id, regDate, title, body, 0);
	}
	
	Article(int id, String regDate, String title, String body, int viewCnt) {
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.body = body;
		this.viewCnt = viewCnt;
	}

	public void addViewCnt() {
		this.viewCnt += 1;
	}
}
