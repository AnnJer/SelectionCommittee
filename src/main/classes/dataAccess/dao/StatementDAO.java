package dataAccess.dao;

import selectionCommittee.Faculty;
import selectionCommittee.Statement;
import user.Enrollee;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatementDAO implements DAO<Statement>, Closeable {


    static final String TABLE_NAME = "statements";
    static final String ENROLLEE_TABLE_NAME = "users";
    static final String FACULTIES_TABLE_NAME = "faculties";

    static final String APPLICATIONS_TABLE_NAME = "applications";

    private Connection conn;


    public StatementDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Statement get(long id) throws Exception {
        String sql = "SELECT s.*, " +
                     "e.name AS e_name, " +
                     "e.surname AS e_surname, " +
                     "e.lastname AS e_lastname, " +
                     "f.id AS f_id, " +
                     "f.label AS f_label FROM " + TABLE_NAME + " AS s " +
                     "JOIN " + ENROLLEE_TABLE_NAME + " AS e ON (s.id_enrollee = e.id) " +
                     "JOIN " + FACULTIES_TABLE_NAME + " AS f ON (s.id_faculty = f.id) WHERE s.id = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> st1.setLong(1, id)
                );

                ResultSet rs = st.executeQuery()
        ){

            List<Statement> statements = getStatementsWithFacultyAndEnrolleeInfo(rs);

            if (statements.size() < 1) {
                return null;
            }

            return statements.get(0);
        }
    }


    public List<Statement> getByFacultyId(long id, long selectionRoundId) throws Exception {
        String sql = "SELECT s.*, " +
                "e.name AS e_name, " +
                "e.surname AS e_surname, " +
                "e.lastname AS e_lastname, " +
                "f.id AS f_id, " +
                "f.label AS f_label FROM " + TABLE_NAME + " AS s " +
                "JOIN " + ENROLLEE_TABLE_NAME + " AS e ON (s.id_enrollee = e.id) " +
                "JOIN " + FACULTIES_TABLE_NAME + " AS f ON (s.id_faculty = f.id) WHERE f.id = ? AND s.id_selection_round = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> {
                            st1.setLong(1, id);
                            st1.setLong(2, selectionRoundId);
                        }
                );

                ResultSet rs = st.executeQuery()
        ){
            return getStatementsWithFacultyAndEnrolleeInfo(rs);
        }
    }

    public List<Statement> getByEnrolleeId(long id) throws Exception {
        String sql = "SELECT s.*, " +
                "e.name AS e_name, " +
                "e.surname AS e_surname, " +
                "e.lastname AS e_lastname, " +
                "f.id AS f_id, " +
                "f.label AS f_label FROM " + TABLE_NAME + " AS s " +
                "JOIN " + ENROLLEE_TABLE_NAME + " AS e ON (s.id_enrollee = e.id) " +
                "JOIN " + FACULTIES_TABLE_NAME + " AS f ON (s.id_faculty = f.id) WHERE e.id = ?;";

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql, (PreparedStatement st1) -> st1.setLong(1, id)
                );

                ResultSet rs = st.executeQuery()
        ){
            return getStatementsWithFacultyAndEnrolleeInfo(rs);
        }
    }


    public void saveAll(List<Statement> statements) throws SQLException {

        StringBuilder sql = new StringBuilder("INSERT INTO " + TABLE_NAME + "(id_faculty, id_enrollee, rating, c_date) VALUES");

        for (Statement statement: statements) {
            sql.append(" (?, ?, (SELECT rating FROM " + APPLICATIONS_TABLE_NAME + " WHERE id_user = ?), now())");
        }

        try (
                PreparedStatement st = Utils.getPreparedStatement(
                        conn, sql.toString(), (PreparedStatement st1) -> {
                            int i = 0;
                            for (Statement statement: statements) {
                                st1.setLong(++i, statement.getFaculty().getId());
                                st1.setLong(++i, statement.getEnrollee().getId());
                                st1.setLong(++i, statement.getEnrollee().getId());
                            }
                        }
                )
        ){
            st.executeUpdate();
        }

    }


    @Override
    public List<Statement> getAll() throws Exception {
        // TODO: implement getAll
        return null;
    }



    protected List<Statement> getStatementsWithFacultyAndEnrolleeInfo(ResultSet rs) throws SQLException {

        List<Statement> statements = new ArrayList<>();

        while (rs.next()) {

            Statement statement = parseFromResultSet(rs);
            Enrollee enrollee = parseEnrolleeFromResultSet(rs);
            Faculty faculty = parseFacultyFromResultSet(rs);

            statement.setFaculty(faculty);
            statement.setEnrollee(enrollee);

            statements.add(statement);
        }

        return statements;
    }

    protected Enrollee parseEnrolleeFromResultSet(ResultSet rs) throws SQLException {

        String name = rs.getString("e_name");
        String surname = rs.getString("e_surname");
        String lastname = rs.getString("e_lastname");


        return new Enrollee(null, name, surname, lastname, null);
    }

    protected Faculty parseFacultyFromResultSet(ResultSet rs) throws SQLException {

        Long id = rs.getLong("f_id");
        String label = rs.getString("f_label");

        return new Faculty(id, label, null, null);
    }

    protected Statement parseFromResultSet(ResultSet rs) throws SQLException {

        Long id = rs.getLong("id");
        Long selectionRoundId = rs.getLong("id_selection_round");
        Float rating = rs.getFloat("rating");
        Date cDate = rs.getDate("c_date");


        return new Statement(id, null, null, selectionRoundId, rating, cDate);
    }



    @Override
    public Statement save(Statement statement) throws Exception {
        // TODO: implement save
        return null;
    }

    @Override
    public void update(Statement statement) throws Exception {
        // TODO: implement update
    }

    @Override
    public void delete(Statement statement) throws Exception {
        // TODO: implement delete
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
