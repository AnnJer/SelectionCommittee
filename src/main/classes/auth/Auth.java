package auth;

import dataAccess.dao.EnrolleeDAO;
import dataAccess.dao.SessionDAO;
import dataAccess.DBAccessFactory;
import dataAccess.DataAccessFactory;
import user.Enrollee;
import user.User;


public class Auth {


    private DataAccessFactory dataAccessFactory;


    public Auth() {
        dataAccessFactory = DBAccessFactory.getInstance();
    }



    public Session getSession(String token) throws Exception {

        try (
                SessionDAO sessionDAO = dataAccessFactory.getDAOFactory().getSessionDAO()
                ) {

            return sessionDAO.getByToken(token);
        }
    }


    public Session signIn(String login, String password) throws Exception {

        try (
                EnrolleeDAO enrolleeDAO = dataAccessFactory.getDAOFactory().getEnrolleeDAO();
                SessionDAO sessionDAO = dataAccessFactory.getDAOFactory().getSessionDAO()
                ) {
            Enrollee enrollee = enrolleeDAO.getByLoginAndPassword(login, password);

            if (enrollee == null) {
                return null;
            }

            Session session = enrollee.createSession();

            sessionDAO.save(session);
            return session;
        }

    }


    public Session signUp(User user) throws Exception {
        try (
                EnrolleeDAO enrolleeDAO = dataAccessFactory.getDAOFactory().getEnrolleeDAO()
                ) {
            Enrollee enrollee = new Enrollee(user.getId(), user.getName(), user.getLastname(), user.getSurname(), user.getLogin());
            enrollee.setPassword(new String(user.getPassword()));
            enrollee = enrolleeDAO.save(enrollee);

            return enrollee.createSession();
        }
    }


    public void signOut(String token) throws Exception {

        try (
                SessionDAO sessionDAO = dataAccessFactory.getDAOFactory().getSessionDAO()
        ) {
            sessionDAO.deleteByToken(token);
        }

    }

    public boolean isSignedIn(String token) throws Exception {
        try (
                SessionDAO sessionDAO = dataAccessFactory.getDAOFactory().getSessionDAO()
        ) {
            return sessionDAO.getByToken(token) != null;
        }
    }


}
