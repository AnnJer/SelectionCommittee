package json;

import java.util.List;
import java.util.Map;

public class JsonUtil {

    public static JsonObject object() {
        return new JsonObject();
    }

    public static JsonObject object(Map<String, JsonComponent> values) {
        return new JsonObject(values);
    }

    public static JsonArray array() {
        return new JsonArray();
    }

    public static JsonArray array(List<JsonComponent> values) {
        return new JsonArray(values);
    }

    public static JsonNumber number(Number number) {
        return new JsonNumber(number);
    }

    public static JsonStringValue string(String value) {
        return new JsonStringValue(value);
    }

    public static JsonBoolean logical(boolean value) {
        return new JsonBoolean(value);
    }


}
