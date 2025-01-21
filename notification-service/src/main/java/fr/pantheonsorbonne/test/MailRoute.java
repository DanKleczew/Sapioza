package fr.pantheonsorbonne.test;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;

@ApplicationScoped
public class MailRoute extends RouteBuilder {

    @ConfigProperty(name = "smtp.host")
    String smtpHost;

    @ConfigProperty(name = "smtp.port")
    String smtpPort;

    @ConfigProperty(name = "smtp.user")
    String smtpUser;

    @ConfigProperty(name = "smtp.password")
    String smtpPassword;

    @ConfigProperty(name = "smtp.from")
    String smtpFrom;

    @Override
    public void configure() throws Exception {
        from("direct:sendNewMail")
                .log("Préparation à l'envoi d'un email à : ${body.email}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        EmailNotificationDTO emailNotification = exchange.getMessage().getBody(EmailNotificationDTO.class);

                        exchange.getMessage().setHeaders(new HashMap<>());
                        exchange.getMessage().setHeader("to", emailNotification.getEmail());
                        exchange.getMessage().setHeader("from", smtpFrom);
                        exchange.getMessage().setHeader("subject", "Nouvel article publié : " + emailNotification.getTitle());
                        exchange.getMessage().setHeader("contentType", "text/html; charset=UTF-8");

                        String body = String.format(
                                "Bonjour,<br><br>" +
                                        "Un nouvel article a été publié par <b>%s</b> : <i>%s</i>.<br>" +
                                        "Lien : <a href='%s'>%s</a><br><br>" +
                                        "Cordialement,<br>L'équipe.",
                                emailNotification.getAuthorName(),
                                emailNotification.getTitle(),
                                emailNotification.getLink(),
                                emailNotification.getLink()
                        );

                        exchange.getMessage().setBody(body);
                    }
                })
                .to("smtp://" + smtpHost + ":" + smtpPort + "?username=" + smtpUser + "&password=" + smtpPassword + "&mail.smtp.starttls.enable=true")
                .log("Email envoyé avec succès à : ${header.to}");
    }

}