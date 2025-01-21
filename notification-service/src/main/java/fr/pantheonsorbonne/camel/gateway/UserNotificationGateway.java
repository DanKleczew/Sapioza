package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserNotificationGateway {

    @Inject
    CamelContext camelContext;

    public UserInfoDTO getUserInfo(Long userId) {
        try (ProducerTemplate producerTemplate = this.camelContext.createProducerTemplate()) {
            return producerTemplate.requestBody(Routes.GET_USER_INFO.getRoute(), userId, UserInfoDTO.class);
        } catch (Exception e) {
            Log.error("Error while fetching user info for user ID: " + userId, e);
            return null;
        }
    }

    public UserFollowersDTO getUserFollowers (Long userId) {
        try (ProducerTemplate producerTemplate = this.camelContext.createProducerTemplate()) {
            return producerTemplate.requestBody(Routes.GET_USER_FOLLOWERS.getRoute(), userId, UserFollowersDTO.class);
        } catch (Exception e) {
            Log.error("Error while fetching followers for user ID: " + userId, e);
            return null;
        }
    }
}
