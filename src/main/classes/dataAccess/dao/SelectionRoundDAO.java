package dataAccess.dao;

import rateFactors.RateFactorCoefficient;
import rateFactors.factories.RateFactorsFactory;
import selectionCommittee.SelectionRound;
import selectionCommittee.factories.SelectionRoundFactory;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class SelectionRoundDAO implements DAO<SelectionRound>, Closeable {



    private Connection conn;

    private SelectionRoundFactory selectionRoundFactory;
    private RateFactorsFactory rateFactorsFactory;

    static final String TABLE_NAME = "selection_rounds";
    static final String COEFFICIENTS_TABLE_NAME = "rate_factor_coefficients";


    public SelectionRoundDAO(Connection conn, SelectionRoundFactory factory, RateFactorsFactory rateFactorsFactory) {
        this.selectionRoundFactory = factory;
        this.rateFactorsFactory = rateFactorsFactory;
        this.conn = conn;
    }

    @Override
    public SelectionRound get(long id) {

        String sql = "SELECT s.*, c.id AS c_id, c.coefficient, c.type FROM " + TABLE_NAME + " AS s " +
                "LEFT JOIN " + COEFFICIENTS_TABLE_NAME + " AS c " +
                "ON s.id = c.id_selection_round " +
                "WHERE s.id = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> st1.setLong(1, id)
                );

                ResultSet rs = st.executeQuery()
                ) {

            List<SelectionRound> selectionRounds = getSelectionRoundsWithResultFactors(rs);

            if (selectionRounds.size() < 1) {
                return null;
            }

            return selectionRounds.get(0);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public List<SelectionRound> getAll() throws SQLException {

        String sql = "SELECT s.*, c.id AS c_id, c.coefficient, c.type FROM " + TABLE_NAME + " AS s " +
                "LEFT JOIN " + COEFFICIENTS_TABLE_NAME + " AS c " +
                "ON s.id = c.id_selection_round;";

        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)
                ) {

            return getSelectionRoundsWithResultFactors(rs);

        }
    }


    public List<SelectionRound> getByIdList (List<Long> ids) throws SQLException {

        String sql = "SELECT s.*, c.id AS c_id, c.coefficient, c.type FROM " + TABLE_NAME + " AS s " +
                "LEFT JOIN " + COEFFICIENTS_TABLE_NAME + " AS c " +
                "ON s.id = c.id_selection_round WHERE s.id IN (";

        sql += ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        sql += ");";

        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)
                ) {

            return getSelectionRoundsWithResultFactors(rs);

        }
    }


    @Override
    public SelectionRound save(SelectionRound selectionRound) throws Exception {

        String sql = "INSERT INTO "
                     + TABLE_NAME
                     + " (id, selection_plan, start_date, end_date) VALUES (default, ?, ?, ?) RETURNING id;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> {
                            st1.setInt(1, selectionRound.getSelectionPlan());
                            st1.setDate(2, (java.sql.Date)selectionRound.getStartDate());
                            st1.setDate(3, (java.sql.Date)selectionRound.getEndDate());
                        }
                );

                ResultSet rs = st.executeQuery()
                ) {

            rs.next();

            selectionRound.setId(rs.getLong("id"));

            return selectionRound;
        }
    }

    @Override
    public void update(SelectionRound selectionRound) throws Exception {

        if (selectionRound.getId() == null) {
            throw new NullPointerException("ID was not defined.");
        }

        String sql = "UPDATE " + TABLE_NAME + " SET selection_plan = ?, start_date = ?, end_date = ?  WHERE id = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> {
                            st1.setInt(1, selectionRound.getSelectionPlan());
                            st1.setDate(2, (java.sql.Date)selectionRound.getStartDate());
                            st1.setDate(3, (java.sql.Date)selectionRound.getEndDate());
                            st1.setLong(4, selectionRound.getId());
                        }
                )
                ) {
            st.executeUpdate();
        }

    }

    @Override
    public void delete(SelectionRound selectionRound) throws Exception {

        if (selectionRound.getId() == null) {
            throw new NullPointerException("ID was not defined.");
        }

        Utils.deleteCascadeById(selectionRound.getId(), TABLE_NAME, conn);


    }

    List<SelectionRound> getSelectionRoundsWithResultFactors(ResultSet rs) throws SQLException {

        Map<Long, SelectionRound> selectionRounds = new HashMap<>();
        Map<Long, List<RateFactorCoefficient>> coefficients = new HashMap<>();

        while(rs.next()) {

            long id = rs.getLong("id");

            if (!selectionRounds.containsKey(id)) {
                selectionRounds.put(id, parseFromResultSet(rs));
                coefficients.put(id, new ArrayList<>());
            }


            float result = rs.getFloat("coefficient");
            String type = rs.getString("type");
            long coefficientId = rs.getLong("c_id");

            if (type == null) {
                continue;
            }

            RateFactorCoefficient rateFactorCoefficient = rateFactorsFactory.createCoefficient(result, type);
            rateFactorCoefficient.setId(coefficientId);

            coefficients.get(id).add(rateFactorCoefficient);
        }



        for (Long id: selectionRounds.keySet()) {
            SelectionRound sr = selectionRounds.get(id);

            sr = selectionRoundFactory.createSelectionRound(
                    sr.getSelectionPlan(), sr.getStartDate(), sr.getEndDate(), coefficients.get(id)
            );

            sr.setId(id);

            selectionRounds.replace(
                    id,
                    sr
            );
        }


        return new ArrayList<>(selectionRounds.values());
    }



    private SelectionRound parseFromResultSet(ResultSet rs) throws SQLException {

        int selectionPlan = rs.getInt("selection_plan");
        long id = rs.getLong("id");
        Date startDate =  rs.getDate("start_date");
        Date endDate = rs.getDate("end_date");


        return selectionRoundFactory.createSelectionRound(selectionPlan, startDate, endDate, null);
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
