package com.slimechan.avsoft.manager.impl;

import com.slimechan.avsoft.manager.PropertiesManager;
import com.slimechan.avsoft.manager.RenderManager;

public class RenderManagerImpl implements RenderManager {

    private static final String PREFIX = PropertiesManager.Instance.getProperty("render.pefix");
    private static final String SUFFIX = PropertiesManager.Instance.getProperty("render.suffix");

    @Override
    public String getPage(String url) {
        String page = "";

        if (url.equalsIgnoreCase("/login")) {
            page = "login";
        } else if (url.equalsIgnoreCase("/register")) {
            page = "register";
        } else if (url.startsWith("/admin")) {
            page = "admin";
            String[] parts = url.split("/");
            if (parts.length == 4 && !parts[3].isEmpty()) {
                String method = parts[2];
                page = "model";
            }

        } else {
            page = "index";
        }

        return PREFIX + page + SUFFIX;
    }
}
