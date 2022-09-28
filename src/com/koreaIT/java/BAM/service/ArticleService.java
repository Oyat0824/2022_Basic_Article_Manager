package com.koreaIT.java.BAM.service;

import java.util.List;

import com.koreaIT.java.BAM.container.Container;
import com.koreaIT.java.BAM.dto.Article;

public class ArticleService {
	// 게시글 번호
	public int getNewId() {
		return Container.articleDao.getNewId();
	}
	
	// 게시글 작성
	public void add(Article article) {
		Container.articleDao.add(article);
	}
	
	// 게시글 삭제
	public void remove(Article foundArticle) {
		Container.articleDao.remove(foundArticle);
	}
	
	// 검색 결과 리스트 뽑기
	public List<Article> getForPrintArticles(String searchKeyword) {
		return Container.articleDao.getForPrintArticles(searchKeyword);
	}
	
	// ID값에 맞는 게시글 찾기
	public Article getArticleById(int id) {
		return Container.articleDao.getArticleById(id);
	}
}
