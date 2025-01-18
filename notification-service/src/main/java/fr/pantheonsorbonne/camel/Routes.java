package fr.pantheonsorbonne.camel;


public enum Routes {
    GET_USER_INFO("direct:getUserNotificationInfo"),
    GET_USER_FOLLOWERS("direct:getUserNotificationFollowers");

    private final String route;

    Routes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}

