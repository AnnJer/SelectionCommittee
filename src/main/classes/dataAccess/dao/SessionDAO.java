package dataAccess.dao;

import auth.Session;
import jdk.jshell.spi.ExecutionControl;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SessionDAO implements DAO<Session>, Closeable {


    private Connection conn;
    static final String TABLE_NAME = "sessions";


    public SessionDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Session get(long id) {
        // TODO: implement get
        return null;
    }

    @Override
    public List<Session> getAll() {
        // TODO: implement getAll
        return null;
    }


    public Session getByToken(String token) {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE token = ? AND NOW() < end_date;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> st1.setString(1, token)
                );

                ResultSet rs = st.executeQuery()
                ) {

            if (!rs.next()) {
                return null;
            }

            return parseFromResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Session save(Session session) throws Exception {

        String sql = "INSERT INTO " + TABLE_NAME +
                " (token, type, id_user, start_date, end_date) VALUES (?, ?, ?, NOW(), NOW() + INTERVAL '3 DAY');";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> {
                            st1.setString(1, session.getToken());
                            st1.setString(2, session.getType());
                            st1.setLong(3, session.getUserId());
                        }
                )
                ) {

            st.executeUpdate();
            return session;
        }
    }


    @Override
    public void update(Session session) throws Exception {
        throw new ExecutionControl.NotImplementedException("Session are immutable");
    }


    public void deleteByToken(String token) throws Exception {

        String sql = "DELETE FROM " + TABLE_NAME + " WHERE token = ?";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> st1.setString(1, token)
                )
                ) {
            st.executeUpdate();
        }
    }


    @Override
    public void delete(Session session) throws Exception {
        deleteByToken(session.getToken());
    }

    private Session parseFromResultSet(ResultSet rs) throws SQLException {

        long userId = rs.getLong("id_user");
        String token = rs.getString("token");
        String type = rs.getString("type");

        return Session.createSession(userId, token, type);
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
