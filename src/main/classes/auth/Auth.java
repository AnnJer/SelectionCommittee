package auth;

import dataAccess.dao.UserDAO;
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
                UserDAO userDAO = dataAccessFactory.getDAOFactory().getUserDAO();
                SessionDAO sessionDAO = dataAccessFactory.getDAOFactory().getSessionDAO()
                ) {
            User user = userDAO.getByLoginAndPassword(login, password);

            if (user == null) {
                return null;
            }

            Session session = user.createSession();

            sessionDAO.save(session);
            return session;
        }

    }


    public Session signUp(User user) throws Exception {
        try (
                UserDAO userDAO = dataAccessFactory.getDAOFactory().getUserDAO()
                ) {
            user = new Enrollee(user.getId(), user.getName(), user.getLastname(), user.getSurname(), user.getLogin());
            user.setPassword(new String(user.getPassword()));
            user = userDAO.save(user);

            return user.createSession();
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
