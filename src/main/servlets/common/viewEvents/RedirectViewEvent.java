package common.viewEvents;

import json.JsonComponent;
import json.JsonUtil;

import java.util.HashMap;

public class RedirectViewEvent extends ViewEvent {

    public static final String TYPE = "redirect";

    protected String url;

    public RedirectViewEvent(String url) {
        this.url = url;
    }

    @Override
    public JsonComponent toJson() {
        return JsonUtil.object(new HashMap<>() {
            {
                put("type", JsonUtil.string(TYPE));
                put("url",  JsonUtil.string(url));
            }
        });
    }
}
