package selectionCommittee;

import common.ServiceProvider;
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




    public boolean isValidApplication(Application application) throws Exception {

        Application existApplication = ServiceProvider.getInstance()
                .getSelectionCommittee().getApplicationByUser(application.getUser().getId());

        if (existApplication != null) {
            return false;
        }

        return true;
    }

}
