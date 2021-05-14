package pl.jolawitaszak.party.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.events.EventException;
import pl.jolawitaszak.party.domain.*;
import pl.jolawitaszak.party.mapper.EventMapper;
import pl.jolawitaszak.party.mapper.GpsPositionMapper;
import pl.jolawitaszak.party.mapper.UserMapper;
import pl.jolawitaszak.party.repository.EventRepository;
import pl.jolawitaszak.party.repository.GpsPositionRepository;
import pl.jolawitaszak.party.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final GpsPositionRepository gpsPositionRepository;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final GpsPositionMapper gpsPositionMapper;

    public EventDto addEvent(final EventDto eventDto) {
        Event eventToAdd = eventMapper.mapToEvent(eventDto);
        Event savedEvent = eventRepository.save(eventToAdd);
        return eventMapper.mapToEventDto(savedEvent);
    }

    public EventDto updateEvent(final EventDto eventDto) throws EventNotExistsException {
        Optional<Event> findEvent = eventRepository.findById(eventDto.getId());
        if (findEvent.isPresent()) {
            Event eventToUpdate = eventMapper.mapToEvent(eventDto);
            Event updatedEvent = eventRepository.save(eventToUpdate);
            return eventMapper.mapToEventDto(updatedEvent);
        } else {
            throw new EventNotExistsException("Event with id " + eventDto.getId() + " not exists");
        }
    }

    public void removeEvent(final long eventId) throws EventNotExistsException {
        if (eventRepository.existsById(eventId)) {
            eventRepository.deleteById(eventId);
        } else {
            throw new EventNotExistsException("Event with id " + eventId + " not exists");
        }
    }

    public EventDto getEvent(final long eventId) throws EventNotExistsException {
        Event findEvent = eventRepository.findById(eventId).orElseThrow(() -> new EventNotExistsException("Event with id " + eventId + " not exists"));
        return eventMapper.mapToEventDto(findEvent);
    }

    public List<EventDto> getAll() {
        List<Event> events =  eventRepository.findAll();
        return eventMapper.mapToEventsDtoList(events);
    }

    public Set<UserDto> inviteGuests(long eventId, long userId) throws UserNotExistsException, EventNotExistsException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotExistsException("User with id " + userId + " not exists"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotExistsException("Event with id " + eventId + " not exists"));
        event.getUsers().add(user);
        user.getEvents().add(event);
        eventRepository.save(event);
        Set<User> guests = event.getUsers();
        return userMapper.mapToUsersDtoSet(guests);
    }

    public void removeGuests(long eventId, long userId) throws UserNotExistsException, EventNotExistsException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotExistsException("User with id " + userId + " not exists"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotExistsException("Event with id " + eventId + " not exists"));
        event.getUsers().remove(user);
        user.getEvents().remove(event);
        eventRepository.save(event);
    }

    public Set<GpsPositionDto> addGpsPosition(long eventId, long gpsPositionId) throws EventNotExistsException, GpsPositionNotFoundException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotExistsException("Event with id " + eventId + " not exists"));
        GpsPosition gpsPosition = gpsPositionRepository.findById(gpsPositionId).orElseThrow(() -> new GpsPositionNotFoundException("GPS position with id " + gpsPositionId + " not exists"));
        event.getGpsPositions().add(gpsPosition);
        gpsPosition.getEvents().add(event);
        Set<GpsPosition> locations = event.getGpsPositions();
        return gpsPositionMapper.mapToGpsSignalsDtoSet(locations);
    }

    public void removeGpsPosition(long eventId, long gpsPositionId) throws EventNotExistsException, GpsPositionNotFoundException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotExistsException("Event with id " + eventId + " not exists"));
        GpsPosition gpsPosition = gpsPositionRepository.findById(gpsPositionId).orElseThrow(() -> new GpsPositionNotFoundException("GPS position with id " + gpsPositionId + " not exists"));
        event.getGpsPositions().remove(gpsPosition);
        gpsPosition.getEvents().remove(event);
    }
}
