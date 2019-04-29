import org.junit.Test;
import user.Enrollee;
import user.User;

public class AuthLogic {



    private User createUser() {
        User user = new Enrollee(1L, null, null, null, "111");
        user.setPassword("111");
        return user;
    }


    @Test
    public void testUserLogin() {
//        JsonObject answer = new JsonObject();
//
//        answer.addValue("name", new JsonStringValue("Bob \"carl\""));
//        answer.addValue("age", new JsonNumber(12.5));
//        answer.addValue("marks", new JsonArray(new ArrayList<>() {
//            {
//                add(new JsonNumber(2));
//                add(new JsonNumber(3.5));
//            }
//        }));
//
//        answer.addValue("bbq", new JsonObject(new HashMap<>() {
//            {
//                put("source", new JsonStringValue("alt"));
//            }
//        }));
//
//        System.out.println(answer.encode());

//        try {
//            StatementDAO statementDAO = DBAccessFactory.getInstance().getDAOFactory().getStatementDAO();
//
//            System.out.println(statementDAO.getByFacultyId(1, 1));
//
//            statementDAO.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        try {
//            System.out.println(ServiceProvider.getInstance().getSelectionCommittee().getApplicationByUser(2));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }



}
