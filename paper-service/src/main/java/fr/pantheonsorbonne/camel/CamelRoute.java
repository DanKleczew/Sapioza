package fr.pantheonsorbonne.camel;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class CamelRoute extends RouteBuilder {

        @Override
        public void configure() throws Exception {
//            from("timer:foo?period=100000000000")
//                    .log("Création d'un PDF...")
//                    .setBody(constant("Bonjour, ceci est un exemple de PDF généré avec Apache Camel!"))
//                    .to("pdf:create")
//                    .to("file:output?fileName=example.pdf")
//                    .log("PDF créé et enregistré dans le dossier 'output'.");
        }
}
