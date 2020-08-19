package com.slimechan.avsoft.servlet.frontend;

import com.slimechan.avsoft.manager.RenderManager;
import com.slimechan.avsoft.manager.RoleManager;
import com.slimechan.avsoft.manager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin", "/admin/*"})
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] parts = req.getRequestURI().split("/");

        if (parts.length == 2) {
            req.setAttribute("groups", RoleManager.Instance.findAll());
            req.setAttribute("users", UserManager.Instance.findAll());
        } else if (parts.length == 4 && !parts[3].isEmpty()) {
            req.setAttribute("modelType", parts[2]);
            if (parts[2].equalsIgnoreCase("group")) {
                req.setAttribute("model", RoleManager.Instance.getByName(parts[3]));
            } else if (parts[2].equalsIgnoreCase("user")) {
                req.setAttribute("model", UserManager.Instance.getByName(parts[3]));
            }
        }

        getServletContext().getRequestDispatcher(RenderManager.Instance.getPage(req.getRequestURI())).forward(req, resp);
    }
}