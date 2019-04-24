package selectionCommittee;

import json.JsonComponent;
import json.JsonSerializable;
import json.JsonUtil;
import user.User;

import java.sql.Date;
import java.util.HashMap;

public class Application implements JsonSerializable {

    protected Long id;
    protected Faculty faculty;
    protected User user;
    protected float rating;
    protected Date cDate;

    public Application(Long id, Faculty faculty, User user, float rating, Date cDate) {
        this.id = id;
        this.faculty = faculty;
        this.user = user;
        this.rating = rating;
        this.cDate = cDate;
    }

    public Application(Faculty faculty, User user, float rating, Date cDate) {
        this.faculty = faculty;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
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
                put("rating", JsonUtil.number(rating));
                put("c_date", JsonUtil.string(cDate.toString()));
                put("faculty", JsonUtil.object(new HashMap<>() {
                    {
                        put("label", JsonUtil.string(faculty.getLabel()));
                        put("selection_round", JsonUtil.object(new HashMap<>() {
                            {
                                put("selection_plan", JsonUtil.number(faculty.getSelectionRound().getSelectionPlan()));
                            }
                        }));
                    }
                }));
                put("user", JsonUtil.object(new HashMap<>() {
                    {
                        put("id", JsonUtil.number(user.getId()));
                        put("name", JsonUtil.string(user.getName()));
                        put("surname", JsonUtil.string(user.getSurname()));
                        put("lastname", JsonUtil.string(user.getLastname()));
                    }
                }));
            }
        });
    }
}
