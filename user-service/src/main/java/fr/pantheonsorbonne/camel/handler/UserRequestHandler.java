package fr.pantheonsorbonne.camel.handler;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.mappers.UserMapper;
import fr.pantheonsorbonne.service.UserService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;

@ApplicationScoped
public class UserRequestHandler {


    @Inject
    UserMapper userMapper;

    @Inject
    UserService userService;

    @Handler
    public void getUserInformation(Exchange exchange) {
        try {
            Long id = exchange.getIn().getBody(Long.class);
            UserDTO userDTO = userService.getUserDTOById(id);
            UserInfoDTO userInfoDTO = null;
            if(userDTO != null){
                userInfoDTO = userMapper.mapUserDTOToUserInfoDTO(userDTO);
            }
            exchange.getMessage().setBody(userInfoDTO, UserInfoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting user information");
        }
    }
}
