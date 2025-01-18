package fr.pantheonsorbonne.camel.gateway;
import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.global.UserInfoDTO;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import java.io.IOException;

public class UserGateway {
    @Inject
    CamelContext camelContext;

    public UserInfoDTO getUserInfos(Long userId) throws Exception {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()){
            return producerTemplate.requestBody(Routes.GET_USER_INFO.getRoute(), userId, UserInfoDTO.class);
        } catch (IOException e) {
            throw new Exception("Error fetching author info from user service");
        }
    }
}
