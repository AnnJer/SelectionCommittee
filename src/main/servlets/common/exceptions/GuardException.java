package common.exceptions;

import common.viewEvents.ViewEvent;
import json.JsonComponent;
import json.JsonSerializable;
import json.JsonUtil;

import java.util.HashMap;

public class GuardException extends Exception implements JsonSerializable {

    protected ViewEvent event;


    public GuardException(String errorMessage) {
        super(errorMessage);
    }

    public GuardException(String errorMessage, ViewEvent event) {
        super(errorMessage);

    }

    @Override
    public JsonComponent toJson() {
        return JsonUtil.object(new HashMap<>() {
            {
                put("message", JsonUtil.string(getMessage()));

                if (event != null) {
                    put("event", event.toJson());
                }
            }
        });
    }
}
