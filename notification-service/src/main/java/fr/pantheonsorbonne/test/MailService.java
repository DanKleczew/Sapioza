package fr.pantheonsorbonne.test;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import java.io.IOException;

@ApplicationScoped
public class MailService {

    @Inject
    CamelContext camelContext;

    public void sendTestEmail() throws IOException {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            EmailNotificationDTO emailNotification = new EmailNotificationDTO(
                    "Dan.Kleczewski1@etu.univ-paris1.fr",
                    "Nouvelle publication",
                    "Auteur Test",
                    "http://example.com/articles/test"
            );

            producerTemplate.sendBody("direct:sendNewMail", emailNotification);
        }
    }
}

