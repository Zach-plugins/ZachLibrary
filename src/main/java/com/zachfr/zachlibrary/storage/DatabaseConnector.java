package com.zachfr.zachlibrary.storage;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnector {
    boolean isInitialized();

    void closeConnection();

    void connect(ConnectionCallback callback);

    interface ConnectionCallback {
        void accept(Connection connection) throws SQLException;
    }
}
