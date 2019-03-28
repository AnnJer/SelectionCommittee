package common.viewEvents.factories;

import common.viewEvents.RedirectViewEvent;

public class ViewEventFactory {
    private static ViewEventFactory ourInstance = new ViewEventFactory();

    public static ViewEventFactory getInstance() {
        return ourInstance;
    }

    private ViewEventFactory() {
    }

    public RedirectViewEvent redirect(String url) {
        return new RedirectViewEvent(url);
    }
}
