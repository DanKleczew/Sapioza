package fr.pantheonsorbonne.camel;

public enum Routes {

    NEW_TO_STORAGE("direct:newPaperToStorage"),
    DELETE_COMMAND_TO_STORAGE("direct:deletePaperToStorage"),
    GET_PAPER_CONTENT("direct:getPaperContent");

    private final String route;

    Routes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
