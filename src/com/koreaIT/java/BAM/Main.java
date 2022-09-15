package com.koreaIT.java.BAM;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		Scanner sc= new Scanner(System.in);
		int contentId = 0;
		
		List<Article> articles = new ArrayList<>();
				
		while(true) {		
			System.out.printf("명령어 : ");
			String cmd = sc.nextLine().trim();
			
			// 프로그램 종료
			if(cmd.equals("exit")) {
				break;
			}
			
			// 명령어를 입력 안했을 경우
			if(cmd.equals("")) {
				System.out.println("명령어를 입력해주세요.");
				continue;
			}
			
			// 게시글 작성
			if (cmd.equals("write")) {
				int id = contentId + 1;
				contentId = id;
				
				// 시간 관련
				LocalDateTime dateTime = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String date = dateTime.format(formatter);
				
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();
				
				Article article = new Article(id, date, title, body);
				articles.add(article);
				
				System.out.printf("%d번 글이 생성되었습니다.\n", id);
			
			// 게시글 리스트
			} else if(cmd.equals("list")) {
				if(articles.isEmpty()) {
					System.out.println("게시글이 존재하지 않습니다.");	
					continue;
				}
				
				System.out.println("번호		|		제목");
				for(int i = articles.size() - 1; i >= 0 ; i--) {
					Article article = articles.get(i);
					System.out.printf("%d		|		%s\n", article.id, article.title);
				}
				
			// 게시글 내용 확인
			} else if(cmd.startsWith("detail ")) {
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[1]);
				
				Article foundArticle = null;
				
				for(Article article : articles) {
					if(article.id == id) {
						foundArticle = article;
						
						break;
					}
				}
				
				if(foundArticle == null) {
					System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
					continue;
				} else {
					System.out.println("번호 : " + foundArticle.id);
					System.out.println("날짜 : " + foundArticle.date);
					System.out.println("제목 : " + foundArticle.title);
					System.out.println("내용 : " + foundArticle.body);
				}
	
			// 게시글 삭제
			} else if(cmd.startsWith("remove ")) {
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[1]);

				Article foundArticle = null;
				
				for(Article article : articles) {
					if(article.id == id) {
						foundArticle = article;
						
						break;
					}
				}
				
				if(foundArticle == null) {
					System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
					continue;
				} else {
					articles.remove(foundArticle);
					System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
				}
			
			// 명령어가 존재하지 않는 경우
			} else {
				System.out.println("존재하지 않는 명령어 입니다.");
			}
		}
		
		System.out.println("== 프로그램 끝 ==");
		sc.close();
	}
}

class Article {
	int id;
	String date;
	String title;
	String body;
	
	Article(int id, String date, String title, String body) {
		this.id = id;
		this.date= date;
		this.title = title;
		this.body = body;
	}
}
