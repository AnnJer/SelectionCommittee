package university.factories;

import university.Subject;

public class SubjectFactory {

    private static SubjectFactory instance = null;

    public static SubjectFactory getInstance() {

        if (instance != null) {
            return instance;
        }


        instance = new SubjectFactory();
        return instance;
    }


    protected SubjectFactory() { }


    public Subject createSubject(String label) {
        return new Subject(label);
    }


}
