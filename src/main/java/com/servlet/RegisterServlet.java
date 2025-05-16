package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDao;
import com.db.HibernateUtil;
import com.entity.User;

@WebServlet("/userRegister")
public class RegisterServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name = req.getParameter("fullName");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String about = req.getParameter("about");
		
		User u = new User(name, email, password, about);
		
		UserDao dao = new UserDao(HibernateUtil.getEntityManagerFactory());
		boolean f = dao.saveUser(u);
		
		HttpSession session = req.getSession();
		
		if(f) {
			session.setAttribute("msg", "Register Successfully");
			resp.sendRedirect("register.jsp");
		}
		else {
			session.setAttribute("msg", "Something Went Wrong");
			resp.sendRedirect("register.jsp");
		}
	}
}


//  1.01