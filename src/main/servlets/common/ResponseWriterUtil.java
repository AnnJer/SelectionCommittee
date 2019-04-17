package common;

import json.JsonComponent;
import json.JsonUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class ResponseWriterUtil {

    public final static int OK = 200;
    public final static int CRATED = 201;
    public final static int BAD_REQUEST = 400;
    public final static int UNAUTHORIZED = 401;
    public final static int FORBIDDEN = 403;
    public final static int NOT_FOUND = 404;
    public final static int METHOD_NOT_ALLOWED = 405;
    public final static int UNPROCESSABLE_ENTITY = 422;
    public final static int SERVER_ERROR = 500;


    public static void write(HttpServletResponse resp, JsonComponent answer, int code) throws IOException {
        resp.setStatus(code);
        resp.getWriter().write(answer.encode());
    }


    public static void error(HttpServletResponse resp, String message, int code) throws IOException {
        write(resp, JsonUtil.object(new HashMap<>() {
            {
                put("error", JsonUtil.string(message));
            }
        }), code);
    }

    public static void error(HttpServletResponse resp, JsonComponent error, int code) throws IOException {
        write(resp, JsonUtil.object(new HashMap<>() {
            {
                put("error", error);
            }
        }), code);
    }

}
