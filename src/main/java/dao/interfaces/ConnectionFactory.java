package dao.interfaces;

import dao.impl.LoginToMySQLException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    Connection createConnection() throws ClassNotFoundException, SQLException, LoginToMySQLException;
}