package com.koreaIT.java.BAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.java.BAM.container.Container;
import com.koreaIT.java.BAM.dto.Article;
import com.koreaIT.java.BAM.dto.Member;
import com.koreaIT.java.BAM.util.Util;

public class ArticleController extends Controller {
	private List<Article> articles;
	private Scanner sc;
	private String cmd;

//	생성자
	public ArticleController(Scanner sc) {
		this.articles = Container.articleDao.articles;
		this.sc = sc;
	}

//	명령어 분기 실행문
	@Override
	public void doAction(String cmd, String methodName) {
		this.cmd = cmd;

		switch (methodName) {
		case "write":
			doWrite();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		default:
			System.out.println("[❌] 존재하지 않는 명령어 입니다.");
			break;
		}
	}

//	게시글 작성 메서드
	private void doWrite() {
		int id = Container.articleDao.getNewId();
		String regDate = Util.getNowDateStr();

		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		Article article = new Article(id, regDate, loginedMember.id, title, body);
		Container.articleDao.add(article);

		System.out.printf("[✔️] %d번 글이 생성되었습니다.\n", id);
	}

//	게시글 수정 메서드
	private void doModify() {
		String[] cmdBits = cmd.split(" ");
		int id = Integer.parseInt(cmdBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("[❌] %d번 게시물이 존재하지 않습니다.\n", id);
			return;
		}
		
		if (foundArticle.memberId != loginedMember.id) {
			System.out.printf("[❌] 권한이 없습니다.\n");
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
	private void doDelete() {
		String[] cmdBits = cmd.split(" ");
		int id = Integer.parseInt(cmdBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("[❌] %d번 게시물이 존재하지 않습니다.\n", id);
			return;
		}
		
		if (foundArticle.memberId != loginedMember.id) {
			System.out.printf("[❌] 권한이 없습니다.\n");
			return;
		}

		articles.remove(foundArticle);
		System.out.printf("[✔️] %d번 게시물이 삭제되었습니다.\n", id);
	}

//	게시글 리스트 메서드
	private void showList() {
		if (articles.isEmpty()) {
			System.out.println("[❌] 게시글이 존재하지 않습니다.");
			return;
		}

		// 게시글 검색 기능
		List<Article> forPrintArticles = articles;

		String searchKeyword = cmd.substring("article list".length()).trim();

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
		
		

		System.out.println("번호		|		제목		|		작성자		|		작성일			|		조회수");
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			Article article = forPrintArticles.get(i);
			String WriterName = getWriterName(article);
			
			System.out.printf("%d		|		%s		|		%s		|		%s		|		%s\n", article.id, article.title, WriterName, article.regDate.substring(0, 10), article.viewCnt);
		}
	}

//	게시글 상세 정보 메서드
	private void showDetail() {
		String[] cmdBits = cmd.split(" ");

		if (cmdBits.length < 3) {
			System.out.printf("[❌] 게시글 번호를 입력해주세요.\n");
			return;
		}

		int id = Integer.parseInt(cmdBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("[❌] %d번 게시물이 존재하지 않습니다.\n", id);
			return;
		}
		
		String WriterName = getWriterName(foundArticle);
		
		foundArticle.addViewCnt();

		System.out.println("번호 : " + foundArticle.id);
		System.out.println("날짜 : " + foundArticle.regDate);
		System.out.println("작성자 : " + WriterName);
		System.out.println("조회수 : " + foundArticle.viewCnt);
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

//	작성자 이름 반환
	private String getWriterName(Article article) {
		for(Member member : Container.memberDao.members) {
			if(article.memberId == member.id) {
				return member.name;
			}
		}
		
		return "이름없음";
	}
	
//	테스트 데이터 생성
	@Override
	public void makeTestData() {
		System.out.println(">> 테스트를 위한 게시물 데이터를 생성합니다.");
		Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 1, "제목 1", "내용 1", 11));
		Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 2, "제목 2", "내용 2", 22));
		Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 2, "제목 3", "내용 3", 33));
	}

}
