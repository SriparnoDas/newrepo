package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("name");
		String address = request.getParameter("address");
		String sex = request.getParameter("sex");
		String highestQualification = request.getParameter("qualification");
		String course = request.getParameter("select2");
		String courseDuration = request.getParameter("duration");
		String pass = request.getParameter("password");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		RequestDispatcher dispatcher = null;
		Connection conn = null;
//		String age = "";
		PrintWriter out = response.getWriter();
//		int intAge = Integer.parseInt(request.getParameter("age"));
//		if(intAge > 0 && intAge <= 100) {
//			age = Integer.toString(intAge);
//		}else {
//			out.println("<script type =\"text/javascript\">");
//			out.println("alert('Age is Not Correct');");
//			out.println("location='courseRegistration.jsp';");
//			out.println("</script>");
//		}
		String age = request.getParameter("age");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HackathonProject","root","root");
			PreparedStatement pst = conn.prepareStatement("insert into course(id, uname, address, age, sex, highestQualification, course, courseDuration, pass) values(?,?,?,?,?,?,?,?,?)");
			pst.setString(1, id);
			pst.setString(2, uname);
			pst.setString(3, address);
			pst.setString(4, age);
			pst.setString(5, sex);
			pst.setString(6, highestQualification);
			pst.setString(7, course);
			pst.setString(8, courseDuration);
			pst.setString(9, pass);
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("userSuccessCourse.jsp");
			if(rowCount > 0) {
				request.setAttribute("status", "success");
			}else {
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}
