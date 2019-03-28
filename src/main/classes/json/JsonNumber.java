package json;

public class JsonNumber implements JsonComponent {


    private Number value;

    public JsonNumber(Number value) {
        this.value = value;
    }

    @Override
    public String encode() {
        return value.toString();
    }
}
