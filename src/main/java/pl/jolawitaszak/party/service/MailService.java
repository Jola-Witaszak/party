package pl.jolawitaszak.party.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import pl.jolawitaszak.party.domain.Mail;

@Service
@Slf4j
public class MailService {

    private final MailCreatorService mailCreatorService;
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(MailCreatorService mailCreatorService, JavaMailSender javaMailSender) {
        this.mailCreatorService = mailCreatorService;
        this.javaMailSender = javaMailSender;
    }

    public void send(Mail mail) {
        log.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimeMessage(mail));
            log.info("Email has been sent!");
        } catch (MailException e) {
            log.error("Failed to process email sending..." + e.getMessage());
        }
    }

    private MimeMessagePreparator createMimeMessage(Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setCc(mail.getMailToCc());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildWelcomeEmail(mail.getMessage()), true);
        };
    }
}
