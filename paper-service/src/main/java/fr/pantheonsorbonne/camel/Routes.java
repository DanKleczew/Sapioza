package fr.pantheonsorbonne.camel;

public enum Routes {
    NEW_TO_NOTIF("direct:newPaperToNotification"),
    NEW_TO_STORAGE("direct:newPaperToStorage"),
    DELETE_COMMAND_TO_STORAGE("direct:deletePaperToStorage"),
    UPDATE_TO_STORAGE("direct:updatePaperToStorage"),
    REQUEST_USER_INFO("direct:requestUserInfo"),
    REQUEST_USER_STRONG_IDENTIFICATION("direct:requestUserStrongIdentification"),
    GET_PAPER_CONTENT("direct:getPaperContent");

    private final String route;

    Routes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}


