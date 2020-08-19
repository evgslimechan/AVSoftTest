package com.slimechan.avsoft.filter;

import com.slimechan.avsoft.entity.user.AuthUser;
import com.slimechan.avsoft.entity.user.impl.SimpleUser;
import com.slimechan.avsoft.manager.UserManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/register", "/login", "/api/login", "/api/register"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        String uri = req.getRequestURI();

        if (session != null && session.getAttribute("user") != null && AuthUser.class.isAssignableFrom(session.getAttribute("user").getClass())) {
            res.sendRedirect("/");
            return;
        }

        if (req.getMethod().equalsIgnoreCase("post")) {

            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                res.sendRedirect(uri.substring(4) + "?err=empty_credentials");
                return;
            }

            if (uri.equalsIgnoreCase("/api/register")) {
                if (UserManager.Instance.exists(username)) {
                    res.sendRedirect("/register?err=already_exists");
                    return;
                } else {
                    session.setAttribute("canEdit", false);
                    session.setAttribute("user", UserManager.Instance.register(username, password));
                    res.sendRedirect("/");
                    return;
                }

            } else if (uri.equalsIgnoreCase("/api/login")) {
                if (UserManager.Instance.auth(username, password)) {
                    SimpleUser user = (SimpleUser) UserManager.Instance.getByName(username);
                    boolean canEdit = user.getPermissions().parallelStream()
                            .anyMatch(permission -> permission.getName().equalsIgnoreCase("*") ||
                                    (permission.getName().equalsIgnoreCase("edit") && permission.getValue().equalsIgnoreCase("*")));
                    session.setAttribute("canEdit", canEdit);
                    session.setAttribute("user", user);
                    res.sendRedirect("/");
                    return;
                } else {
                    res.sendRedirect("/login?err=bad_credentials");
                    return;
                }
            }
        }
        chain.doFilter(req, res);
    }
}
