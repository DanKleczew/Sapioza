package fr.pantheonsorbonne.camel;


public enum Routes {
    GET_USER_INFO("direct:getUserInfo");

    private final String route;

    Routes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}

