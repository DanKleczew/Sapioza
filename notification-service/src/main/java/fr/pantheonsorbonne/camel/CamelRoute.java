package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.camel.handler.NotificationHandler;
import fr.pantheonsorbonne.global.GlobalRoutes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class CamelRoute extends RouteBuilder {

    @ConfigProperty(name = "smtp.host")
    String smtpHost;

    @ConfigProperty(name = "smtp.port")
    String smtpPort;

    @ConfigProperty(name = "smtp.user")
    String smtpUser;

    @ConfigProperty(name = "smtp.password")
    String smtpPassword;

    @ConfigProperty(name = "smtp.from")
    String smtpFrom;

    @Inject
    NotificationHandler notificationHandler;

    @Override
    public void configure() {
        onException(Exception.class)
                .log("Erreur lors du traitement de la notification : ${exception.message}")
                .handled(true);

        from(GlobalRoutes.NEW_PAPER_P2N.getRoute())
                .log("Message reçu pour notification d'un nouvel article : ${body}")
                .bean(notificationHandler);

        from(Routes.GET_USER_INFO.getRoute())
                .setExchangePattern(ExchangePattern.InOut)
                .to(GlobalRoutes.USER_INFO_REQUEST_REPLY_QUEUE.getRoute()+ "?requestTimeout=5000")
                .end();

        from(Routes.GET_USER_FOLLOWERS.getRoute())
                .setExchangePattern(ExchangePattern.InOut)
                .to(GlobalRoutes.GET_USER_FOLLOWERS.getRoute()+ "?requestTimeout=5000")
                .end();

        from(Routes.NEW_MAIL.getRoute())
                .log("${body}")
                .process(exchange -> {
                    exchange.getMessage().setHeader("from", smtpFrom);
                    exchange.getMessage().setHeader("contentType", "text/html; charset=UTF-8");
                })
                .to("smtp://" + smtpHost + ":"
                        + smtpPort + "?username="
                        + smtpUser + "&password="
                        + smtpPassword + "&mail.smtp.starttls.enable=true");
    }
}
