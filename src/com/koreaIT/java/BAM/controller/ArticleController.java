package com.koreaIT.java.BAM.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.java.BAM.dto.Article;
import com.koreaIT.java.BAM.util.Util;

public class ArticleController {
	List<Article> articles;
	Scanner sc;

//	생성자
	public ArticleController(List<Article> articles, Scanner sc) {
		this.articles = articles;
		this.sc = sc;
	}

//	게시글 작성 메서드
	public void doWrite() {
		int id = articles.size() + 1;
		String regDate = Util.getNowDataStr();

		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		Article article = new Article(id, regDate, title, body);
		articles.add(article);

		System.out.printf("[✔️] %d번 글이 생성되었습니다.\n", id);
	}

//	게시글 수정 메서드
	public void doModify(String cmd) {
		String[] cmdBits = cmd.split(" ");
		int id = Integer.parseInt(cmdBits[1]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("[❌] %d번 게시물이 존재하지 않습니다.\n", id);
			return;
		}

		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine();

		foundArticle.title = title;
		foundArticle.body = body;
		System.out.printf("[✔️] %d번 게시물이 수정되었습니다.\n", id);
	}

//	게시글 삭제 메서드
	public void doDelete(String cmd) {
		String[] cmdBits = cmd.split(" ");
		int id = Integer.parseInt(cmdBits[1]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("[❌] %d번 게시물이 존재하지 않습니다.\n", id);
			return;
		}

		articles.remove(foundArticle);
		System.out.printf("[✔️] %d번 게시물이 삭제되었습니다.\n", id);
	}

//	게시글 리스트 메서드
	public void showList(String cmd) {
		if (articles.isEmpty()) {
			System.out.println("[❌] 게시글이 존재하지 않습니다.");
			return;
		}

		// 게시글 검색 기능
		List<Article> forPrintArticles = articles;

		String searchKeyword = cmd.substring("list".length()).trim();

		if (searchKeyword.length() > 0) {
			System.out.println("검색어 : " + searchKeyword);

			forPrintArticles = new ArrayList<>();

			for (Article article : articles) {
				if (article.title.contains(searchKeyword)) {
					forPrintArticles.add(article);
				}
			}

			if (forPrintArticles.size() == 0) {
				System.out.println("[❌] 검색결과가 없습니다.\n");
				return;
			}
		}

		System.out.println("번호		|		제목		|		작성일			|		조회수");

		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			Article article = forPrintArticles.get(i);
			System.out.printf("%d		|		%s		|		%s		|		%s\n", article.id, article.title, article.regDate.substring(0, 10), article.viewCnt);
		}
	}

//	게시글 상세 정보 메서드
	public void showDetail(String cmd) {
		String[] cmdBits = cmd.split(" ");
		int id = Integer.parseInt(cmdBits[1]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("[❌] %d번 게시물이 존재하지 않습니다.\n", id);
			return;
		}

		foundArticle.addViewCnt();

		System.out.println("번호 : " + foundArticle.id);
		System.out.println("날짜 : " + foundArticle.regDate);
		System.out.println("조회 : " + foundArticle.viewCnt);
		System.out.println("제목 : " + foundArticle.title);
		System.out.println("내용 : " + foundArticle.body);
	}

//	게시글 찾기 메서드
	private Article getArticleById(int id) {
		for (Article article : articles) {
			if (article.id == id) {
				return article;
			}
		}

		return null;
	}

}
