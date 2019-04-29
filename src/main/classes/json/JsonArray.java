package json;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JsonArray implements JsonComponent {

    static final String BEGIN = "[";
    static final String END = "]";
    static final String SEPARATOR = ", ";

    private List<JsonComponent> values;


    public JsonArray() {
        values = new LinkedList<>();
    }

    public JsonArray(List<JsonComponent> values) {
        this.values = values;
    }

    public void addValue(JsonComponent component) {
        values.add(component);
    }


    private StringBuilder getEncodedValues(StringBuilder builder) {

        int i = 0;

        for (JsonComponent value: values
             ) {

            if (i > 0) {
                builder.append(SEPARATOR);
            }

            builder.append(value.encode());

            i++;
        }

        return builder;
    }


    @Override
    public String encode() {
        return getEncodedValues(new StringBuilder().append(BEGIN)).append(END).toString();
    }
}
