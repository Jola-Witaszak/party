package pl.jolawitaszak.party.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.jolawitaszak.party.domain.EventDto;
import pl.jolawitaszak.party.domain.GpsPositionDto;
import pl.jolawitaszak.party.domain.UserDto;
import pl.jolawitaszak.party.service.EventNotExistsException;
import pl.jolawitaszak.party.service.EventService;
import pl.jolawitaszak.party.service.GpsPositionNotFoundException;
import pl.jolawitaszak.party.service.UserNotExistsException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE)
    public EventDto add(@RequestBody EventDto eventDto) {
        return eventService.addEvent(eventDto);
    }

    @PutMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE)
    public EventDto update(@RequestBody EventDto eventDto) throws EventNotExistsException {
        return eventService.updateEvent(eventDto);
    }

    @DeleteMapping(value = "/events/{eventId}")
    public void delete(@PathVariable long eventId) throws EventNotExistsException {
        eventService.removeEvent(eventId);
    }

    @GetMapping(value = "/events/{eventId}")
    public EventDto get(@PathVariable long eventId) throws EventNotExistsException {
        return eventService.getEvent(eventId);
    }

    @GetMapping(value = "/events")
    public List<EventDto> getAll() {
        return eventService.getAll();
    }

    @PostMapping (value = "/events/guests")
    public Set<UserDto> addGuest(@RequestParam long eventId, long userId) throws UserNotExistsException, EventNotExistsException {
        return eventService.inviteGuests(eventId, userId);
    }

    @DeleteMapping(value = "/events/guests/{eventId}/{userId}")
    public void removeGuest(@PathVariable long eventId, @PathVariable long userId) throws UserNotExistsException, EventNotExistsException {
        eventService.removeGuests(eventId, userId);
    }

    @PostMapping(value = "/events/location")
    public Set<GpsPositionDto> addLocations(@RequestParam long eventId, @RequestParam long gpsPositionId) throws EventNotExistsException, GpsPositionNotFoundException {
        return eventService.addGpsPosition(eventId, gpsPositionId);
    }

    @DeleteMapping(value = "/events/location/{eventId}/{gpsPositionId}")
    public void removeLocation(@PathVariable long eventId, @PathVariable long gpsPositionId) throws EventNotExistsException, GpsPositionNotFoundException {
        eventService.removeGpsPosition(eventId, gpsPositionId);
    }
}
