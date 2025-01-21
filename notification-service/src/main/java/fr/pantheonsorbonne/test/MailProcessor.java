package fr.pantheonsorbonne.test;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@ApplicationScoped
public class MailProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        EmailNotificationDTO emailNotification = exchange.getMessage().getBody(EmailNotificationDTO.class);

        exchange.getMessage().setHeader("subject", "Nouvel article publié : " + emailNotification.getTitle());
        exchange.getMessage().setHeader("to", emailNotification.getEmail());

        // Ajout de la phrase dans le corps de l'e-mail
        String body = String.format(
                "Bonjour,\n\n" +
                        "Un nouvel article a été publié par %s : %s.\n\n" +
                        "Lien : %s\n\n" +
                        "Cette phrase est générée par la méthode :\n" +
                        "EmailNotificationDTO emailNotification = new EmailNotificationDTO(\n" +
                        "    \"test@example.com\", \"Titre de Test\", \"Auteur Test\", \"http://example.com/articles/test\"\n" +
                        ");\n\n" +
                        "Cordialement,\nL'équipe.",
                emailNotification.getAuthorName(),
                emailNotification.getTitle(),
                emailNotification.getLink()
        );

        exchange.getMessage().setBody(body);
    }

}
