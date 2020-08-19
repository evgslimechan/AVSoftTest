package com.slimechan.avsoft.filter;

import com.slimechan.avsoft.entity.permission.Permission;
import com.slimechan.avsoft.entity.user.AbstractUser;
import com.slimechan.avsoft.manager.FileManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;

@WebFilter(servletNames = {"IndexServlet", "FileServlet"}, asyncSupported = true)
public class FileFilter implements Filter {
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

                boolean isAdmin = user.getPermissions().parallelStream()
                        .anyMatch(permission -> permission.getName().equalsIgnoreCase("*"));

                if (isAdmin) {

                    if (req.getRequestURI().equalsIgnoreCase("/")) {
                        req.setAttribute("files", FileManager.Instance.fileList(".*"));
                    }

                } else {
                    if (req.getRequestURI().startsWith("/download/")) {
                        String[] parts = req.getRequestURI().split("/");
                        if (parts.length > 2 && !parts[2].isEmpty()) {
                            String fileName = parts[2];
                            if (!FileManager.Instance.hasAccess(user, fileName)) {
                                res.sendRedirect("/");
                                return;
                            }
                        }
                    } else {
                        String[] extensions = user.getPermissions().parallelStream()
                                .filter(permission -> permission.getName().equalsIgnoreCase("file.download"))
                                .map(Permission::getValue)
                                .collect(Collectors.toList()).toArray(new String[0]);
                        req.setAttribute("files", FileManager.Instance.fileList(extensions));
                    }
                }
            }
        }
        filterChain.doFilter(req, res);

    }

    @Override
    public void destroy() {
    }
}
