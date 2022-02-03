package com.lee.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

				// 게시물 목록으로 간다 => 회원정보 불러와야되니까 포워딩
				request.setAttribute("loginedUserName", member.getNickname());

				request.setAttribute("articleList", articles);

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
			response.sendRedirect("/article/list");
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
