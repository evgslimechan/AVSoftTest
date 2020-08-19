package com.slimechan.avsoft.listener;

import com.slimechan.avsoft.dao.HibernateRepository;
import com.slimechan.avsoft.manager.PropertiesManager;
import com.slimechan.avsoft.manager.RoleManager;
import com.slimechan.avsoft.manager.UserManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.Properties;

public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String cfgfile = servletContextEvent.getServletContext().getInitParameter("properties");
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(cfgfile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertiesManager.Instance.loadProperties(properties);

        RoleManager.Instance.findAll();
        UserManager.Instance.findAll();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateRepository.getSessionFactory().close();
    }
}
