package university;

public class Subject {

    protected Long id;
    protected String label;


    public Subject(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Subject(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
