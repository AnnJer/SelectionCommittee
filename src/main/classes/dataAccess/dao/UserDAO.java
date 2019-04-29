package dataAccess.dao;

import rateFactors.RateFactorResult;
import rateFactors.factories.RateFactorsFactory;
import rateFactors.types.SchoolCertificateType;
import user.Enrollee;
import user.User;
import user.UserFactory;
import user.UserRoles;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class UserDAO implements DAO<User>, Closeable {


    private Connection conn;
    static final String TABLE_NAME = "users";
    static final String RATE_FACTORS_TABLE_NAME = "rate_factor_results";


    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public User get(long id) throws SQLException {

        String sql = "SELECT u.*, r.id AS r_id, r.result, r.type FROM " + TABLE_NAME + " AS u " +
                "LEFT JOIN " + RATE_FACTORS_TABLE_NAME + " AS r " +
                "ON u.id = r.id_user " +
                "WHERE u.id = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> st1.setLong(1, id)
                );

                ResultSet rs = st.executeQuery()
                ) {

            List<User> users = getUsersListWithResultFactors(rs);

            return users.size() > 0 ? users.get(0) : null;

        }
    }


    public User getByLoginAndPassword(String login, String password) throws SQLException {

        String sql = "SELECT u.*, r.id AS r_id, r.result, r.type FROM " + TABLE_NAME + " AS u " +
                "LEFT JOIN " + RateFactorResultDAO.TABLE_NAME + " AS r ON r.id_user = u.id " +
                "WHERE u.login = ? AND u.password = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> {
                            st1.setString(1, login);
                            st1.setString(2, password);
                        }
                );

                ResultSet rs = st.executeQuery()
                ) {

            List<User> users = getUsersListWithResultFactors(rs);

            if (users.size() < 1) {
                return null;
            }

            return users.get(0);

        }
    }


    private RateFactorResult getAndRemoveFromListSchoolCertificateResult(List<RateFactorResult> results) {
        Iterator<RateFactorResult> i = results.iterator();

        while( i.hasNext() ) {

            RateFactorResult result = i.next();

            if (result.getType().getType().equals(SchoolCertificateType.TYPE_PREFIX)) {
                i.remove();
                return result;
            }
        }

        return null;
    }


    @Override
    public List<User> getAll() throws SQLException {

        String sql = "SELECT u.*, r.id AS r_id, r.result, r.type FROM " + TABLE_NAME + " AS u " +
                "LEFT JOIN " + RATE_FACTORS_TABLE_NAME + " AS r ON r.id_user = u.id;";

        try (
               Statement st = conn.createStatement();
               ResultSet rs = st.executeQuery(sql)
                ) {

            return getUsersListWithResultFactors(rs);

        }
    }



    List<User> getUsersListWithResultFactors(ResultSet rs) throws SQLException {

        Map<Long, User> userHashMapList = new HashMap<>();
        Map<Long, List<RateFactorResult>> results = new HashMap<>();

        while(rs.next()) {

            long id = rs.getLong("id");

            if (!userHashMapList.containsKey(id)) {
                userHashMapList.put(id, parseFromResultSet(rs));
                results.put(id, new LinkedList<>());
            }


            float result = rs.getFloat("result");
            String type = rs.getString("type");
            long resultId = rs.getLong("r_id");

            if (type == null) {
                continue;
            }

            RateFactorResult rateFactorResult = RateFactorsFactory.getInstance().createResult(result, type);
            rateFactorResult.setId(resultId);

            results.get(id).add(rateFactorResult);
        }



        for (Long id: userHashMapList.keySet()) {

            if (userHashMapList.get(id).getRole() == UserRoles.ADMINISTRATOR) {
                continue;
            }

            Enrollee enrollee = (Enrollee) userHashMapList.get(id);


            enrollee.setSchoolCertificate(
                    getAndRemoveFromListSchoolCertificateResult(results.get(id))
            );

            enrollee.setExamResults(results.get(id));
        }


        return new ArrayList<>(userHashMapList.values());
    }





    /*
        WARNING: IT DOESNT SAVE RATE FACTORS (@see table rate_factor_results);
     */
    @Override
    public User save(User user) throws Exception {

        String sql = "INSERT INTO " + TABLE_NAME +
                " (name, surname, lastname, login, password, role) VALUES (?, ?, ?, ?, ?, ?) RETURNING id;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> {
                            st1.setString(1, user.getName());
                            st1.setString(2, user.getSurname());
                            st1.setString(3, user.getLastname());
                            st1.setString(4, user.getLogin());
                            st1.setBytes(5, user.getPassword());
                            st1.setString(5, user.getRole().toString());
                        }
                );

                ResultSet rs = st.executeQuery()
                ) {

            if (!rs.next()) {
                return null;
            }

            user.setId(rs.getLong("id"));

            return user;
        }
    }

    @Override
    public void update(User user) throws Exception {


        if (user.getId() == null) {
            throw new NullPointerException("ID was not defined.");
        }

        String sql = "UPDATE " + TABLE_NAME + " SET name = ?, surname = ?, lastname = ? WHERE id = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> {
                            st1.setString(1, user.getName());
                            st1.setString(2, user.getSurname());
                            st1.setString(3, user.getLastname());
                            st1.setLong(4, user.getId());
                        }
                );
        ) {
            st.executeUpdate();
        }
    }

    @Override
    public void delete(User user) throws Exception {

        if (user.getId() == null) {
            throw new NullPointerException("ID was not defined.");
        }

        Utils.deleteById(user.getId(), TABLE_NAME, conn);
    }

    private User parseFromResultSet(ResultSet rs) throws SQLException {

        long id = rs.getLong("id");

        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String lastname = rs.getString("lastname");


        String login = rs.getString("login");

        String role = rs.getString("role");


        return UserFactory.getInstance().createUser(id, name, lastname, surname, login, role);
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
