package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
public class StorageGateway {

    @Inject
    CamelContext camelContext;

    public void savePaper(PaperContentDTO paperContentDTO) throws InternalCommunicationException {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            producerTemplate.sendBody(Routes.NEW_FROM_PAPER.getRoute(), paperContentDTO);
        } catch (Exception e) {
            throw new InternalCommunicationException("Failed to send new paper to storage");
        }
    }

    public String fetchPaperContent(String paperUuid) throws InternalCommunicationException {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            return producerTemplate.requestBody(Routes.SEND_PAPER_CONTENT.getRoute(), paperUuid, String.class);
        } catch (Exception e) {
            throw new InternalCommunicationException("Failed to fetch paper content from storage");
        }
    }
}
