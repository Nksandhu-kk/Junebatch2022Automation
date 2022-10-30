package com.envision.automation.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    public static Properties loadPropertiesFile(String filename) throws IOException {
Properties properties=new Properties();
properties.load(new FileInputStream(new File(filename)));

return  properties;

    }

public static   String getPropertyValue(String propertyName, Properties properties){
        return properties.getProperty(propertyName);
}
}
