package com.slimechan.avsoft.servlet.frontend;

import com.slimechan.avsoft.manager.RenderManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "IndexServlet", urlPatterns = {"", "/"}, asyncSupported = true)
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] parts = req.getRequestURI().split("/");

        if (req.getRequestURI().equalsIgnoreCase("/favicon.ico")) {
            return;
        }

        getServletContext().getRequestDispatcher(RenderManager.Instance.getPage(req.getRequestURI())).forward(req, resp);
    }
}