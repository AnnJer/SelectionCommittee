package json;

public class JsonBoolean implements JsonComponent {

    private boolean value;

    final static String FALSE = "false";
    final static String TRUE = "true";

    public JsonBoolean(boolean value) {
        this.value = value;
    }


    @Override
    public String encode() {
        if (value) {
            return TRUE;
        } else {
            return FALSE;
        }
    }
}
