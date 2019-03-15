package university;


import user.Enrollee;

public class Faculty {

    protected Long id;
    protected String label;
    protected SelectionRound selectionRound;
    protected ApplicationManager applicationManager;

    public Faculty(Long id, String label, SelectionRound selectionRound, ApplicationManager applicationManager) {
        this.id = id;
        this.label = label;
        this.selectionRound = selectionRound;
        this.applicationManager = applicationManager;
    }




    public boolean isCanApply(Enrollee enrollee) {
        if(selectionRound != null) {
            return selectionRound.isCanApply(enrollee);
        }

        return false;
    }






    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public SelectionRound getSelectionRound() {
        return selectionRound;
    }

    public void setSelectionRound(SelectionRound selectionRound) {
        this.selectionRound = selectionRound;
    }


    public ApplicationManager getApplicationManager() {
        return applicationManager;
    }

    public void setApplicationManager(ApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }
}
