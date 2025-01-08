package fr.pantheonsorbonne.global;

public enum GlobalRoutes {
    NEW_PAPER_P2N("sjms2:M1.SAPIOZA:newPaperMetaData"),
    // At paper POST, metadata from paper-service to notification-service. Message Body: PaperMetaDataDTO
    NEW_PAPER_P2S("sjms2:M1.SAPIOZA:newPaperBody"),
    // At paper POST, content from paper-service to storage-service. Message Body: PaperContentDTO
    PERSIST_FAIL_N2P("sjms2:M1.SAPIOZA:notificationPersistFailedToPaper"),
    // If notification-service fails to persist metadata, to paper-service. Message Body: Long (PaperID)
    PERSIST_FAIL_N2S("sjms2:M1.SAPIOZA:notificationPersistFailedToStorage"),
    // If notification-service fails to persist metadata, to storage-service. Message Body: Long (PaperID)
    PERSIST_FAIL_S2P("sjms2:M1.SAPIOZA:storagePersistFailedToPaper"),
    // If storage-service fails to persist content, to paper-service. Message Body: Long (PaperID)
    PERSIST_FAIL_S2N("sjms2:M1.SAPIOZA:storagePersistFailedToNotification"),
    // If storage-service fails to persist content, to notification-service. Message Body: Long (PaperID)
    DELETE_PAPER_P2S("sjms2:M1.SAPIOZA:deletePaperContentCommand");
    // At paper DELETE, paperID from paper-service to storage-service. Message Body: Long (PaperID)


    private final String route;

    GlobalRoutes(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }

}
