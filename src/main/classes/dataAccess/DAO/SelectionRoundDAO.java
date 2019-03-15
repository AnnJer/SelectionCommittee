package dataAccess.DAO;

import rateFactors.RateFactorCoefficient;
import rateFactors.factories.RateFactorsFactory;
import university.SelectionRound;
import university.factories.SelectionRoundFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectionRoundDAO implements DAO<SelectionRound> {



    private Connection conn;

    private SelectionRoundFactory factory;

    static final String TABLE_NAME = "selection_rounds";
    static final String COEFFICIENTS_TABLE_NAME = "rate_factor_coefficients";


    public SelectionRoundDAO(Connection conn, SelectionRoundFactory factory) {
        this.factory = factory;
        this.conn = conn;
    }

    @Override
    public SelectionRound get(long id) {
        try {

            String sql = "SELECT * FROM " + TABLE_NAME + " AS s " +
                    "INNER JOIN " + COEFFICIENTS_TABLE_NAME + " as c " +
                    "ON s.id = c.id_user " +
                    "WHERE id = ?;";

            PreparedStatement st = conn.prepareStatement(sql);

            st.setLong(1, id);
            st.execute();


            ResultSet rs = st.getResultSet();

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
    public List<SelectionRound> getAll() {
        return null;
    }

    @Override
    public SelectionRound save(SelectionRound selectionRound) throws Exception {
        return null;
    }

    @Override
    public void update(SelectionRound selectionRound) throws Exception {

    }

    @Override
    public void delete(SelectionRound selectionRound) throws Exception {

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


            float result = rs.getFloat("result");
            String type = rs.getString("type");
            long coefficientId = rs.getLong("c_id");

            RateFactorCoefficient rateFactorCoefficient = RateFactorsFactory.getInstance()
                                                                            .createCoefficient(result, type);
            rateFactorCoefficient.setId(coefficientId);

            coefficients.get(id).add(rateFactorCoefficient);
        }



        for (Long id: selectionRounds.keySet()) {
            SelectionRound sr = selectionRounds.get(id);

            sr = factory.createSelectionRound(
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
        // TODO: implements this sheet.
        return null;
    }

}
