package fr.pantheonsorbonne.camel.gateway;

import fr.pantheonsorbonne.camel.Routes;
import fr.pantheonsorbonne.global.PaperMetaDataDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import java.util.Map;

@ApplicationScoped
public class MailingGateway {

    @Inject
    CamelContext camelContext;

    public void sendMail(String email, PaperMetaDataDTO paperMetaDataDTO, UserInfoDTO userInfoDTO) {
        Map<String, String> headers = Map.of("to", email, "subject", "Nouvel article publié : " + paperMetaDataDTO.title());

        try(ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            producerTemplate.sendBodyAndHeader(
                    Routes.NEW_MAIL.getRoute(), //Endpoint
                    String.format(
                    "Bonjour,<br><br>" +
                            "Un nouvel article a été publié par <b>%s</b> : <i>%s</i>.<br>" +
                            "Lien : <a href='%s'> Nouvel article </a><br><br>" +
                            "Cordialement,<br>L'équipe SAPIOZA.",
                    userInfoDTO.firstName() + " " + userInfoDTO.lastName(),
                    paperMetaDataDTO.title(),
                    paperMetaDataDTO.PaperId()), //Body
                    headers); //Header
        } catch (Exception e) {
            Log.error("Error while sending email", e);
        }
    }
}
