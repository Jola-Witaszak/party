package pl.jolawitaszak.party.mapper;

import org.springframework.stereotype.Service;
import pl.jolawitaszak.party.domain.Event;
import pl.jolawitaszak.party.domain.EventDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventMapper {

    public Event mapToEvent(final EventDto eventDto) {
        return new Event(
                eventDto.getId(),
                eventDto.getName(),
                eventDto.getStartDate(),
                eventDto.getStartTime(),
                eventDto.getEndDate(),
                eventDto.getDescription()
        );
    }

    public EventDto mapToEventDto(final Event event) {
        return new EventDto(
                event.getEventId(),
                event.getName(),
                event.getStartDate(),
                event.getStartTime(),
                event.getEndDate(),
                event.getDescription()
        );
    }

    public List<EventDto> mapToEventsDtoList(List<Event> events) {
        return events.stream()
                .map(this::mapToEventDto)
                .collect(Collectors.toList());
    }

    public List<Event> mapToEventsList(List<EventDto> eventsDto) {
        return eventsDto.stream()
                .map(this::mapToEvent)
                .collect(Collectors.toList());
    }
}
