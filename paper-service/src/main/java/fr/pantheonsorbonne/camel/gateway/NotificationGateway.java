package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
public class NotificationGateway {

    @Inject
    CamelContext camelContext;


    public void newPaper(PaperMetaDataDTO paperMetaDataDTO) throws InternalCommunicationException {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            producerTemplate.sendBody(Routes.NEW_TO_NOTIF.getRoute(), paperMetaDataDTO);
        } catch (Exception e) {
            throw new InternalCommunicationException("Error while sending new paper to notification");
        }
    }
}
