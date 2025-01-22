package fr.pantheonsorbonne.camel.handler;

import fr.pantheonsorbonne.dto.UserDTO;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.global.UserFollowsDTO;
import fr.pantheonsorbonne.global.UserIdentificationDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.mappers.UserFollowersDTOMapper;
import fr.pantheonsorbonne.mappers.UserInfoDTOMapper;
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

    @Inject
    UserInfoDTOMapper userInfoDTOMapper;

    @Inject
    UserFollowersDTOMapper userFollowersDTOMapper;

    @Handler
    public void getUserInformation(Exchange exchange) {
        try {
            Long id = exchange.getIn().getBody(Long.class);
            UserDTO userDTO = userService.getUser(id);
            UserInfoDTO userInfoDTO = null;
            if(userDTO != null){
                userInfoDTO = userInfoDTOMapper.mapEntityToDTO(userDTO);
            }
            exchange.getMessage().setBody(userInfoDTO, UserInfoDTO.class);
        } catch (Exception e) {
            exchange.getMessage().setBody(null, UserInfoDTO.class);;
        }
    }

    @Handler
    public void getUserFollowers(Exchange exchange) {
        try {
            Long id = exchange.getMessage().getBody(Long.class);
            UserDTO userDTO = userService.getUser(id);
            UserFollowersDTO userFollowersDTO = null;
            if (userDTO != null) {
                userFollowersDTO = userFollowersDTOMapper.mapEntityToDTO(userDTO);
                Log.error("getUserFollowers : " + userFollowersDTO);
            }
            exchange.getMessage().setBody(userFollowersDTO, UserFollowersDTO.class);
        } catch (Exception e) {
            Log.error("Error while getting user followers"+ e);
            exchange.getMessage().setBody(null, UserFollowersDTO.class);
        }
    }

    @Handler
    public void getUserFollows(Exchange exchange) {
        try {
            Long id = exchange.getMessage().getBody(Long.class);
            UserDTO userDTO = userService.getUser(id);
            UserFollowsDTO userFollowsDTO = null;
            if (userDTO != null) {
                userFollowsDTO = new UserFollowsDTO(userDTO.id(), userService.getSubscribersId(id));
            }
            exchange.getMessage().setBody(userFollowsDTO, UserFollowsDTO.class);
        } catch (Exception e) {
            Log.error("Error while getting user follows");
            exchange.getMessage().setBody(null, UserFollowsDTO.class);
        }
    }

    @Handler
    public void getUuidByAuthentification(Exchange exchange) {
        try {
            UserIdentificationDTO userIdentificationDTO = exchange.getMessage().getBody(UserIdentificationDTO.class);
            Log.error("matchUuidAndPassword : " + userIdentificationDTO);
            Boolean matchUuidAndPassword = userService.identificationByUuidAndId(userIdentificationDTO.userUUID(), userIdentificationDTO.userId());
            Log.error("matchUuidAndPassword : " + matchUuidAndPassword);
            exchange.getMessage().setBody(matchUuidAndPassword, Boolean.class);
        } catch (Exception e) {
            exchange.getMessage().setBody(null, Boolean.class);
            Log.error("Error while getting user id by authentification");
        }
    }




}
