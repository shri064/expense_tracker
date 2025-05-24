package com.servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.dao.UserDao;
import com.db.HibernateUtil;
import com.entity.User;

@WebServlet("/userRegister")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String about = req.getParameter("about");

        HttpSession session = req.getSession();
        UserDao dao = new UserDao(HibernateUtil.getEntityManagerFactory());

        
        User existingUser = dao.getUserByEmail(email);
        if (existingUser != null) {
            session.setAttribute("msg", "User already exists");
            resp.sendRedirect("register.jsp");
            return;
        }

        User u = new User(name, email, password, about);
        boolean f = dao.saveUser(u);

        if (f) {
            session.setAttribute("msg", "Register Successfully");
        } else {
            session.setAttribute("msg", "Something Went Wrong");
        }

        resp.sendRedirect("register.jsp");
    }
}
