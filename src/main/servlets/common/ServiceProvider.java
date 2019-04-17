package common;

import auth.Auth;
import dataAccess.DBAccessFactory;
import selectionCommittee.SelectionCommittee;

public class ServiceProvider {
    private static ServiceProvider ourInstance = new ServiceProvider();

    public static ServiceProvider getInstance() {
        return ourInstance;
    }

    private Auth auth;
    private SelectionCommittee selectionCommittee;

    private ServiceProvider() {
        auth = new Auth();
        selectionCommittee = new SelectionCommittee(DBAccessFactory.getInstance());
    }


    public Auth getAuth() {
        return auth;
    }

    public SelectionCommittee getSelectionCommittee() {
        return selectionCommittee;
    }
}
