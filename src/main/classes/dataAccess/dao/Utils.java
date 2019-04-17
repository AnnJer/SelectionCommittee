package dataAccess.dao;

import java.sql.*;

public class Utils {


    static ResultSet getById(long id, String table, Connection conn) throws SQLException {
        String sql = "SELECT * FROM " + table + " WHERE id = ?;";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setLong(1, id);

        st.execute();
//        st.close();
        ResultSet rs = st.getResultSet();

        return rs;
    }

    static ResultSet getAll(String table, Connection conn) throws SQLException {
        String sql = "SELECT * FROM " + table + ";";
        Statement st = conn.createStatement();

        st.execute(sql);
        ResultSet rs = st.getResultSet();
//        st.close();

        return rs;
    }


    static void deleteById(long id, String table, Connection conn) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE id = ?;";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setLong(1, id);

        st.executeUpdate();

        st.close();
    }


    static void deleteCascadeById(long id, String table, Connection conn) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE id = ? CASCADE;";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setLong(1, id);

        st.executeUpdate();

        st.close();
    }

}
