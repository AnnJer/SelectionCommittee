package dataAccess.dao;

import selectionCommittee.Subject;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO implements DAO<Subject>, Closeable {


    private Connection conn;
    static final String TABLE_NAME = "subjects";


    public SubjectDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Subject get(long id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";
            st = conn.prepareStatement(sql);

            st.setLong(1, id);

            st.execute();

            rs = st.getResultSet();

            rs.next();
            return parseFromResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {

                if (st != null) {
                    st.close();
                }

                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Subject> getAll() {

        Statement st = null;
        ResultSet rs = null;


        try {

            String sql = "SELECT * FROM " + TABLE_NAME + ";";
            st = conn.createStatement();

            st.execute(sql);
            rs = st.getResultSet();

            List<Subject> subjects = new ArrayList<>();

            while(rs.next()) {
                subjects.add(parseFromResultSet(rs));
            }

            return subjects;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {

                if (st != null) {
                    st.close();
                }

                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Subject save(Subject subject) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (id, label) VALUES (default, ?) RETURNING id;";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, subject.getLabel());

        st.execute();

        ResultSet rs = st.getResultSet();

        rs.next();

        subject.setId(rs.getLong("id"));

        rs.close();
        st.close();

        return subject;
    }

    @Override
    public void update(Subject subject) throws SQLException {

        if (subject.getId() == null) {
            throw new NullPointerException("ID was not defined.");
        }

        String sql = "UPDATE " + TABLE_NAME + " SET label = ? WHERE id = ?;";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, subject.getLabel());
        st.setLong(2, subject.getId());

        st.executeUpdate();

        st.close();
    }

    @Override
    public void delete(Subject subject) throws SQLException {

        if (subject.getId() == null) {
            throw new NullPointerException("ID was not defined.");
        }

        Utils.deleteById(subject.getId(), TABLE_NAME, conn);
    }



    private Subject parseFromResultSet(ResultSet rs) throws SQLException {
        String label = rs.getString("label");
        long id = rs.getLong("id");

        return new Subject(id, label);
    }

    @Override
    public void close() throws IOException {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
