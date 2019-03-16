import dataAccess.DBAccessFactory;
import org.junit.Assert;
import org.junit.Test;
import rateFactors.RateFactorResult;
import user.Enrollee;
import user.User;

public class AuthLogic {



    private User createUser() {
        User user = new User(null, null, null, null, "111");
        user.setPassword("111");
        return user;
    }


    @Test
    public void testUserLogin() {

        try {
            Enrollee enrollee = DBAccessFactory.getInstance()
                                                .getDAOFactory()
                                                .getEnrolleeDAO()
                                                .getByLoginAndPassword(createUser());


            Assert.assertNotNull(enrollee);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
