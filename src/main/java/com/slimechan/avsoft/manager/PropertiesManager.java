package com.slimechan.avsoft.manager;

import com.slimechan.avsoft.manager.impl.PropertiesManagerImpl;

import java.util.Properties;

public interface PropertiesManager {

    PropertiesManager Instance = new PropertiesManagerImpl();

    void loadProperties(Properties properties);

    String getProperty(String key);

}
