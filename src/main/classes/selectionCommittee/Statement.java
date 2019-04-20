package selectionCommittee;

import json.JsonComponent;
import json.JsonSerializable;
import json.JsonUtil;
import user.Enrollee;

import java.sql.Date;
import java.util.HashMap;

public class Statement implements JsonSerializable {

    private Long id;

    private Faculty faculty;
    private Enrollee enrollee;

    private Long idSelectionRound;

    private Float rating;
    private Date cDate;


    public Statement(Long id, Faculty faculty, Enrollee enrollee, Long idSelectionRound, Float rating, Date cDate) {
        this.id = id;
        this.faculty = faculty;
        this.enrollee = enrollee;
        this.idSelectionRound = idSelectionRound;
        this.rating = rating;
        this.cDate = cDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Enrollee getEnrollee() {
        return enrollee;
    }

    public void setEnrollee(Enrollee enrollee) {
        this.enrollee = enrollee;
    }

    public Long getIdSelectionRound() {
        return idSelectionRound;
    }

    public void setIdSelectionRound(Long idSelectionRound) {
        this.idSelectionRound = idSelectionRound;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    @Override
    public JsonComponent toJson() {
        return JsonUtil.object(new HashMap<>() {
            {
                put("id", JsonUtil.number(id));
                put("faculty", JsonUtil.object(new HashMap<>() {
                    {
                        put("id", JsonUtil.number(faculty.getId()));
                        put("label", JsonUtil.string(faculty.getLabel()));
                    }
                }));
                put("enrollee", JsonUtil.object(new HashMap<>() {
                    {
                        put("name", JsonUtil.string(enrollee.getName()));
                        put("surname", JsonUtil.string(enrollee.getSurname()));
                        put("lastname", JsonUtil.string(enrollee.getLastname()));
                    }
                }));
                put("rating", JsonUtil.number(rating));
                put("c_date", JsonUtil.string(cDate.toString()));
            }
        });
    }
}
