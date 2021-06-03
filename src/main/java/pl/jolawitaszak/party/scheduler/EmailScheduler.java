package pl.jolawitaszak.party.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.jolawitaszak.party.domain.Event;
import pl.jolawitaszak.party.domain.Mail;
import pl.jolawitaszak.party.mapper.EventMapper;
import pl.jolawitaszak.party.service.EventService;
import pl.jolawitaszak.party.service.MailService;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private static final String SUBJECT = "Party next week!";

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final MailService mailService;

    @Scheduled(cron = "0 0 8 * * *")
    public void sendReminderEmail() {

        String reminderMessage = "Please confirm your participation in the event" ;

        List<Event> events = eventMapper.mapToEventsList(eventService.getAll());

        events.stream()
                .filter(event -> event.getStartDate() == LocalDate.now().plusDays(7L))
                .flatMap(event -> event.getUsers().stream())
                .filter(user -> user.getAttendingParty() == null)
                .forEach(user -> {
                    mailService.send(Mail.builder()
                            .mailTo(user.getEmail())
                            .subject(SUBJECT)
                            .message(reminderMessage)
                            .build()
                    );
                });
    }
}
