package com.slimechan.avsoft.filter;

import com.slimechan.avsoft.entity.user.AbstractUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin", "/admin/*", "/api/user/*", "/api/group/*"})
public class AdminFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        if (session != null) {
            AbstractUser user = (AbstractUser) session.getAttribute("user");
            if (user != null) {
                boolean hasAccess = user.getPermissions().parallelStream()
                        .anyMatch(permission -> permission.getName().equalsIgnoreCase("*") ||
                                (permission.getName().equalsIgnoreCase("edit") && permission.getValue().equalsIgnoreCase("*")));
                if (hasAccess) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }
        res.sendRedirect("/");
    }

    @Override
    public void destroy() {
    }
}
