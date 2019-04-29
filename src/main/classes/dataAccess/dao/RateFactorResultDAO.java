package dataAccess.dao;

import rateFactors.RateFactorResult;
import rateFactors.factories.RateFactorsFactory;
import user.Enrollee;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RateFactorResultDAO implements DAO<RateFactorResult>, Closeable {


    private Connection conn;
    private RateFactorsFactory rateFactorsFactory;
    static final String TABLE_NAME = "rate_factor_results";


    public RateFactorResultDAO(Connection conn, RateFactorsFactory factorsFactory) {
        this.rateFactorsFactory = factorsFactory;
        this.conn = conn;
    }



    public List<RateFactorResult> getByEnrollee(Enrollee enrollee) throws SQLException {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_user = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> st1.setLong(1, enrollee.getId())
                );

                ResultSet rs = st.executeQuery()
                ){
            List<RateFactorResult> rateFactorResults = new LinkedList<>();

            while(rs.next()) {
                rateFactorResults.add(parseFromResultSet(rs));
            }

            return rateFactorResults;
        }

    }


    @Override
    public RateFactorResult get(long id) {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> st1.setLong(1, id)
                );

                ResultSet rs = st.executeQuery()
                ){

            rs.next();
            return parseFromResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RateFactorResult> getAll() throws SQLException {

        String sql = "SELECT * FROM " + TABLE_NAME + ";";

        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)
                ){

            List<RateFactorResult> rateFactorResults = new LinkedList<>();

            while(rs.next()) {
                rateFactorResults.add(parseFromResultSet(rs));
            }

            return rateFactorResults;

        }
    }

    @Override
    public RateFactorResult save(RateFactorResult rateFactorResult) throws Exception {

        String sql = "INSERT INTO " + TABLE_NAME + " (id, result, type) VALUES (default, ?, ?) RETURNING id;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> {
                            st1.setFloat(1, rateFactorResult.getResult());
                            st1.setString(2, rateFactorResult.getType().getType());
                        }
                );

                ResultSet rs = st.executeQuery()
                ) {
            rs.next();
            rateFactorResult.setId(rs.getLong("id"));

            return rateFactorResult;
        }

    }

    @Override
    public void update(RateFactorResult rateFactorResult) throws Exception {

        if (rateFactorResult.getId() == null) {
            throw new NullPointerException("ID was not defined.");
        }

        String sql = "UPDATE " + TABLE_NAME + " SET result = ? WHERE id = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> {
                            st1.setFloat(1, rateFactorResult.getResult());
                            st1.setLong(2, rateFactorResult.getId());
                        }
                )
                ) {
            st.executeUpdate();
        }
    }

    @Override
    public void delete(RateFactorResult rateFactorResult) throws Exception {

        if (rateFactorResult.getId() == null) {
            throw new NullPointerException("ID was not defined.");
        }

        Utils.deleteById(rateFactorResult.getId(), TABLE_NAME, conn);
    }


    public void deleteByEnrolleId(long id) throws Exception {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id_user = ?";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> st1.setLong(1, id)
                )
                ) {
            st.executeUpdate();
        }
    }

    public void saveAll(List<RateFactorResult> results, long userId) throws Exception {

        StringBuilder sql = new StringBuilder("INSERT INTO " + TABLE_NAME + "(result, type, id_user) VALUES ");

        for (int i = 0; i < results.size(); i++) {
            sql.append("(?, ?, ?)");

            if (i < results.size() - 1) {
                sql.append(", ");
            } else {
                sql.append(";");
            }
        }

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql.toString(), (PreparedStatement st1) -> {
                            int i = 0;
                            for (RateFactorResult rateFactorResult: results) {
                                st1.setFloat(++i, rateFactorResult.getResult());
                                st1.setString(++i, rateFactorResult.getType().getType());
                                st1.setLong(++i, userId);
                            }
                        }
                )
                ) {
            st.executeUpdate();
        }
    }

    private RateFactorResult parseFromResultSet(ResultSet rs) throws SQLException {
        float result = rs.getFloat("result");
        String type = rs.getString("type");
        long id = rs.getLong("id");

        RateFactorResult rateFactorResult = rateFactorsFactory.createResult(result, type);
        rateFactorResult.setId(id);

        return rateFactorResult;
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
