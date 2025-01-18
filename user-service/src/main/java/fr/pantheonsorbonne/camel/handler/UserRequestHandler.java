package fr.pantheonsorbonne.camel.handler;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.global.UserFollowsDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.mappers.UserMapper;
import fr.pantheonsorbonne.service.UserService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;

import java.util.List;

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
            UserDTO userDTO = userService.getUser(id);
            UserInfoDTO userInfoDTO = null;
            if(userDTO != null){
                userInfoDTO = userMapper.mapUserDTOToUserInfoDTO(userDTO);
            }
            exchange.getMessage().setBody(userInfoDTO, UserInfoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting user information");
        }
    }

    @Handler
    public void getUserFollowers(Exchange exchange) {
        try {
            Long id = exchange.getMessage().getBody(Long.class);
            UserDTO userDTO = userService.getUser(id);
            UserFollowersDTO userFollowersDTO = null;
            if (userDTO != null) {
                userFollowersDTO = userMapper.mapUserToUserFollowersDTO(userDTO);
            }
            exchange.getMessage().setBody(userFollowersDTO, UserFollowersDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting user followers");
        }
    }

    @Handler
    public void getUserFollows(Exchange exchange) {
        try {
            Long id = exchange.getMessage().getBody(Long.class);
            UserDTO userDTO = userService.getUser(id);
            List<Long> follows = userService.getSubscribersId(id);
            UserFollowsDTO userFollowsDTO = null;
            if (userDTO != null) {
                userFollowsDTO = new UserFollowsDTO(userDTO.id(), follows);
            }
            exchange.getMessage().setBody(userFollowsDTO, UserFollowsDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting user follows");
        }
    }




}
