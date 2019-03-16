import auth.Session;
import dataAccess.DBAccessFactory;
import org.junit.Test;
import user.Enrollee;
import user.User;

public class AuthLogic {



    private User createUser() {
        User user = new Enrollee(1, null, null, null, "111");
        user.setPassword("111");
        return user;
    }


    @Test
    public void testUserLogin() {

    }



}
