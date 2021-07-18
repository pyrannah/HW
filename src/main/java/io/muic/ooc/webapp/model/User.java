package io.muic.ooc.webapp.model;


public class User {

    private long id;
    private String username;
    private String password;
    private String display_name;

    public User(){

    }

    public User(long id, String username, String password, String display_name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.display_name = display_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisplayname(String display_name) {
        this.display_name = display_name;
    }

    public String getDisplayName() {
        return display_name;
    }
}
