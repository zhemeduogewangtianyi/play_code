package com.opencode;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

//package com.sf.mdmtrm.frame2.integration.db.ds.init;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class DbSource implements DataSource {
    private DataSource dataSource;
    private String dbName;
    private String dbHost;

    public String getDbName() {
        return this.dbName;
    }

    public DbSource(DataSource dataSource, String dbName, String dbHost) {
        this.dataSource = dataSource;
        this.dbName = dbName;
        this.dbHost = dbHost;
    }

    public PrintWriter getLogWriter() throws SQLException {
        return this.dataSource.getLogWriter();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.dataSource.unwrap(iface);
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        this.dataSource.setLogWriter(out);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.dataSource.isWrapperFor(iface);
    }

    public Connection getConnection() throws SQLException {
        return this.prepareConnection(this.dataSource.getConnection());
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        this.dataSource.setLoginTimeout(seconds);
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return this.prepareConnection(this.dataSource.getConnection(username, password));
    }

    public int getLoginTimeout() throws SQLException {
        return this.dataSource.getLoginTimeout();
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.dataSource.getParentLogger();
    }

    private Connection prepareConnection(Connection c) throws SQLException {
        c.setCatalog(this.dbName);
        return c;
    }

    public String getDbHost() {
        return this.dbHost;
    }
}
