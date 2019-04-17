package dataAccess.dao;

import rateFactors.RateFactorResult;
import rateFactors.factories.RateFactorsFactory;
import user.Enrollee;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        PreparedStatement st = conn.prepareStatement(sql);

        st.setLong(1, enrollee.getId());
        st.execute();

        ResultSet rs = st.getResultSet();


        List<RateFactorResult> rateFactorResults = new ArrayList<>();

        while(rs.next()) {
            rateFactorResults.add(parseFromResultSet(rs));
        }

        return rateFactorResults;

    }


    @Override
    public RateFactorResult get(long id) {
        try {

            ResultSet rs = Utils.getById(id, TABLE_NAME, conn);

            rs.next();
            return parseFromResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RateFactorResult> getAll() {
        try {

            ResultSet rs = Utils.getAll(TABLE_NAME, conn);

            List<RateFactorResult> rateFactorResults = new ArrayList<>();

            while(rs.next()) {
                rateFactorResults.add(parseFromResultSet(rs));
            }

            return rateFactorResults;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RateFactorResult save(RateFactorResult rateFactorResult) throws Exception {

        String sql = "INSERT INTO " + TABLE_NAME + " (id, result, type) VALUES (default, ?, ?) RETURNING id;";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setFloat(1, rateFactorResult.getResult());
        st.setString(2, rateFactorResult.getType().getType());

        st.execute();

        ResultSet rs = st.getResultSet();

        rs.next();
        rateFactorResult.setId(rs.getLong("id"));

        return rateFactorResult;
    }

    @Override
    public void update(RateFactorResult rateFactorResult) throws Exception {

        if (rateFactorResult.getId() == null) {
            throw new NullPointerException("ID was not defined.");
        }

        String sql = "UPDATE " + TABLE_NAME + " SET result = ? WHERE id = ?;";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setFloat(1, rateFactorResult.getResult());
        st.setLong(2, rateFactorResult.getId());

        st.executeUpdate();
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
        PreparedStatement st = conn.prepareStatement(sql);

        st.setLong(1, id);

        st.execute();
    }

    public void batchInsert(List<RateFactorResult> results, long userId) throws Exception {
        StringBuilder sql = new StringBuilder("INSERT INTO " + TABLE_NAME + "(result, type, id_user) VALUES ");

        for (int i = 0; i < results.size(); i++) {
            sql.append("(?, ?, ?)");

            if (i < results.size() - 1) {
                sql.append(", ");
            } else {
                sql.append(";");
            }
        }

        PreparedStatement st = conn.prepareStatement(sql.toString());

        int i = 0;
        for (RateFactorResult rateFactorResult: results) {
            st.setFloat(++i, rateFactorResult.getResult());
            st.setString(++i, rateFactorResult.getType().getType());
            st.setLong(++i, userId);
        }

        st.executeUpdate();
        st.close();
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
