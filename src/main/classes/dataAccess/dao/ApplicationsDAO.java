package dataAccess.dao;

import jdk.jshell.spi.ExecutionControl;
import selectionCommittee.Application;
import selectionCommittee.Faculty;
import selectionCommittee.SelectionRound;
import selectionCommittee.factories.SelectionRoundFactory;
import user.Enrollee;
import user.User;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationsDAO implements DAO<Application>, Closeable {


    static final String TABLE_NAME = "applications";
    static final String USER_TABLE_NAME = "users";
    static final String FACULTIES_TABLE_NAME = "faculties";
    static final String SELECTION_ROUND_TABLE = "selection_rounds";


    private Connection conn;


    public ApplicationsDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Application get(long id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            String sql = "SELECT a.id, a.rating, a.c_date, u.name, u.lastname, u.surname, f.label, s.selection_plan, f.id as f_id FROM " + TABLE_NAME + " AS a " +
                         "JOIN " + USER_TABLE_NAME + " AS u ON a.id_user = u.id " +
                         "JOIN " + FACULTIES_TABLE_NAME + " AS f ON a.id_faculty = f.id " +
                         "JOIN  " + SELECTION_ROUND_TABLE + " AS s ON f.id_selection_round = s.id WHERE a.id = ?;";

            st = conn.prepareStatement(sql);

            st.setLong(1, id);

            st.execute();
            rs = st.getResultSet();

            if (!rs.next()) {
                return null;
            }

            return parseFromResultSet(rs);

        } catch (SQLException e) {
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


    public Application getByUserId(long id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            String sql = "SELECT a.id, a.rating, a.c_date, u.name, u.lastname, u.surname, f.label, s.selection_plan, f.id as f_id FROM " + TABLE_NAME + " AS a " +
                    "JOIN " + USER_TABLE_NAME + " AS u ON a.id_user = u.id " +
                    "JOIN " + FACULTIES_TABLE_NAME + " AS f ON a.id_faculty = f.id " +
                    "JOIN  " + SELECTION_ROUND_TABLE + " AS s ON f.id_selection_round = s.id WHERE u.id = ?;";

            st = conn.prepareStatement(sql);

            st.setLong(1, id);

            st.execute();
            rs = st.getResultSet();

            if (!rs.next()) {
                return null;
            }

            return parseFromResultSet(rs);

        } catch (SQLException e) {
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

    public List<Application> getByFacultyId(long id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {

            String sql = "SELECT a.id, a.rating, a.c_date, u.name, u.lastname, u.surname, f.label, s.selection_plan, f.id as f_id FROM " + TABLE_NAME + " AS a " +
                    "JOIN " + USER_TABLE_NAME + " AS u ON a.id_user = u.id " +
                    "JOIN " + FACULTIES_TABLE_NAME + " AS f ON a.id_faculty = f.id " +
                    "JOIN  " + SELECTION_ROUND_TABLE + " AS s ON f.id_selection_round = s.id WHERE f.id = ?;";

            st = conn.prepareStatement(sql);

            st.setLong(1, id);

            st.execute();

            rs = st.getResultSet();


            List<Application> applications = new ArrayList<>();

            while(rs.next()) {
                applications.add(parseFromResultSet(rs));
            }

            return applications;

        } catch (SQLException e) {
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
    public List<Application> getAll() {

        Statement st = null;
        ResultSet rs = null;

        try {

            String sql = "SELECT a.id, a.rating, a.c_date, u.name, u.lastname, u.surname, f.label, s.selection_plan, f.id as f_id FROM " + TABLE_NAME + " AS a " +
                    "JOIN " + USER_TABLE_NAME + " AS u ON a.id_user = u.id " +
                    "JOIN " + FACULTIES_TABLE_NAME + " AS f ON a.id_faculty = f.id " +
                    "JOIN  " + SELECTION_ROUND_TABLE + " AS s ON f.id_selection_round = s.id;";

            st = conn.createStatement();

            st.execute(sql);

            rs = st.getResultSet();


            List<Application> applications = new ArrayList<>();

            while(rs.next()) {
                applications.add(parseFromResultSet(rs));
            }

            return applications;

        } catch (SQLException e) {
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
    public Application save(Application application) throws Exception {
        String sql = "INSERT INTO " + TABLE_NAME +
                " (rating, id_faculty, id_user, c_date) VALUES (?, ?, ?, now());";


        try (
                PreparedStatement st = conn.prepareStatement(sql)
                ){
            st.setFloat(1, application.getRating());
            st.setLong(2, application.getFaculty().getId());
            st.setLong(3, application.getUser().getId());

            st.executeUpdate();

            return application;
        }

    }

    @Override
    public void update(Application application) throws Exception {
        throw new ExecutionControl.NotImplementedException("Application are immutable");
    }

    @Override
    public void delete(Application application) throws Exception {
        Utils.deleteById(application.getId(), TABLE_NAME, conn);
    }


    protected Application parseFromResultSet(ResultSet rs) throws SQLException {

        long id = rs.getLong("id");
        float rating = rs.getFloat("rating");
        Date date = rs.getDate("c_date");

        String name = rs.getString("name");
        String lastname = rs.getString("lastname");
        String surname = rs.getString("surname");

        String label = rs.getString("label");

        int selectionPlan = rs.getInt("selection_plan");

        long facultyId = rs.getLong("f_id");

        SelectionRound s = new SelectionRound(null, null, null, null, selectionPlan);
        Faculty f = new Faculty(facultyId, label, s, null);
        User u = new Enrollee(null, name, lastname, surname, null);

        return new Application(id, f, u, rating, date);
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
