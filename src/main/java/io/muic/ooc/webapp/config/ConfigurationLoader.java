package io.muic.ooc.webapp.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationLoader {



    public static ConfigProperties load() {

        String configFileName = "config.properties";

        try (FileInputStream fin = new FileInputStream(configFileName)) {
            Properties prop = new Properties();
            prop.load(fin);

            String driverClassName = prop.getProperty("database.driverClassName");
            String connectionUrl = prop.getProperty("database.connectionUrl");
            String username = prop.getProperty("database.username");
            String password = prop.getProperty("database.password");


//            System.out.println(driverClassName);
//            System.out.println(connectionUrl);
//            System.out.println(username);
//            System.out.println(password);
//
//ConfigProperties configProperties = new ConfigProperties();
//configProperties.setDatabaseDriverClassName(driverClassName);
//configProperties.setDatabaseConnectionUrl(connectionUrl);
//configProperties.setDatabaseUsername(username);
//configProperties.setGetDatabasePassword(password);



            return new ConfigProperties.ConfigPropertiesBuilder()
                    .databaseDriverClassName(driverClassName)
                    .databaseConnectionUrl(connectionUrl)
                    .databaseUsername(username)
                    .databasePassword(password)
                    .build();

//return configProperties;


        } catch (IOException e) {
            System.out.println("Exception" + e);
        }
        return null;
    }



}
