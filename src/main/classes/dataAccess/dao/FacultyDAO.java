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
    public Faculty get(long id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;";
            st = conn.prepareStatement(sql);

            st.setLong(1, id);

            st.execute();

            rs = st.getResultSet();

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

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {

                if (st != null) {
                    st.close();
                }

                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Faculty> getAll() {

        Statement st = null;
        ResultSet rs = null;

        Map<Long, Faculty> selectionRoundIdToFacultyMap = new HashMap<>();

        try {

            String sql = "SELECT * FROM " + TABLE_NAME + ";";
            st = conn.createStatement();

            st.execute(sql);
            rs = st.getResultSet();

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

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {

                if (st != null) {
                    st.close();
                }

                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
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
