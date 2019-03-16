package dataAccess.DAO;

import java.sql.*;

public class Utils {


    static ResultSet getById(long id, String table, Connection conn) throws SQLException {
        String sql = "SELECT * FROM " + table + " WHERE id = ?;";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setLong(1, id);
        st.execute();

        return st.getResultSet();
    }

    static ResultSet getAll(String table, Connection conn) throws SQLException {
        String sql = "SELECT * FROM " + table + ";";
        Statement st = conn.createStatement();

        st.execute(sql);
        return st.getResultSet();
    }


    static void deleteById(long id, String table, Connection conn) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE id = ?;";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setLong(1, id);

        st.executeUpdate();
    }


    static void deleteCasadeById(long id, String table, Connection conn) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE id = ? CASCADE;";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setLong(1, id);

        st.executeUpdate();
    }

}
