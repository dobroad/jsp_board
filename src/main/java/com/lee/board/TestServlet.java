package com.lee.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// HTTP => 역할/기능에 따른 특정 메서드를 만듦
// - GET: 자원을 가져올 때 사용 (서버의 상태 변하지 않음), 
//		  연산을 했을 때 결과가 변하지 않고 그대로 나오는 성질 => 멱등성
//   	  데이터를 URL에 포함시켜서 전송 (queryString)
//		  캐시 기능 지원
// - POST: 자원을 처리할 때 사용 (서버의 상태 변함)
//         멱등성 X
//         데이터를 메시지 바디에 포함시켜서 전송
//		   캐시 X
// - PUT
// - PATCH
// - DELETE 
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
