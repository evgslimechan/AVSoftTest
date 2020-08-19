package com.slimechan.avsoft.servlet.api;


import com.slimechan.avsoft.manager.FileManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DownloadApiServlet", urlPatterns = {"/download/*"}, asyncSupported = true)
public class DownloadApiServlet extends HttpServlet {


    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] parts = req.getRequestURI().split("/");

        if (parts.length > 2 && !parts[2].isEmpty()) {

            if (FileManager.Instance.findFile(parts[2]) != null)
                FileManager.Instance.download(parts[2], resp);
        }
        resp.sendRedirect("/");
    }
}
