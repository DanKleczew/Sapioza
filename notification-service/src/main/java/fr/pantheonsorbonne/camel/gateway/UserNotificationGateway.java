package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.global.UserInfoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
public class UserNotificationGateway {

    @Inject
    CamelContext camelContext;


    public UserInfoDTO getUserInfo(Long userId) {
        try (ProducerTemplate producerTemplate = this.camelContext.createProducerTemplate()) {
            return producerTemplate.requestBody(Routes.GET_USER_INFO.getRoute(), userId, UserInfoDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
