package dao.interfaces;

import dao.impl.LoginToMySQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    /*
    Задачи:
    - обработать исключение при считывании логина и пароля
    - вынести создание соединения во всех реализациях
    - вынести закрытие ресурсов во всех реализациях
    - настроить импорты в Идее, отрефакторить в проекте
    - вынести общую функциональность в ДАО слое в этот интерфейс - CRUD
    - попробовать убрать папки в gitignore
     */

    int insert(T entity) throws SQLException, ClassNotFoundException, LoginToMySQLException;
    T retrieve(int id) throws SQLException;
    List<T> retrieveAll() throws SQLException;
    int update(T entity) throws SQLException;
    int delete(int id) throws SQLException, LoginToMySQLException, ClassNotFoundException;

    static void closing(PreparedStatement st, Connection conn) throws SQLException {
        if (st != null) st.close();
        if (conn != null) conn.close();
    }

    static void closing(ResultSet rs, PreparedStatement st, Connection conn) throws SQLException {
        if (rs != null) rs.close();
        if (st != null) st.close();
        if (conn != null) conn.close();
    }
}
