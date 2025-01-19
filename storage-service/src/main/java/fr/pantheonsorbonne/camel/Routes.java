package fr.pantheonsorbonne.camel;

public enum Routes {

    NEW_FROM_PAPER("direct:newPaperToStorage"),
    ALTER_COMMAND_FROM_PAPER("direct:deletePaperToStorage"),
    SEND_PAPER_CONTENT("direct:getPaperContent");

    private final String route;

    Routes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
