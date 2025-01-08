package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.camel.handler.PersistFailureHandler;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class CamelRoute extends RouteBuilder {

        @Override
        public void configure() throws Exception {

                // Publishing process for a new paper
                from("direct:toNotification")
                        .to("jms:queue:newPaperMetaDonnees");

                from("direct:toStorage")
                        .to("jms:queue:newPaperBody");

                // Fallback process for failed persistence in any of the two services
                from("jms:queue:notificationPersistFailed")
                        .bean(PersistFailureHandler.class, "handle(${id})");

                from("jms:queue:storagePersistFailed")
                        .bean(PersistFailureHandler.class, "handle(${id})");





//            from("timer:foo?period=100000000000")
//                    .log("Création d'un PDF...")
//                    .setBody(constant("Bonjour, ceci est un exemple de PDF généré avec Apache Camel!"))
//                    .to("pdf:create")
//                    .to("file:output?fileName=example.pdf")
//                    .log("PDF créé et enregistré dans le dossier 'output'.")
//                    .bean(;
        }
}
