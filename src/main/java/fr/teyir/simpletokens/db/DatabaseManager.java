package fr.teyir.simpletokens.db;

import fr.teyir.simpletokens.SimpleTokens;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseManager {
    private final String user;
    private final String pass;
    private final String uri;
    private Connection connection;

    private SimpleTokens plugin;

    public DatabaseManager(String user, String pass, String uri) {
        this.user = user;
        this.pass = pass;
        this.uri = uri;
        connect();
    }

    private void connect() {
        SimpleTokens.getPlugin(SimpleTokens.class).getLogger().log(Level.INFO, "Trying to connect to the db");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(this.uri, this.user, this.pass);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        SimpleTokens.getPlugin(SimpleTokens.class).getLogger().log(Level.INFO, "Connected to the MySQL database");
    }

    public void disconnect() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
            SimpleTokens.getPlugin(SimpleTokens.class).getLogger().log(Level.INFO, "Disconnected from the MySQL database");
        }
    }

    public Connection getConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed())
                return this.connection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connect();
        return this.connection;
    }

}