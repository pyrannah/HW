package io.muic.ooc.webapp.service;

import io.muic.ooc.webapp.model.User;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserService {
    //find user by username
    // delete user
    // create new users
    //list all users
    //update user by user id

    private static final String INSERT_USER_SQL = "INSERT INTO tbl_user (username, password, display_name) VALUES (?, ?, ?);";
    private static final String SELECT_USER_SQL = "SELECT * FROM tbl_user WHERE username = ?;";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM tbl_user;";
    private static final String DELETE_USER_SQL = "DELETE FROM tbl_user WHERE username = ?;";
    private static final String UPDATE_USER_SQL = "UPDATE tbl_user SET display_name = ? WHERE username = ?;";
    private static final String UPDATE_USER_PASSWORD_SQL = "UPDATE tbl_user SET password = ? WHERE username = ?;";



    @Setter
    private static UserService service = new UserService();
    private static DatabaseConnectionService databaseConnectionService = new DatabaseConnectionService();

    public UserService(){

    }

    public static UserService getInstance(){
        if (service== null){
            service = new UserService();
            UserService.setDatabase(DatabaseConnectionService.getInstance());
        }
        return service;

    }

    private static void setDatabase(DatabaseConnectionService instance) {

    }

    public void createUser(String username, String password, String displayName) throws UserServiceException {

        try (
                Connection connection = databaseConnectionService.getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL);
                )
        {
            ps.setString(1,username);
            ps.setString(2,BCrypt.hashpw(password, BCrypt.gensalt()));
            ps.setString(3,displayName);
            ps.executeUpdate();
            connection.setAutoCommit(false);
            connection.commit();

        }catch (SQLIntegrityConstraintViolationException e){
            throw new UsernameNotUniqueException(String.format("Username %s has already been taken",username));
        }catch (SQLException throwables){
            throw new UserServiceException(throwables.getMessage());
        }


    }

    public User findByUsername(String username) throws UserServiceException {
        try (
                Connection connection = databaseConnectionService.getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL);
        ) {
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("display_name")
            );
        } catch (SQLException throwables) {
            return null;
        }
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try {
                Connection connection = databaseConnectionService.getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS_SQL);
                ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                users.add(
                        new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("display_name")));
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return users;
    }

    public boolean deleteUserByUsername(String username) {
        try (
                Connection connection = databaseConnectionService.getConnection();
                PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL);
        ) {

            ps.setString(1, username);
            ps.executeUpdate();

            return true;

        } catch (SQLException throwables) {
            return false;

        }

    }

    public void updateUserByUserName(String username, String displayName) throws UserServiceException {

        try {
            Connection connection = databaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_USER_SQL);

            ps.setString(1, displayName);
            ps.setString(2, username);

            ps.executeUpdate();

            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }


    }


    public void changePassword(String username, String newPassword) throws UserServiceException {
        try {
            Connection connection = databaseConnectionService.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_USER_PASSWORD_SQL);

            ps.setString(1, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            ps.setString(2, username);

            ps.executeUpdate();

            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }

    }


    public static void main(String[] args) throws UserServiceException, SQLException {

//        UserService userService = UserService.getInstance();
//        try {
//            userService.createUser("admin","123456","Admin");
//        }catch (UserServiceException e){
//            e.printStackTrace();
//        }
//
////        Connection connection = databaseConnectionService.getConnection();
////        System.out.println(connection==null);
////        userService.setDatabaseConnectionService(new DatabaseConnectionService());
//        List<User> users = userService.findAll();
//
//        for (User user : users){
//            System.out.println(user.getUsername());
//        }
//        User user = userService.findByUsername("pp");
//        System.out.println(user.getUsername());


//        Connection connection = databaseConnectionService.getConnection();
//
//        service.createUser("ad","123456","Admin2");
//        System.out.println(connection==null);


        service.createUser("admin1", "password", "displayName");



    }



}
