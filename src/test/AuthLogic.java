import common.Router;
import common.ServiceProvider;
import dataAccess.Crypto;
import dataAccess.DBAccessFactory;
import json.*;
import org.junit.Test;
import user.Enrollee;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;

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

        try {
            System.out.println(DBAccessFactory.getInstance().getDAOFactory().getUserDAO().get(1));
        } catch (Exception e) {
            e.printStackTrace();
        }


//        try {
//            System.out.println(ServiceProvider.getInstance().getSelectionCommittee().getApplicationByUser(2));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        System.out.println(new String(Crypto.encodePassword("222".getBytes(), "222".getBytes())));

    }



}
