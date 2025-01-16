package fr.pantheonsorbonne.camel;

public enum Routes {
    NEW_TO_NOTIF("direct:newPaperToNotification"),
    NEW_TO_STORAGE("direct:newPaperToStorage"),
    DELETE_COMMAND_TO_STORAGE("direct:deletePaperToStorage"),
    REQUEST_USER_INFO("direct:requestUserInfo")
    ;

    private final String route;

    Routes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}


