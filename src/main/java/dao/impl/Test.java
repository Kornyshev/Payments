package dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection conn = new MySQLConnectionFactory().createConnection();
        conn.close();
    }
}
