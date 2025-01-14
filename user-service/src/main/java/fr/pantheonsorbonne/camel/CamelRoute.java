package fr.pantheonsorbonne.camel;


import fr.pantheonsorbonne.global.GlobalRoutes;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class CamelRoute extends RouteBuilder {

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


        }
}
