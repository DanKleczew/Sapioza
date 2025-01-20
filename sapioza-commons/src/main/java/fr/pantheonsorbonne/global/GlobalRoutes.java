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
    ALTER_PAPER_P2S("sjms2:M1.SAPIOZA:alterPaper"),
    // At paper DELETE or PATCH, from paper-service to storage-service. Message Body: Long (PaperID) OR PaperContentDTO
    USER_INFO_REQUEST_REPLY_QUEUE("sjms2:M1.SAPIOZA:userInfoRequestReplyQueue"),
    // Request/Reply queue for User Information. Message Body : UserInfoDTO
    USER_STRONG_ID_REQUEST_REPLY_QUEUE("sjms2:M1.SAPIOZA:userStrongIdRequestReplyQueue"),
    // Request/Reply queue for User Strong Identification. Message Body : UserIdentificationDTO => Boolean <=
    PAPER_CONTENT_REQUEST_REPLY_QUEUE("sjms2:M1.SAPIOZA:getPaperContent"),
    // Request/Reply queue for Paper Content. Message Body : PaperContentDTO

    //User
    GET_USER_INFORMATION_U2N("sjms2:M1.SAPIOZA:getUserInformation"),
    // From user-service to get information about a user. Message Body: Long (UserID), Response: UserInfoDTO

    GET_USER_FOLLOWERS("sjms2:M1.SAPIOZA:userFollowers"),
    // From user-service to get followers of a user. Message Body: Long (UserID), Response: UserFollowersDTO

    GET_USER_FOLLOWS("sjms2:M1.SAPIOZA:userFollowing");
    // From user-service to get users followed by a user. Message Body: Long (UserID) Response: UserFollowsDTO

    private final String route;

    GlobalRoutes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }

}
