package fr.pantheonsorbonne.camel;

public enum Routes {

    CREATE_ACCOUNT("direct:createAccount"),
    DELETE_ACCOUNT("direct:deleteAccount"),
    UPDATE_ACCOUNT("direct:updateAccount"),
    GET_ACCOUNT("direct:getAccount"),
    GET_USER_FOLLOWERS("direct:getUserFollowers"),
    GET_USER_FOLLOWS("direct:getUserFollows"),
    GET_USER_INFORMATION("direct:getUserInformation"),

    RESPONSE_USER_INFORMATION("direct:responseUserInformation"),
    ;

    private final String route;

    Routes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}


