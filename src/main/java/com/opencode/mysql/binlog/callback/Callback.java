package com.opencode.mysql.binlog.callback;

import java.sql.SQLException;

public interface Callback<T> {

    void execute(T obj) throws SQLException;

}
