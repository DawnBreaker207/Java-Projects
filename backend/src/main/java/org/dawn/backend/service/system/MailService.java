package org.dawn.backend.service.system;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.dawn.backend.config.web.MailConfig;
import org.dawn.backend.config.web.TemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MailService {
    private static MailService instance;
    private TemplateEngine template;
    private final Session session;

    private final String mailUsername;
    private final String mailFrom;
    private final boolean enabled;

    @Autowired
    public MailService(MailConfig mailConfig,
                       @Value("${mail.username}") String mailUsername,
                       @Value("${mail.from}") String mailFrom,
                       @Value("${mail.enabled:false}") boolean enabled) {
        this.template = TemplateConfig.getEngine();
        this.session = mailConfig.getSession();
        this.mailUsername = mailUsername;
        this.mailFrom = mailFrom;
        this.enabled = enabled;
        if (!this.enabled) {
            log.info("Mail Service is running in DEV mode (Email is not sent)");
        }
    }

    public void sendHtmlMail(String to, String subject, String templateName, Map<String, Object> variables) {
        log.info("Check enable config: {}", enabled);
        log.info("Check enable : {}", enabled);
        if (!enabled) {
            log.info("[DEV] pass out sending mail. Send to: {} | Title: {} | Data: {}", to, subject, variables);
            return;
        }
        Context context = new Context();
        context.setVariables(variables);
        String htmlContent = template.process(templateName, context);
        Thread.ofVirtual().start(() -> {
            try {
                Message message = new MimeMessage(session);
                String senderEmail = (mailFrom != null && !mailFrom.isBlank()) ? mailFrom : mailUsername;
                message.setFrom(new InternetAddress(senderEmail, "System"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setContent(htmlContent, "text/html; charset=utf-8");

                Transport.send(message);
                log.info("Email sent successfully to: {}", to);
            } catch (Exception e) {
                log.error("Failed to send email to: {}", to, e);
            }
        });
    }

    public void sendPasswordResetMail(String to, String recipientName, String resetLink) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", recipientName);
        variables.put("resetLink", resetLink);

        sendHtmlMail(to, "Đặt lại mật khẩu", "password-reset", variables);
    }


}
