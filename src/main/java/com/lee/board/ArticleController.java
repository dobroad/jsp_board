package com.lee.board;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/article/*")
public class ArticleController extends HttpServlet {
	ArticleDB db = new ArticleDB();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8"); 
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8"); 
		System.out.println("공통코드 실행");
		
		String uri = req.getRequestURI();
		String[] uriPieces = uri.split("/");
		if (uriPieces.length < 3) {
			System.out.println("잘못된 요청입니다.");
			return;
		}
		
		String func = uriPieces[2];
		
		String method = req.getMethod(); // POST, GET
		
		req.setAttribute("func", func);
		if(method.equals("POST")) {
			postProcess(req, resp);
			
		} else if(method.equals("GET")) {
			getProcess(req, resp);
		}
	}
	
	private void postProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String func = (String)request.getAttribute("func");
		if(func.equals("add")) {
			doAdd(request, response);
		}
	}
	
	private void getProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String func = (String)request.getAttribute("func");
		
		if(func.equals("add")) {
			doAdd(request, response);
			
		} else if (func.equals("list")) {
			list(request, response);
			
		} else if(func.equals("showAddForm")) {
			RequestDispatcher rd = request.getRequestDispatcher("/addForm.jsp");
			rd.forward(request, response);
			
		} else if(func.equals("detail")) {
			int idx = Integer.parseInt(request.getParameter("idx"));
			Article article = db.getArticleByIdx(idx);
			request.setAttribute("article", article);
			forward(request, response, "/detail.jsp");	
			
		} else if(func.equals("update")){
			int idx = Integer.parseInt(request.getParameter("idx"));
			String title = request.getParameter("title");
			String body = request.getParameter("body");
			db.updateArticle(idx, title, body);
			response.sendRedirect("/article/detail?idx=" + idx);
			
		} else if(func.equals("showUpdateForm")) {
			int idx = Integer.parseInt(request.getParameter("idx"));
			Article article = db.getArticleByIdx(idx);
			request.setAttribute("article", article);
			forward(request, response, "/updateForm.jsp");
		}
	}

	private void doAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		String nickname = request.getParameter("nickname");
		// 주소록 추가
		db.insertArticle(title, body, nickname);
		// 포워드 => 요청 정보를 재사용. url 안바뀜
		// 리다이렉트 => 새로운 요청을 보냄. url 바꿈.	
		list(request, response);
		// response.sendRedirect("/article/list");
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Article> articleList = db.getAllArticles();
		request.setAttribute("articleList", articleList);
		forward(request, response, "/list.jsp");
	}

	private void forward(HttpServletRequest request, HttpServletResponse response, String path) {
				
		try {			
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
			
		} catch(Exception e) {
			System.out.println("포워딩 중 문제 발생");
			
		}
			
	}
	
}