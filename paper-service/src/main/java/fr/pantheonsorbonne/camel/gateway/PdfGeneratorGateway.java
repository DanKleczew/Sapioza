package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.dto.SubmittedPaperDTO;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

public class PdfGeneratorGateway {

    @Inject
    CamelContext camelContext;

    public void generatePDF(SubmittedPaperDTO completePaperDTO) throws InternalCommunicationException {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            producerTemplate.sendBody(Routes.GENERATE_PDF.getRoute(), completePaperDTO);
        } catch (Exception e) {
            throw new InternalCommunicationException("Error while generating pdf");
        }
    }
}
