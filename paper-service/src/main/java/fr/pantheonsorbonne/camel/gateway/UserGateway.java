package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.global.UserIdentificationDTO;
import fr.pantheonsorbonne.exception.InternalCommunicationException;
import fr.pantheonsorbonne.global.UserInfoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import java.io.IOException;

@ApplicationScoped
public class UserGateway {

    @Inject
    CamelContext camelContext;

    public UserInfoDTO getUserInfos(Long authorId) throws InternalCommunicationException {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()){
            return producerTemplate.requestBody(Routes.REQUEST_USER_INFO.getRoute(), authorId, UserInfoDTO.class);
        } catch (IOException e) {
            throw new InternalCommunicationException("Error fetching author info from user service");
        }
    }

    public boolean isUserAllowed(UserIdentificationDTO userIdentificationDTO) throws InternalCommunicationException {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()){
            return producerTemplate.requestBody(Routes.REQUEST_USER_STRONG_IDENTIFICATION.getRoute(),
                    userIdentificationDTO, Boolean.class);
        } catch (IOException e) {
            throw new InternalCommunicationException("Error fetching strong user identification from user service");
        }
    }
}
