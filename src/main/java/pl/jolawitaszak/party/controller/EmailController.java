package pl.jolawitaszak.party.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.jolawitaszak.party.domain.Mail;
import pl.jolawitaszak.party.service.MailService;

@RestController
@RequestMapping("/v1")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EmailController {
    private final MailService mailService;

    @PostMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String sendEmail(@RequestBody Mail mail) {
        return mailService.send(mail);
    }
}
