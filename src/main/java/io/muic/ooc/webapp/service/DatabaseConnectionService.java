package io.muic.ooc.webapp.service;
import com.zaxxer.hikari.HikariDataSource;
import io.muic.ooc.webapp.config.ConfigProperties;
import io.muic.ooc.webapp.config.ConfigurationLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnectionService {

    private static DatabaseConnectionService service;

    public Connection getConnection() {
        try {
            ConfigProperties configProperties = ConfigurationLoader.load();
            if (configProperties == null) {
                throw new RuntimeException("Unable to read the config.properties");
            }
            String jdbcDriver =  configProperties.getDatabaseDriverClassName();
            String jdbcURL =  configProperties.getDatabaseConnectionUrl();
            String username = configProperties.getDatabaseUsername();
            String password = configProperties.getDatabasePassword();
            Class.forName(jdbcDriver);

            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            return connection;

        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    public static DatabaseConnectionService getInstance() {
        if (service == null) {
            service = new DatabaseConnectionService();
        }
        return service;
    }


    public static void main(String[] args) {

        final HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/login_webapp");
        ds.addDataSourceProperty("user", "root");
        ds.addDataSourceProperty("password", "12345678");
        ds.setAutoCommit(false);


        try {
            Connection connection = ds.getConnection();
            String sql = "INSERT INTO tbl_user (username, password, display_name)" +
                    "VALUES (?,?,?)";
            connection.prepareStatement(sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,"my_username");
            ps.setString(2,"my_password");
            ps.setString(3,"my_display_name");
            ps.executeUpdate();
            connection.commit(); // have to be commit the change


        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

    }

}
