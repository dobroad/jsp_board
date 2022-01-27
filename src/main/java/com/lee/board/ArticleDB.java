package com.lee.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class ArticleDB {
	String url = "jdbc:mysql://localhost:3306/b1?serverTimezone=UTC";
	String user = "root";
	String pass = "";
	String driver = "com.mysql.cj.jdbc.Driver";
	
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			System.out.println("Connection 가져오는 중 문제 발생");
		}
		return conn;
	}

	public ArrayList<Article> getArticleList(String sql) {

		Connection conn = getConnection();

		ArrayList<Article> articleList = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int idx = rs.getInt("idx");
				String title = rs.getString("title");
				String body = rs.getString("body");
				String nickname = rs.getString("nickname");
				String regDate = rs.getString("regDate");
				
				Article addr = new Article(idx, title, body, nickname, regDate);
				articleList.add(addr);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articleList;
	}

	public ArrayList<Article> getAllArticles() {
		String sql = "SELECT * FROM article";
		ArrayList<Article> articles = getArticleList(sql);

		return articles;

	}

	public Article getArticleByIdx(int idx) {
		Article article = null;

		String sql = String.format("SELECT * FROM article WHERE idx = %d", idx);		
		ArrayList<Article> articles = getArticleList(sql);

		if(articles.size() > 0) {
			article = articles.get(0); 
		}

		return article;

	}

	public void updateQuery(String sql) {
		Connection conn = getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertArticle(String title, String body, String nickname) {

		String sql = String.format(
				"INSERT INTO article SET `title` = '%s', body = '%s', nickname = '%s', regDate = NOW()", title, body, nickname);
		updateQuery(sql);
	}
	
	public void updateArticle(int idx, String title, String body) {
		String sql = String.format("UPDATE article SET title = '%s', `body` = '%s' WHERE idx = %d", title, body, idx);
		updateQuery(sql);
	}

	public void deleteArticle(int idx) {
		String sql = String.format("DELETE FROM article WHERE idx = %d", idx);
		updateQuery(sql);
	}
	
}