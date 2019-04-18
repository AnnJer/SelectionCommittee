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
    public Subject get(long id) throws SQLException {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> st1.setLong(1, id)
                );

                ResultSet rs = st.executeQuery()
                ) {

            rs.next();
            return parseFromResultSet(rs);

        }
    }

    @Override
    public List<Subject> getAll() throws SQLException {

        String sql = "SELECT * FROM " + TABLE_NAME + ";";

        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)
                ) {

            List<Subject> subjects = new ArrayList<>();

            while(rs.next()) {
                subjects.add(parseFromResultSet(rs));
            }

            return subjects;

        }
    }

    @Override
    public Subject save(Subject subject) throws SQLException {

        String sql = "INSERT INTO " + TABLE_NAME + " (id, label) VALUES (default, ?) RETURNING id;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> st1.setString(1, subject.getLabel())
                );

                ResultSet rs = st.executeQuery()
                ) {

            rs.next();

            subject.setId(rs.getLong("id"));

            return subject;
        }
    }

    @Override
    public void update(Subject subject) throws SQLException {

        if (subject.getId() == null) {
            throw new NullPointerException("ID was not defined.");
        }

        String sql = "UPDATE " + TABLE_NAME + " SET label = ? WHERE id = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> {
                            st1.setString(1, subject.getLabel());
                            st1.setLong(2, subject.getId());
                        }
                )
                ) {
            st.executeUpdate();
        }
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
