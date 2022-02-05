package com.lee.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("*.do")
public class MemberController extends HttpServlet {

	MemberDB db = new MemberDB();
	ArticleDB adb = new ArticleDB();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
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

		if (method.equals("POST")) {
			postProcess(req, resp);

		} else if (method.equals("GET")) {
			getProcess(req, resp);
		}
	}

	private void postProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String func = (String) request.getAttribute("func");

		if (func.equals("add.do")) {
			//
			String loginId = request.getParameter("loginId");
			String loginPw = request.getParameter("loginPw");
			String nickname = request.getParameter("nickname");

			db.insertMember(loginId, loginPw, nickname);

			response.sendRedirect("/article/list");

		} else if (func.equals("login.do")) {
			String loginId = request.getParameter("loginId");
			String loginPw = request.getParameter("loginPw");

			int idx = db.getMemberIdxByLoginInfo(loginId, loginPw);
			if (idx != 0) { // 로그인 성공
				Member member = db.getMemberByIdx(idx);
				ArrayList<Article> articles = adb.getAllArticles();

				// request는 데이터 유지가 힘듦
				//request.setAttribute("loginedUserName", member.getNickname());
				//request.setAttribute("articleList", articles);
				
				// session 저장소에 저장하도록 한다.
				HttpSession session = request.getSession();
				session.setAttribute("loginedUserName", member.getNickname());
				session.setAttribute("articleList", articles);

				forward(request, response, "/list.jsp");

			} else { // 로그인 실패
				System.out.println("lose");
			}
			
		}
	}

	private void getProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String func = (String) request.getAttribute("func");

		if (func.equals("showLoginForm.do")) {
			forward(request, response, "/member/loginForm.jsp");
			
		} else if(func.equals("logout.do")) {
			// 로그아웃 처리
			// session 내용을 지운다.
			HttpSession session = request.getSession();
			session.removeAttribute("loginedUserName");
			
			response.sendRedirect("/article/list");
			
		} else if(func.equals("test.do")) { // 스코프 사용 테스트 용도
			request.setAttribute("test", "req");
			
			HttpSession session = request.getSession();
			session.setAttribute("test", "sess");
			
			ServletContext application = request.getServletContext();
			application.setAttribute("test", "app");
			
			forward(request, response, "/scopeTest.jsp");
		}

	}

	private void forward(HttpServletRequest request, HttpServletResponse response, String path) {
		try {
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);

		} catch (Exception e) {
			System.out.println("포워딩 중 문제 발생");
		}

	}
}
