package university;

import user.Enrollee;

import java.util.ArrayList;
import java.util.List;

public class ApplicationManager {


    private static ApplicationManager instance = null;

    public static ApplicationManager getInstance() {

        if (instance != null) {
            return instance;
        }


        instance = new ApplicationManager();
        return instance;
    }




    public boolean isValidApplication(Application aapplication) {

        return true;
    }


    List<Application> getApplicationsByEnrollee(Enrollee enrollee) {
        return new ArrayList<>();
    }


    List<Application> getApplicationsByFaculty(Faculty faculty) {
        return new ArrayList<>();
    }


}
