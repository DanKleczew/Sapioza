package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
public class StorageGateway {

    @Inject
    CamelContext camelContext;

    public void persistFailed(PaperContentDTO paperContentDTO) {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            producerTemplate.sendBody(Routes.PAPER_PERSIST_FAILURE.getRoute(), paperContentDTO.paperUuid());
        } catch (Exception e) {
            Log.error("Failed to send persist failed message to paper", e);
        }
    }
}
