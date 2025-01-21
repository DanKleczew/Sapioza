package fr.pantheonsorbonne.camel;

public enum Routes {
    PAPER_PERSIST_FAILURE("direct:persistFailedPaper");

    private final String route;

    Routes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
