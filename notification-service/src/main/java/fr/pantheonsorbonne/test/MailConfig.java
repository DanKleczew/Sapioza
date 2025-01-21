package fr.pantheonsorbonne.test;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class MailConfig {

    @ConfigProperty(name = "smtp.host")
    String smtpHost;

    @ConfigProperty(name = "smtp.port")
    String smtpPort;

    @ConfigProperty(name = "smtp.user")
    String smtpUser;

    @ConfigProperty(name = "smtp.password")
    String smtpPassword;

    public String getSmtpEndpoint() {
        return "smtps://" + smtpHost + ":" + smtpPort + "?username=" + smtpUser + "&password=" + smtpPassword + "&starttls=true";
    }
}