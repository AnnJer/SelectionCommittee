package json;


import org.json.simple.JSONObject;

public class JsonStringValue implements JsonComponent {

    static final String BEGIN = "\"";
    static final String END = "\"";


    private String value;

    public JsonStringValue(String value) {
        this.value = value;
    }

    @Override
    public String encode() {
        return new StringBuilder().append(BEGIN).append(JSONObject.escape(this.value)).append(END).toString();
    }

}
