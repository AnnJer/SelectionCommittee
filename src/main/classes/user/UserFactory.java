package user;

public class UserFactory {
    private static UserFactory ourInstance = new UserFactory();

    public static UserFactory getInstance() {
        return ourInstance;
    }

    private UserFactory() {
    }



    public User createUser(Long id, String name, String lastname, String surname, String login, String role) {

        if (role.equals("admin")) {
            return new Administrator(id, name, lastname, surname, login);
        } else {
            return new Enrollee(id, name, lastname, surname, login);
        }

    }

}
