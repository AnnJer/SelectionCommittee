package dataAccess.dao;

import auth.Session;

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
        return null;
    }

    @Override
    public List<Session> getAll() {
        return null;
    }


    public Session getByToken(String token) {
        try {

            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE token = ? AND NOW() < end_date;";

            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, token);


            st.execute();

            ResultSet rs = st.getResultSet();

            rs.next();
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


        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, session.getToken());
        st.setString(2, session.getType());
        st.setLong(3, session.getUserId());


        st.executeUpdate();

        return session;
    }


    @Override
    public void update(Session session) throws Exception {

    }


    public void deleteByToken(String token) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE token = ?";

        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, token);

        st.executeUpdate();

        st.close();
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
