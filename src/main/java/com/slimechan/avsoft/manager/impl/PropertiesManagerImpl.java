package com.slimechan.avsoft.manager.impl;

import com.slimechan.avsoft.manager.PropertiesManager;

import java.util.Properties;

public class PropertiesManagerImpl implements PropertiesManager {

    private Properties properties;

    @Override
    public void loadProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

}
