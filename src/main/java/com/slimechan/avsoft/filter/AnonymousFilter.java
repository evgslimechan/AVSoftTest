package com.slimechan.avsoft.filter;

import com.slimechan.avsoft.entity.user.impl.AnonymousUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class AnonymousFilter implements Filter {

    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        Object user = req.getSession().getAttribute("user");

        if (user == null) {
            req.getSession().setAttribute("user", new AnonymousUser());
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}
