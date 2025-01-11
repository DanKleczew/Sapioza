package fr.pantheonsorbonne;

import fr.pantheonsorbonne.service.NotificationService;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class NotificationRoute extends RouteBuilder {

    private final NotificationService notificationService;

    public NotificationRoute(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void configure() {
        from("kafka:new-paper-topic?brokers=localhost:9092")
                .process(exchange -> {
                    // Extraire les données du message Kafka
                    String authorName = exchange.getIn().getHeader("authorName", String.class);
                    String paperTitle = exchange.getIn().getBody(String.class);
                    notificationService.createNotification(authorName, paperTitle);
                });
    }
}
