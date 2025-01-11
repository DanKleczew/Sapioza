package fr.pantheonsorbonne.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import org.jboss.logging.Logger;

@ApplicationScoped
public class NotificationProducer {

    private static final Logger LOGGER = Logger.getLogger(NotificationProducer.class);

    @Inject
    JMSContext jmsContext;

    @Inject
    @io.quarkus.artemis.jms.Queue("notification-queue") // Nom de la queue Artemis
    Queue queue;

    /**
     * Envoie un message texte à la queue Artemis.
     *
     * @param message Le message à envoyer (texte de la notification).
     */
    public void sendNotification(String message) {
        try {
            jmsContext.createProducer().send(queue, message);
            LOGGER.infof("Message envoyé à la queue : %s", message);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de l'envoi du message", e);
        }
    }
}
