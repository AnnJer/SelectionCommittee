package dataAccess.dao;

import java.sql.*;

class Utils {


    static PreparedStatement getPreparedStatement(Connection conn, String sql, StatementCompiler compiler) throws SQLException {

        PreparedStatement st = conn.prepareStatement(sql);

        compiler.prepare(st);

        return st;
    }

    static ResultSet getById(long id, String table, Connection conn) throws SQLException {
        String sql = "SELECT * FROM " + table + " WHERE id = ?;";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setLong(1, id);

        st.execute();
//        st.close();
        ResultSet rs = st.getResultSet();

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
