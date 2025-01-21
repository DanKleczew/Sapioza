package fr.pantheonsorbonne.test;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
@Path("/notifications")
public class NotificationResource {
    @Inject
    MailService mailService;

    @GET
    @Path("/test-email")
    public Response testEmail() throws IOException {
        mailService.sendTestEmail();
        return Response.ok("Email envoyé avec succès.").build();
    }
}
