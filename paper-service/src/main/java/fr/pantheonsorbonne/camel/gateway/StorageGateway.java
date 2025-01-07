package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.dto.PaperBodyDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
public class StorageGateway {

        @Inject
        CamelContext camelContext;

        public void sendToStorage(PaperBodyDTO paperBodyDTO) {
            try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
                producerTemplate.sendBody("direct:toStorage", paperBodyDTO);
            } catch (Exception e) {
                Log.error("Error while sending to storage", e);
            }
        }
}
