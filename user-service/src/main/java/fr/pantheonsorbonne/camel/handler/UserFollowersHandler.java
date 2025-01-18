package fr.pantheonsorbonne.camel.handler;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;

@ApplicationScoped
public class UserFollowersHandler {

    @Inject
    UserService userService;

    @Handler
    public void getUserFollowers(Exchange exchange) {
        try {
            Long id = exchange.getIn().getBody(Long.class);
            UserDTO userDTO = userService.getUser(id);
            UserFollowersDTO userFollowersDTO = null;
            if (userDTO != null) {
                userFollowersDTO = new UserFollowersDTO(userDTO.id(), userService.getSubscribersId(userDTO.id()));
            }
            exchange.getMessage().setBody(userFollowersDTO, UserFollowersDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting user followers");
        }
    }
}
