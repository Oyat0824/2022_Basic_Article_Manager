package com.koreaIT.java.BAM;

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
			
			if(cmd.equals("exit")) {
				break;
			}
			
			if(cmd.equals("")) {
				System.out.println("명령어를 입력해주세요.");
				continue;
			}
			
			// 게시글 작성
			if (cmd.equals("write")) {
				int id = contentId + 1;
				contentId = id;
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();
				
				Article article = new Article(id, title, body);
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
					System.out.printf("%d		|		%s\n\n", article.id, article.title);
				}
				
			// 게시글 내용 확인
			} else if(cmd.startsWith("detail ")) {
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[1]);
				
				boolean found = false;
				for(Article article : articles) {
					if(article.id == id) {
						found = true;
						
						System.out.println("번호 : " + article.id);
						System.out.println("제목 : " + article.title);
						System.out.println("내용 : " + article.body);
					}
				}
				
				if(found == false) {
					System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
					continue;
				}
	
			// 게시글 삭제
			} else if(cmd.equals("remove")) {
				
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
	String title;
	String body;
	
	Article(int id, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}
}
