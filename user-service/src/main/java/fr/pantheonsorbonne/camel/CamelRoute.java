package fr.pantheonsorbonne.camel;


import fr.pantheonsorbonne.camel.gateway.UserAccount;
import fr.pantheonsorbonne.camel.handler.UserRequestHandler;
import fr.pantheonsorbonne.global.GlobalRoutes;
import fr.pantheonsorbonne.global.UserFollowersDTO;
import fr.pantheonsorbonne.global.UserFollowsDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class CamelRoute extends RouteBuilder {

        @Inject
        UserAccount userAccount;

        @Inject
        UserRequestHandler userRequestHandler;


        @Override
        public void configure(){

                from(GlobalRoutes.USER_INFO_REQUEST_REPLY_QUEUE.getRoute())
                        .log("Received message on user info request reply queue")
                        .bean(userRequestHandler, "getUserInformation")
                        .log("Sending response to user info request reply queue")
                        .log("body :  ${body}")
                        .outputType(UserInfoDTO.class)
                        .end();

                from(GlobalRoutes.GET_USER_FOLLOWERS.getRoute())
                        .log("Received message on user followers request from")
                        .bean(userRequestHandler, "getUserFollowers")
                        .log("Sending response to user followers request")
                        .log("body :  ${body}")
                        .outputType(UserFollowersDTO.class)
                        .end();

                from(GlobalRoutes.GET_USER_FOLLOWS.getRoute())
                        .log("Received message on user follows request from")
                        .bean(userRequestHandler, "getUserFollows")
                        .log("Sending response to user follows request")
                        .log("body :  ${body}")
                        .outputType(UserFollowsDTO.class)
                        .end();

                from(GlobalRoutes.USER_STRONG_ID_REQUEST_REPLY_QUEUE.getRoute())
                        .log("Received message on user strong id request reply queue + ${body}")
                        .bean(userRequestHandler, "getUuidByAuthentification")
                        .log("Sending response to user strong id request reply queue")
                        .log("body :  ${body}")
                        .outputType(Boolean.class)
                        .end();
        }
}
