package com.slimechan.avsoft.servlet.api;

import com.slimechan.avsoft.entity.permission.Permission;
import com.slimechan.avsoft.manager.RoleManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/api/group", "/api/group/*"})
public class RoleApiServlet extends HttpServlet {

    // TODO: add jQuery to send PUT and DELETE requests

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] parts = req.getRequestURI().split("/");

        if (parts.length == 5 && !parts[3].isEmpty()) {
            String permissionName = req.getParameter("permission");
            String value = req.getParameter("value");
            String weight = req.getParameter("weight");

            if (!permissionName.isEmpty() && !value.isEmpty() && !weight.isEmpty()) {
                Permission permission = new Permission(permissionName, value, Integer.parseInt(weight));
                if (parts[4].equalsIgnoreCase("add")) {
                    RoleManager.Instance.addPermission(parts[3], permission);
                } else if (parts[4].equalsIgnoreCase("remove")) {
                    RoleManager.Instance.removePermission(parts[3], permission);
                }
            }
        }
        resp.sendRedirect("/admin/" + parts[2] + "/" + parts[3]);
    }

}
