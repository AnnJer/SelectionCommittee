package dataAccess.DAO;

import dataAccess.DBAccessFactory;
import rateFactors.factories.RateFactorsFactory;
import university.ApplicationManager;
import university.Faculty;
import university.SelectionRound;
import university.factories.ApplicationManagerFactory;
import university.factories.SelectionRoundFactory;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacultyDAO implements DAO<Faculty>, Closeable {



    private Connection conn;

    private ApplicationManagerFactory applicationManagerFactory;

    static final String TABLE_NAME = "selection_rounds";

    public FacultyDAO(Connection conn, ApplicationManagerFactory applicationManagerFactory) {
        this.conn = conn;
        this.applicationManagerFactory = applicationManagerFactory;
    }

    @Override
    public Faculty get(long id) {

        try {

            ResultSet rs = Utils.getById(id, TABLE_NAME, conn);

            rs.next();

            long idSelectionRound = rs.getLong("id_selection_round");

            Faculty faculty = parseFromResultSet(rs);

            faculty.setSelectionRound(DBAccessFactory.getInstance()
                                                     .getDAOFactory()
                                                     .getSelectionRoundDAO()
                                                     .get(idSelectionRound)
            );

            return faculty;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Faculty> getAll() {

        Map<Long, Faculty> selectionRoundIdToFacultyMap = new HashMap<>();

        try {

            ResultSet rs = Utils.getAll(TABLE_NAME, conn);

            while(rs.next()) {
                long idSelectionRound = rs.getLong("id_selection_round");

                Faculty faculty = parseFromResultSet(rs);

                selectionRoundIdToFacultyMap.put(idSelectionRound, faculty);
            }


            List<SelectionRound> selectionRounds = DBAccessFactory.getInstance()
                    .getDAOFactory()
                    .getSelectionRoundDAO()
                    .getByIdList(new ArrayList<>(selectionRoundIdToFacultyMap.keySet()));


            for (SelectionRound selectionRound: selectionRounds
                 ) {
                selectionRoundIdToFacultyMap.get(selectionRound.getId()).setSelectionRound(selectionRound);
            }

            return new ArrayList<>(selectionRoundIdToFacultyMap.values());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Faculty save(Faculty faculty) throws Exception {
        // TODO: implement save
        return null;
    }

    @Override
    public void update(Faculty faculty) throws Exception {
        // TODO: implement update
    }

    @Override
    public void delete(Faculty faculty) throws Exception {
        // TODO: implement delete
    }


    protected Faculty parseFromResultSet(ResultSet rs) throws SQLException {

        String label = rs.getString("label");
        Long id = rs.getLong("id");

        return new Faculty(id, label, null, applicationManagerFactory.createApplicationManager());
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
