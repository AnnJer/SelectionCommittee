package dispatcher;

public enum RequestTypes {
    GET ("get"),
    POST ("post"),
    DELETE ("delete"),
    PUT ("put"),
    OPTIONS ("options");


    private String type;

    RequestTypes(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
