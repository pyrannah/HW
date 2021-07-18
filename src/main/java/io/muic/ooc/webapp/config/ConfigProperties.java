package io.muic.ooc.webapp.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConfigProperties {

    private String databaseDriverClassName;
    private String databaseConnectionUrl;
    private String databaseUsername;
    private String databasePassword;


}

//    public String getDatabaseDriverClassName() {
//        return databaseDriverClassName;
//    }
//
//    public void setDatabaseDriverClassName(String databaseDriverClassName) {
//        this.databaseDriverClassName = databaseDriverClassName;
//    }
//
//    public String getDatabaseConnectionUrl() {
//        return databaseConnectionUrl;
//    }
//
//    public void setDatabaseConnectionUrl(String databaseConnectionUrl) {
//        this.databaseConnectionUrl = databaseConnectionUrl;
//    }
//
//    public String getDatabaseUsername() {
//        return databaseUsername;
//    }
//
//    public void setDatabaseUsername(String databaseUsername) {
//        this.databaseUsername = databaseUsername;
//    }
//
//    public String getDatabasePassword() {
//        return getDatabasePassword;
//    }
//
//    public void setGetDatabasePassword(String getDatabasePassword) {
//        this.getDatabasePassword = getDatabasePassword;
//    }
//
//
//}

//    public static ConfigProperties load(){
//
//
//
//    }
//
//
//    public Object ConfigPropertiesBuilder() {
//    }
//}
