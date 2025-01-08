package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.camel.RoutingService;
import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
public class StorageGateway {

        @Inject
        CamelContext camelContext;

        @Inject
        RoutingService routingService;

        public void newPaper(PaperContentDTO paperBodyDTO) throws InternalCommunicationException {
            try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
                producerTemplate.sendBody(routingService.getLocalRoute(Routes.NEW_TO_STORAGE), paperBodyDTO);
            } catch (Exception e) {
                throw new InternalCommunicationException("Error while sending new paper to storage");
            }
        }

        public void deletePaper(Long id) throws InternalCommunicationException {
            try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
                producerTemplate.sendBody(routingService.getLocalRoute(Routes.DELETE_COMMAND_TO_STORAGE), id);
            } catch (Exception e) {
                throw new InternalCommunicationException("Error while sending deleted paper to storage");
            }
        }
}
