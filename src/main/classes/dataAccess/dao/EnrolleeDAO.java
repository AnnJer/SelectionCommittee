package dataAccess.dao;

import dataAccess.DBAccessFactory;
import rateFactors.RateFactorResult;
import rateFactors.factories.RateFactorsFactory;
import rateFactors.types.SchoolCertificateType;
import user.Enrollee;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class EnrolleeDAO implements DAO<Enrollee>, Closeable {


    private Connection conn;
    static final String SUPER_TABLE_NAME = "users";
    static final String TABLE_NAME = "enrollee";


    public EnrolleeDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Enrollee get(long id) {
        try {

            String sql = "SELECT * FROM " + TABLE_NAME +
                    " INNER JOIN " + SUPER_TABLE_NAME +
                    " ON " + SUPER_TABLE_NAME + ".id = " + TABLE_NAME + ".id_user " +
                    " WHERE id = ?;";

            PreparedStatement st = conn.prepareStatement(sql);

            st.setLong(1, id);
            st.execute();


            ResultSet rs = st.getResultSet();

            if (!rs.next()) {
                return null;
            }

            Enrollee enrollee = parseFromResultSet(rs);

            List<RateFactorResult> results;

            try (
            RateFactorResultDAO rateFactorResultDAO = DBAccessFactory.getInstance()
                    .getDAOFactory()
                    .getRateFactorResultDAO()
            ) {
                results = rateFactorResultDAO.getByEnrollee(enrollee);
            }


            enrollee.setSchoolCertificate(getAndRemoveFromListSchoolCertificateResult(results));
            enrollee.setExamResults(results);

            return enrollee;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Enrollee getByLoginAndPassword(String login, String password) {

        try {

            String sql = "SELECT u.*, r.id AS r_id, r.result, r.type FROM " + TABLE_NAME + " AS e " +
                    "INNER JOIN " + SUPER_TABLE_NAME + " AS u " +
                    " ON u.id = e.id_user " +
                    "LEFT JOIN " + RateFactorResultDAO.TABLE_NAME + " AS r ON r.id_user = e.id_user " +
                    "WHERE u.login = ? AND u.password = ?;";


            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, login);
            st.setString(2, password);

            st.execute();

            ResultSet rs = st.getResultSet();

            List<Enrollee> enrollees = getEnrolleeListWithResultFactors(rs);

            if (enrollees.size() < 1) {
                return null;
            }

            return enrollees.get(0);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
    public List<Enrollee> getAll() {
        try {

            String sql = "SELECT u.*, r.id AS r_id, r.result, r.type FROM " + TABLE_NAME + " AS e " +
                    "INNER JOIN " + SUPER_TABLE_NAME + " AS u " +
                    " ON u.id = e.id_user " +
                    "LEFT JOIN " + RateFactorResultDAO.TABLE_NAME + " AS r ON r.id_user = e.id_user;";


            Statement st = conn.createStatement();

            st.execute(sql);

            ResultSet rs = st.getResultSet();

            return getEnrolleeListWithResultFactors(rs);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    List<Enrollee> getEnrolleeListWithResultFactors(ResultSet rs) throws SQLException {

        Map<Long, Enrollee> enrolleeList = new HashMap<>();
        Map<Long, List<RateFactorResult>> results = new HashMap<>();

        while(rs.next()) {

            long id = rs.getLong("id");

            if (!enrolleeList.containsKey(id)) {
                enrolleeList.put(id, parseFromResultSet(rs));
                results.put(id, new ArrayList<>());
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



        for (Long id: enrolleeList.keySet()) {
            enrolleeList.get(id).setSchoolCertificate(
                    getAndRemoveFromListSchoolCertificateResult(results.get(id))
            );

            enrolleeList.get(id).setExamResults(results.get(id));
        }


        return new ArrayList<>(enrolleeList.values());
    }





    /*
        WARNING: IT DOESNT SAVE RATE FACTORS (@see table rate_factor_results);
     */
    @Override
    public Enrollee save(Enrollee enrollee) throws Exception {

        String sql = "INSERT INTO " + SUPER_TABLE_NAME +
                " (id, name, surname, lastname, login, password) VALUES (default, ?, ?, ?, ?, ?) RETURNING id;";


        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, enrollee.getName());
        st.setString(2, enrollee.getSurname());
        st.setString(3, enrollee.getLastname());
        st.setString(4, enrollee.getLogin());
        st.setBytes(5, enrollee.getPassword());

        st.execute();

        ResultSet rs = st.getResultSet();

        if (!rs.next()) {
            return null;
        }

        enrollee.setId(rs.getLong("id"));

        sql = "INSERT INTO " + TABLE_NAME +
                " (id, id_user) VALUES (?);";

        st = conn.prepareStatement(sql);
        st.setLong(1, enrollee.getId());

        return enrollee;
    }

    @Override
    public void update(Enrollee enrollee) throws Exception {


        if (enrollee.getId() == null) {
            throw new NullPointerException("ID was not defined.");
        }

        String sql = "UPDATE " + SUPER_TABLE_NAME + " SET name = ?, surname = ?, lastname = ? WHERE id = ?;";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, enrollee.getName());
        st.setString(2, enrollee.getSurname());
        st.setString(3, enrollee.getLastname());
        st.setLong(4, enrollee.getId());

        st.executeUpdate();
    }

    @Override
    public void delete(Enrollee enrollee) throws Exception {

        if (enrollee.getId() == null) {
            throw new NullPointerException("ID was not defined.");
        }

        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?;";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setLong(1, enrollee.getId());

        st.executeUpdate();
    }

    private Enrollee parseFromResultSet(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String lastname = rs.getString("lastname");

        long id = rs.getLong("id");

        String login = rs.getString("login");

        return new Enrollee(id, name, lastname, surname, login);
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
