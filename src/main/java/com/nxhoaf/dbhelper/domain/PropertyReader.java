/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nxhoaf.dbhelper.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 *
 * @author nxhoaf
 */
public class PropertyReader {
    public static final String CONNECTION_DRIVER = "connection.driver";
    public static final String CONNECTION_URL = "connection.url";
    public static final String USER_NAME = "connection.username";
    public static final String PASSWORD = "connection.password";
    public static final String FILE_LOCATION = "fileLocation";
    public static final String TABLE_NAME = "database.tableName";
    public static final String SQL_QUERY = "database.sqlQuery";
    
    
    private static Properties properties;
    
    static {
        properties = new Properties();
        try {
            Resource resource = new ClassPathResource("Application.properties");
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
