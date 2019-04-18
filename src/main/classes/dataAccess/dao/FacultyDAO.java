package dataAccess.dao;

import dataAccess.DBAccessFactory;
import selectionCommittee.Faculty;
import selectionCommittee.SelectionRound;
import selectionCommittee.factories.ApplicationManagerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacultyDAO implements DAO<Faculty>, Closeable {



    private Connection conn;

    private ApplicationManagerFactory applicationManagerFactory;

    static final String TABLE_NAME = "faculties";

    public FacultyDAO(Connection conn, ApplicationManagerFactory applicationManagerFactory) {
        this.conn = conn;
        this.applicationManagerFactory = applicationManagerFactory;
    }

    @Override
    public Faculty get(long id) throws Exception {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> st1.setLong(1, id)
                );

                ResultSet rs = st.executeQuery()
                ){


            if (!rs.next()) {
                return null;
            }

            long idSelectionRound = rs.getLong("id_selection_round");

            Faculty faculty = parseFromResultSet(rs);

            try (
                    SelectionRoundDAO selectionRoundDAO = DBAccessFactory.getInstance()
                            .getDAOFactory()
                            .getSelectionRoundDAO()
                    ) {
                faculty.setSelectionRound(selectionRoundDAO.get(idSelectionRound));
            }

            return faculty;

        }
    }

    @Override
    public List<Faculty> getAll() throws Exception {

        String sql = "SELECT * FROM " + TABLE_NAME + ";";

        Map<Long, Faculty> selectionRoundIdToFacultyMap = new HashMap<>();

        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)
                ){

            while(rs.next()) {
                long idSelectionRound = rs.getLong("id_selection_round");

                Faculty faculty = parseFromResultSet(rs);

                selectionRoundIdToFacultyMap.put(idSelectionRound, faculty);
            }

            if (selectionRoundIdToFacultyMap.size() > 0) {

                List<SelectionRound> selectionRounds;

                try (
                        SelectionRoundDAO selectionRoundDAO = DBAccessFactory.getInstance()
                                .getDAOFactory()
                                .getSelectionRoundDAO()
                ) {
                    selectionRounds = selectionRoundDAO.getByIdList(
                            new ArrayList<>(selectionRoundIdToFacultyMap.keySet())
                    );
                }


                for (SelectionRound selectionRound: selectionRounds
                ) {
                    selectionRoundIdToFacultyMap.get(selectionRound.getId()).setSelectionRound(selectionRound);
                }

            }

            return new ArrayList<>(selectionRoundIdToFacultyMap.values());

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
