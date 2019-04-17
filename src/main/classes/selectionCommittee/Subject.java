package selectionCommittee;

import json.JsonComponent;
import json.JsonSerializable;
import json.JsonUtil;

import java.util.HashMap;

public class Subject implements JsonSerializable {

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

    @Override
    public JsonComponent toJson() {
        return JsonUtil.object(new HashMap<>() {
            {
                put("id", JsonUtil.number(id));
                put("label", JsonUtil.string(label));
            }
        });
    }
}
