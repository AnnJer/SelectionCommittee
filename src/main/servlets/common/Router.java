package common;

import commands.Command;
import dispatcher.RequestTypes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private static Router ourInstance = new Router();

    public static Router getInstance() {
        return ourInstance;
    }


    private Map<String, Command> routes;

    private Router() {
        routes = new HashMap<>();
    }


    public void add(RequestTypes type, String urlPattern, Command command) {
        routes.put(type.toString() + ":" + urlPattern, command);
    }


    /*
        Warning: splits patterns by ":", that placed between request method and url pattern
     */
    public Command route(String url, HttpServletRequest req) {

        RequestTypes requestType = (RequestTypes)req.getAttribute("requestType");

        Command command = null;

        String[] urlParts = url.substring(1).split("/");

        for (String pattern: routes.keySet()) {

            String[] methodAndPattern = pattern.split(":");

            if (!methodAndPattern[0].equals(requestType.toString())) {
                continue;
            }

            String[] patternParts = methodAndPattern[1].substring(1).split("/");

            if (compareUrlParts(urlParts, patternParts)) {
                parseUrpParameters(urlParts, patternParts, req);
                command = routes.get(pattern);
                break;
            }

        }

        return command;
    }


    private void parseUrpParameters(String[] urlParts, String[] patternParts, HttpServletRequest req) {

        for (int i = 0; i < urlParts.length; i++) {

            if (patternParts[i].charAt(0) == '{') {
                req.setAttribute(patternParts[i].substring(1, patternParts[i].length() - 1), urlParts[i]);
            }

        }

    }

    private boolean compareUrlParts(String[] urlParts, String[] patternParts) {

        if (urlParts.length != patternParts.length) {
            return false;
        }

        for (int i = 0; i < urlParts.length; i++) {

            if (patternParts[i].charAt(0) != '{' && !patternParts[i].equals(urlParts[i])) {
                return false;
            }

        }


        return true;
    }

}
