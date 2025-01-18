package fr.pantheonsorbonne.camel;


import fr.pantheonsorbonne.camel.gateway.UserAccount;
import fr.pantheonsorbonne.camel.handler.UserRequestHandler;
import fr.pantheonsorbonne.global.GlobalRoutes;
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

                from(GlobalRoutes.GET_USER_INFORMATION_U2N.getRoute())
                        .to(Routes.GET_USER_INFORMATION.getRoute());

                from(Routes.RESPONSE_USER_INFORMATION.getRoute())
                        .to(GlobalRoutes.RESPONSE_USER_INFORMATION_N2U.getRoute());

                from(GlobalRoutes.GET_USER_FOLLOWERS_U2N.getRoute())
                        .to(Routes.GET_USER_FOLLOWERS.getRoute());

                from(GlobalRoutes.GET_USER_FOLLOWS_U2N.getRoute())
                        .to(Routes.GET_USER_FOLLOWS.getRoute());

                from(GlobalRoutes.USER_INFO_REQUEST_REPLY_QUEUE.getRoute())
                        .log("Received message on user info request reply queue")
                        .bean(userRequestHandler)
                        .log("Sending response to user info request reply queue")
                        .log("body :  ${body}")
                        .outputType(UserInfoDTO.class)
                        //.log("")
                        .end();

        }
}
