package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.global.UserFollowsDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;

public class UserAccount {

    @Inject
    CamelContext camelContext;

    public void createAccount(Object user) {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            producerTemplate.sendBody("direct:createAccount", user);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating account");
        }
    }

    public void getFollowers(UserFollowersDTO userFollowersDTO) {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            producerTemplate.sendBody("direct:getFollowers", userFollowersDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting followers");
        }
    }

    public void getFollows(UserFollowsDTO userFollowsDTO) {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            producerTemplate.sendBody("direct:getFollows", userFollowsDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting follows");
        }
    }

    public void getUserInformation(Long id){
        try (ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate()) {
            consumerTemplate.receiveBody(Routes.GET_USER_INFORMATION.getRoute(), id);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting user information");
        }
    }

    public void responseUserInformation(UserInfoDTO userInfoDTO){
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            producerTemplate.sendBody(Routes.RESPONSE_USER_INFORMATION.getRoute(), userInfoDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error while responding user information");
        }
    }


}
