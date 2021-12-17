package com.opencode.mysql.binlog;

import com.opencode.mysql.binlog.callback.Callback;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws SQLException {
        String host = "127.0.0.1";
        int port = 3306;
        String username = "root";
        String password = null;
        MySqlConnection mySqlConnection = new MySqlConnection(host, port, username, password);
        mySqlConnection.execute(new Callback<Statement>() {
            @Override
            public void execute(Statement statement) throws SQLException {
                statement.execute("drop database if exists mbcj_test");
                statement.execute("create database mbcj_test");
                statement.execute("use mbcj_test");

                statement.execute("drop table if exists test");
                statement.execute("create table test (name varchar(255) primary key)");

                statement.execute("insert into test values('123123')");
            }
        });

    }

}
