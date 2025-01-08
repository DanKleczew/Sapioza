package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.dto.PaperMetaDataDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
public class NotificationGateway {

    @Inject
    CamelContext camelContext;

    public void sendToNotification(PaperMetaDataDTO paperMetaDataDTO) {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            producerTemplate.sendBody("direct:toNotification", paperMetaDataDTO);
        } catch (Exception e) {
            Log.error("Error while sending notification", e);
        }
    }
}
