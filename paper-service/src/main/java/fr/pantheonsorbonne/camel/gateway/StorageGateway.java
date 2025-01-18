package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.camel.Routes;
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

        public void newPaper(PaperContentDTO paperBodyDTO) throws InternalCommunicationException {
            try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
                producerTemplate.sendBody(Routes.NEW_TO_STORAGE.getRoute(), paperBodyDTO);
            } catch (Exception e) {
                throw new InternalCommunicationException("Error while sending new paper to storage");
            }
        }

        public void deletePaper(Long id) throws InternalCommunicationException {
            try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
                producerTemplate.sendBody(Routes.DELETE_COMMAND_TO_STORAGE.getRoute(), id);
            } catch (Exception e) {
                throw new InternalCommunicationException("Error while sending deleted paper command to storage");
            }
        }

    public String getPaperContent(String uuid) throws InternalCommunicationException {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            return producerTemplate.requestBody(Routes.GET_PAPER_CONTENT.getRoute(), uuid, String.class);

        } catch (Exception e) {
            throw new InternalCommunicationException("Error while getting paper content from storage");
        }
    }
}
