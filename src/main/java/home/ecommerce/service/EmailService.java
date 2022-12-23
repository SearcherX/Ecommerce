package home.ecommerce.service;

import home.ecommerce.entity.User;
import home.ecommerce.entity.VerificationToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Objects;


@Service
@AllArgsConstructor
public class EmailService {
    private final VerificationTokenService verificationTokenService;
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final Environment env;

    @Transactional
    public void sendHtmlEmail(User user) throws MessagingException {
        VerificationToken verificationToken = verificationTokenService.findByUser(user);
        if (verificationToken != null) {
            String token = verificationToken.getToken();
            Context context = new Context();
            context.setVariable("title", "Подтвердите адрес вашей почты");
            context.setVariable("link", "http://localhost:8080/activation?token=" + token);

            String body = templateEngine.process("account/verification", context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
            helper.setTo(user.getEmail());
            helper.setSubject("Подтверждение адреса почты");
            helper.setText(body, true);

            javaMailSender.send(message);
        }
    }
}
