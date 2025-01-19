package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.global.PaperContentDTO;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import java.io.IOException;

@ApplicationScoped
public class StorageGateway {

        @Inject
        CamelContext camelContext;

        public void newPaper(PaperContentDTO paperContentDTO) throws InternalCommunicationException {
            try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
                byte[] pdf = "aaaa afjioskd fiqsdjipd jsd diçsqdjqsdqs usdoiqsdoisqghd zhaydgzayldg sqd".getBytes();
                Byte[] pdfByte = new Byte[pdf.length];
                for (int i = 0; i < pdf.length; i++) {
                    pdfByte[i] = pdf[i];
                }
                PaperContentDTO paperContentDTO1 = new PaperContentDTO(paperContentDTO.paperUuid(), pdfByte);

                producerTemplate.sendBody(Routes.NEW_TO_STORAGE.getRoute(), paperContentDTO1);
            } catch (IOException e) {
                throw new InternalCommunicationException("Error while sending new paper to storage");
            }
        }

        public void deletePaper(String uuid) throws InternalCommunicationException {
            try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
                producerTemplate.sendBody(Routes.DELETE_COMMAND_TO_STORAGE.getRoute(), uuid);
            } catch (Exception e) {
                throw new InternalCommunicationException("Error while sending deleted paper command to storage");
            }
        }

        public void updatePaper(PaperContentDTO paperContentDTO) throws InternalCommunicationException {
            try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
                producerTemplate.sendBody(Routes.UPDATE_TO_STORAGE.getRoute(), paperContentDTO);
            } catch (Exception e) {
                throw new InternalCommunicationException("Error while sending updated paper to storage");
            }
        }

        public Byte[] getPDF(String uuid) throws InternalCommunicationException {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            return producerTemplate.requestBody(Routes.GET_PAPER_CONTENT.getRoute(), uuid, Byte[].class);
        } catch (Exception e) {
            throw new InternalCommunicationException("Error while getting paper content from storage");
        }
    }


}
