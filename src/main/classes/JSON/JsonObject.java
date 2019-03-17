package JSON;

import java.util.HashMap;
import java.util.Map;

public class JsonObject implements JsonComponent {

    static final String BEGIN = "{";
    static final String END = "}";
    static final String SEPARATOR = ", ";
    static final String KEY_VALUE_SEpARATOR = ": ";

    static final String KEY_BEGIN = "\"";
    static final String KEY_END = "\"";

    Map<String, JsonComponent> values;

    public JsonObject() {
        values = new HashMap<>();
    }

    public JsonObject(Map<String, JsonComponent> values) {
        this.values = values;
    }


    public void addValue(String key, JsonComponent component) {
        values.put(key, component);
    }


    private StringBuilder getEncodedValues(StringBuilder builder) {

        int i = 0;

        for (String key: values.keySet()
        ) {

            if (i > 0) {
                builder.append(SEPARATOR);
            }

            builder.append(KEY_BEGIN);
            builder.append(key);
            builder.append(KEY_END);

            builder.append(KEY_VALUE_SEpARATOR);

            builder.append(values.get(key).encode());

            i++;
        }

        return builder;
    }


    @Override
    public String encode() {
        return getEncodedValues(new StringBuilder().append(BEGIN)).append(END).toString();
    }
}
