package com.koreaIT.java.BAM.dao;

import java.util.ArrayList;
import java.util.List;

import com.koreaIT.java.BAM.dto.Article;

public class ArticleDao extends Dao {
	private List<Article> articles;

	public ArticleDao() {
		articles = new ArrayList<>();
	}

	// 게시글 작성
	public void add(Article article) {
		articles.add(article);
		lastId++;
	}
	
	// 게시글 삭제
	public void remove(Article foundArticle) {
		articles.remove(foundArticle);
	}
	
	// 검색 결과 리스트 뽑기
	public List<Article> getForPrintArticles(String searchKeyword) {
		if (searchKeyword != null) {
			List<Article> forPrintArticles = new ArrayList<>();

			for (Article article : articles) {
				if (article.title.contains(searchKeyword)) {
					forPrintArticles.add(article);
				}
			}

			return forPrintArticles;
		}

		return articles;
	}

	// ID값에 맞는 게시글 찾기
	public Article getArticleById(int id) {
		for (Article article : articles) {
			if (article.id == id) {
				return article;
			}
		}
		return null;
	}
}
