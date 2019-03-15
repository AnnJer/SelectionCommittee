package university;

import user.Enrollee;

import java.util.Date;

public class Application {

    protected Faculty faculty;
    protected Enrollee enrollee;
    protected float rating;
    protected Date cDate;


    public Application(Faculty faculty, Enrollee enrollee, float rating, Date cDate) {
        this.faculty = faculty;
        this.enrollee = enrollee;
        this.rating = rating;
        this.cDate = cDate;
    }


    public Faculty getFaculty() {
        return faculty;
    }

    public Enrollee getEnrollee() {
        return enrollee;
    }

    public float getRating() {
        return rating;
    }

}
