package common.exceptions;

import common.viewEvents.ViewEvent;
import json.JsonComponent;
import json.JsonSerializable;
import json.JsonUtil;

import java.util.HashMap;

public class GuardException extends Exception implements JsonSerializable {

    protected ViewEvent event;
    protected Integer errorCode;

    public GuardException(String errorMessage) {
        super(errorMessage);
    }

    public GuardException(String errorMessage, ViewEvent event) {
        super(errorMessage);
        this.event = event;
    }

    public GuardException(String message, ViewEvent event, Integer errorCode) {
        super(message);
        this.event = event;
        this.errorCode = errorCode;
    }

    public GuardException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


    public Integer getErrorCode() {
        return errorCode;
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
