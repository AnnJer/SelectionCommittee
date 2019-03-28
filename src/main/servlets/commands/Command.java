package commands;

import common.exceptions.GuardException;
import json.JsonComponent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    JsonComponent execute(HttpServletRequest req, HttpServletResponse resp) throws GuardException;
}
