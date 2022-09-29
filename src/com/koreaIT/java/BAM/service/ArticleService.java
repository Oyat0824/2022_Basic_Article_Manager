package com.koreaIT.java.BAM.service;

import java.util.List;

import com.koreaIT.java.BAM.container.Container;
import com.koreaIT.java.BAM.dao.ArticleDao;
import com.koreaIT.java.BAM.dto.Article;

public class ArticleService {
	private ArticleDao articleDao;
	
	ArticleService() {
		articleDao = Container.articleDao;
	}
	
	// 게시글 번호
	public int setArticleId() {
		return articleDao.setNewId();
	}
	
	// 게시글 작성
	public void add(Article article) {
		articleDao.add(article);
	}
	
	// 게시글 삭제
	public void remove(Article foundArticle) {
		articleDao.remove(foundArticle);
	}
	
	// 검색 결과 리스트 뽑기
	public List<Article> getForPrintArticles(String searchKeyword) {
		return articleDao.getForPrintArticles(searchKeyword);
	}
	
	// ID값에 맞는 게시글 찾기
	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}
}
