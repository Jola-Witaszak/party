package pl.jolawitaszak.party.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import pl.jolawitaszak.party.domain.Mail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @InjectMocks
    private MailService mailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    void shouldSendEmail() {
        //Given
        Mail mail = Mail.builder()
                .mailTo("test@mail.pl")
                .mailToCc(new String[]{"test@o2.pl", "test@gmail.com", "test@gmail.com"})
                .subject("test subject")
                .message("test message")
                .build();
        //When
        mailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send((MimeMessagePreparator) any());
    }
}
