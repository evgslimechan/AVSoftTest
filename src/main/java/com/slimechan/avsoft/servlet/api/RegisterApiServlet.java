package com.slimechan.avsoft.servlet.api;

import com.slimechan.avsoft.entity.user.AuthUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/api/register")
public class RegisterApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (AuthUser.class.isAssignableFrom(req.getSession().getAttribute("user").getClass())) {
            resp.sendRedirect("/");
        } else resp.sendRedirect("/login");
    }

}
