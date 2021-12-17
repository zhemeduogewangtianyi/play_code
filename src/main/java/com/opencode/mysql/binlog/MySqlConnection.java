package com.opencode.mysql.binlog;

import com.opencode.mysql.binlog.callback.Callback;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public class MySqlConnection implements Closeable {

    private final String hostname;
    private final int port;
    private final String username;
    private final String password;
    private Connection connection;

    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MySqlConnection(String hostname, int port, String username, String password) throws SQLException {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;

        connect();

    }

    private void connect() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port +
                "?serverTimezone=UTC", username, password);
    }

    public PreparedStatement getStatement() throws SQLException {
        return  (PreparedStatement)this.connection.createStatement();
    }

    public void execute(final String...statements) throws SQLException {
        execute(new Callback<Statement>() {
            @Override
            public void execute(Statement statement) throws SQLException {
                for (String command : statements) {
                    statement.execute(command);
                }
            }
        });
    }

    public void execute(Callback<Statement> callback) throws SQLException {
        execute(callback, false);
    }

    public void execute(Callback<Statement> callback, boolean autocommit) throws SQLException {
        connection.setAutoCommit(autocommit);
        Statement statement = connection.createStatement();
        try {
            callback.execute(statement);
            if (!autocommit) {
                connection.commit();
            }
        } finally {
            statement.close();
        }
    }

    public void query(String sql, Callback<ResultSet> callback) throws SQLException {
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        try {
            ResultSet rs = statement.executeQuery(sql);
            try {
                callback.execute(rs);
                connection.commit();
            } finally {
                rs.close();
            }
        } finally {
            statement.close();
        }
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

}
