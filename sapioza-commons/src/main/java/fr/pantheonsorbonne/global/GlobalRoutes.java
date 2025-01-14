package fr.pantheonsorbonne.global;

public enum GlobalRoutes {
    NEW_PAPER_P2N("sjms2:M1.SAPIOZA:newPaperMetaData"),
    // At paper POST, metadata from paper-service to notification-service. Message Body: PaperMetaDataDTO
    NEW_PAPER_P2S("sjms2:M1.SAPIOZA:newPaperBody"),
    // At paper POST, content from paper-service to storage-service. Message Body: PaperContentDTO
    PERSIST_FAIL_S2P("sjms2:M1.SAPIOZA:storagePersistFailedToPaper"),
    // If storage-service fails to persist content, to paper-service. Message Body: Long (PaperID)
    PERSIST_FAIL_S2N("sjms2:M1.SAPIOZA:storagePersistFailedToNotification"),
    // If storage-service fails to persist content, to notification-service. Message Body: Long (PaperID)
    DELETE_PAPER_P2S("sjms2:M1.SAPIOZA:deletePaperContentCommand"),
    // At paper DELETE, paperID from paper-service to storage-service. Message Body: Long (PaperID)


    //Notification
    USER_REQUEST_N2U("sjms2:M1.SAPIOZA:notificationToUserRequest"),
    // From notification-service to user-service for user followers requests. Message Body: Long (UserID)
    USER_RESPONSE_U2N("sjms2:M1.SAPIOZA:notificationToUserResponse"),
    // From user-service to notification-service in response to followers requests. Message Body: Long (UserID)


    //User
    GET_USER_INFORMATION_U2N("sjms2:M1.SAPIOZA:getUserInformation"),
    // From user-service to get information about a user. Message Body: Long (UserID)
    RESPONSE_USER_INFORMATION_N2U("sjms2:M1.SAPIOZA:responseUserInformation"),
    // From user-service to notification-service in response to user information requests. Message Body: UserDTO

    GET_USER_FOLLOWERS_U2N("sjms2:M1.SAPIOZA:userFollowers"),
    // From user-service to get followers of a user. Message Body: Long (UserID)
    RESPONSE_USER_FOLLOWERS_N2U("sjms2:M1.SAPIOZA:responseUserFollowers"),
    // From user-service to notification-service in response to user followers requests. Message Body: List<Long> (UserIDs)

    GET_USER_FOLLOWING_U2N("sjms2:M1.SAPIOZA:userFollowing"),
    // From user-service to get users followed by a user. Message Body: Long (UserID)
    RESPONSE_USER_FOLLOWING_N2U("sjms2:M1.SAPIOZA:responseUserFollowing");
    // From user-service to notification-service in response to user following requests. Message Body: List<Long> (UserIDs)

    private final String route;

    GlobalRoutes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }

}
