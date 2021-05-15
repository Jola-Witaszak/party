package pl.jolawitaszak.party.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jolawitaszak.party.domain.Event;
import pl.jolawitaszak.party.domain.EventDto;
import pl.jolawitaszak.party.domain.GpsPosition;
import pl.jolawitaszak.party.domain.User;
import pl.jolawitaszak.party.mapper.EventMapper;
import pl.jolawitaszak.party.repository.EventRepository;
import pl.jolawitaszak.party.repository.GpsPositionRepository;
import pl.jolawitaszak.party.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceTestSuite {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GpsPositionRepository gpsPositionRepository;

    @Test
    void shouldCreateEvent() {
        //Given
        Event event = new Event(null, "test_name", LocalDate.of(2021, 5, 25),
                LocalTime.of(16, 0), null, "description");
        EventDto eventDto = eventMapper.mapToEventDto(event);
        //When
        EventDto addedEvent = eventService.addEvent(eventDto);
        long id = addedEvent.getId();
        //Then
        Optional<Event> createdEvent = eventRepository.findById(id);
        assertNotEquals(0, id);
        assertTrue(createdEvent.isPresent());
        //CleanUp
        eventRepository.deleteById(id);
    }

    @Test
    void shouldUpdateEvent() throws EventNotExistsException {
        //Given

        Event event = new Event(null, "test_name", LocalDate.of(2021, 5, 25),
                LocalTime.of(16, 0), null, "description");
        Event oldEvent = eventRepository.save(event);
        long eventId = oldEvent.getEventId();

        Event updatedEvent = new Event(eventId, "BirthDay", LocalDate.of(2021,5,25),
                LocalTime.of(15,0), LocalDate.of(2021,5,25), "dsc");
        EventDto eventDto = eventMapper.mapToEventDto(updatedEvent);

        //When
        eventService.updateEvent(eventDto);

        //Then
        assertNotEquals(oldEvent.getName(), updatedEvent.getName());
        //CleanUp
        eventRepository.deleteById(eventId);
    }

    @Test
    void shouldThrowExceptionWhenUpdateEventWithIncorrectId() throws EventNotExistsException {
        //Given
        Event event = new Event(null, "test_name", LocalDate.of(2021, 5, 25),
                LocalTime.of(16, 0), null, "description");
        Event eventToSave = eventRepository.save(event);
        long eventId = eventToSave.getEventId();

        Event savedEvent = eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotExistsException("Event with id " + eventId + " not exists")
        );
        EventDto eventDto = eventMapper.mapToEventDto(savedEvent);
        eventRepository.deleteById(eventId);

        // When & Then
        assertThrows(EventNotExistsException.class, () -> eventService.updateEvent(eventDto));
    }

    @Test
    void shouldThrowExceptionWhenRemoveEventWithIncorrectId() {
        //Given
        Event event = new Event(null, "Garden Party", LocalDate.now(), LocalTime.now(), null,
                "summer party with friends");
        Event event2 = new Event(null, "Test_event", LocalDate.now(), LocalTime.now(), null,
                "test description");

        Event savedEvent = eventRepository.save(event);
        Event savedEvent2 = eventRepository.save(event2);
        long eventId = savedEvent.getEventId();
        long eventId2 = savedEvent2.getEventId();

        eventRepository.deleteById(eventId);

        //When & Then
        assertThrows(EventNotExistsException.class, () -> eventService.removeEvent(eventId));
        //CleanUp
        eventRepository.deleteById(eventId2);
    }

    @Test
    void shouldRemoveEvent() throws EventNotExistsException {
        //Given
        Event event = new Event(null, "Test_event", LocalDate.now(), LocalTime.now(), null,
                "test description");
        Event party = new Event(null, "Garden Party", LocalDate.now(), LocalTime.now(), null,
                "summer party with friends");

        Event savedEvent = eventRepository.save(event);
        Event savedParty = eventRepository.save(party);
        long partyId = savedEvent.getEventId();
        long eventId = savedParty.getEventId();
        List<Event> eventsBeforeRemoval = eventRepository.findAll();

        //When
        eventService.removeEvent(partyId);
        List<Event> eventsAfterRemoval = eventRepository.findAll();

        //Then
        assertEquals(2, eventsBeforeRemoval.size());
        assertEquals(1, eventsAfterRemoval.size());

        //CleanUp
        eventRepository.deleteById(eventId);
    }

    @Test
    void shouldThrowExceptionWhenGetEventWthIncorrectId() {
        //Given
        Event event = new Event(null, "Garden Party", LocalDate.now(), LocalTime.now(), null,
                "summer party with friends");
        Event savedEvent = eventRepository.save(event);
        long eventId = savedEvent.getEventId();
        eventRepository.deleteById(eventId);

        //When & Then
        assertThrows(EventNotExistsException.class, () -> eventService.getEvent(eventId));
    }

    @Test
    public void shouldGetOneEvent() throws EventNotExistsException {
        //Given
        Event party = new Event(null, "Garden Party", LocalDate.now(), LocalTime.now(), null,
                "summer party with friends");

        Event savedParty = eventRepository.save(party);
        long partyId = savedParty.getEventId();

        //When
        EventDto downloadedEvent = eventService.getEvent(partyId);
        Event testEvent = eventMapper.mapToEvent(downloadedEvent);

        //Then
        assertNotEquals(0, partyId);
        assertEquals("Garden Party", testEvent.getName());
        assertEquals("summer party with friends", testEvent.getDescription());
        //CleanUp
        eventRepository.deleteById(testEvent.getEventId());
    }
    @Test
    void shouldGetAllEvents() {
        //Given
        Event event1 = new Event(null, "Garden Party", LocalDate.now(), LocalTime.now(), null,
                "Summer party with friends");
        Event event2 = new Event(null, "Kinder Party", LocalDate.now(), LocalTime.now(), null,
                "Party with one hundred children");
        Event event3 = new Event(null, "Baby Shower", LocalDate.now(), LocalTime.now(), null,
                "The best party!");

        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);

        //When
        List<EventDto> findEventsDto = eventService.getAll();
        int findEventsDtoSize = findEventsDto.size();

        //Then
        assertEquals(eventRepository.findAll().size(), findEventsDtoSize );

        //CleanUp
        List<Event> findEvents = eventMapper.mapToEventsList(findEventsDto);
        eventRepository.deleteAll(findEvents);
    }

    @Test
    @Transactional
    void shouldAddEventToEventsListInUserObjectAndAddUserToUsersSetInEventObject() throws UserNotExistsException, EventNotExistsException {
        //Given
        Event event = new Event(null, "Baby Shower", LocalDate.now(), LocalTime.now(), null,
                "The best party!");
        User user = new User(null, "Marcus", "marcus@mail.pl", null);

        Event savedEvent = eventRepository.save(event);
        User savedUser = userRepository.save(user);

        long eventsListSizeBeforeInviteGuests = savedUser.getEvents().size();
        long usersListSizeBeforeInviteGuests = savedEvent.getUsers().size();

        long eventId = savedEvent.getEventId();
        long userId = savedUser.getUserId();

        //When
        eventService.inviteGuests(eventId, userId);

        //Then
        User userWithEventsList = userRepository.findById(userId).orElseThrow(
                () -> new UserNotExistsException(("User with id " + userId + " not exists"))
        );
        List<Event> usersEvent = userWithEventsList.getEvents();
        int eventListSizeInUser = usersEvent.size();

        Event eventWithGuests = eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotExistsException("Event with id " + eventId + " not exists")
        );
        Set<User> eventsGuests = eventWithGuests.getUsers();
        int usersLListSizeInEvent = eventsGuests.size();

        assertEquals(1, eventListSizeInUser);
        assertNotEquals(eventsListSizeBeforeInviteGuests, eventListSizeInUser);

        assertEquals(1, usersLListSizeInEvent);
        assertNotEquals(usersListSizeBeforeInviteGuests, usersLListSizeInEvent);
        //CleanUp
        eventRepository.deleteById(eventId);
        userRepository.deleteById(userId);
    }

    @Test
    void shouldThrowExceptionWhenRemoveUserWithIncorrectId() {
        //Given
        User user = new User(null, "Daria", "daria@mail.pl", null);
        User user2 = new User(null, "Tom", "tom@mail.pl", null);
        Event event = new Event(null, "Garden Party", LocalDate.now(), LocalTime.now(), null,
                "summer party with friends");
        User savedUser = userRepository.save(user);
        User savedUser2 = userRepository.save(user2);
        long userId2 = savedUser2.getUserId();
        long userId = savedUser.getUserId();
        Event savedEvent = eventRepository.save(event);
        long eventId = savedEvent.getEventId();

        userRepository.deleteById(userId);

        //When & Then
        assertThrows(UserNotExistsException.class, () -> eventService.removeGuests(eventId, userId));
        //Cleanup
        eventRepository.deleteById(eventId);
        userRepository.deleteById(userId2);
    }

    @Test
    @Transactional
    void shouldRemoveEventFromEventsListInUserObjectAndRemoveUserFromUsersSetInEventObject() throws UserNotExistsException, EventNotExistsException {
        //Given
        Event event = new Event(null, "Baby Shower", LocalDate.now(), LocalTime.now(), null,
                "The best party!");
        User user = new User(null, "Marcus", "marcus@mail.pl", null);

        Event savedEvent = eventRepository.save(event);
        User savedUser = userRepository.save(user);

        long eventsListSizeBeforeInviteGuests = savedUser.getEvents().size();
        long usersListSizeBeforeInviteGuests = savedEvent.getUsers().size();

        long eventId = savedEvent.getEventId();
        long userId = savedUser.getUserId();

        //When
        eventService.removeGuests(eventId, userId);

        //Then
        User userWithEventsList = userRepository.findById(userId).orElseThrow(
                () -> new UserNotExistsException("User with id " + userId + " not exists")
        );
        List<Event> usersEvent = userWithEventsList.getEvents();
        int eventListSizeInUser = usersEvent.size();

        Event eventWithGuests = eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotExistsException("Event with id " + eventId + " not exists")
        );
        Set<User> eventsGuests = eventWithGuests.getUsers();
        int usersLListSizeInEvent = eventsGuests.size();

        assertEquals(0, eventListSizeInUser);
        assertEquals(0, usersLListSizeInEvent);
        assertEquals(eventsListSizeBeforeInviteGuests, eventListSizeInUser);
        assertEquals(usersListSizeBeforeInviteGuests, usersLListSizeInEvent);
        //CleanUp
        eventRepository.deleteById(eventId);
        userRepository.deleteById(userId);
    }

    @Test
    void shouldThrowExceptionsWhenAddGpsPositionWithIncorrectEventId() {
        //Given
        Event event = new Event(null, "Baby Shower", LocalDate.now(), LocalTime.now(), null,
                "The best party!");
        GpsPosition gpsPosition = new GpsPosition(null, "test place", 51.7569, 17.9874);

        Event savedEvent = eventRepository.save(event);
        GpsPosition savedGps = gpsPositionRepository.save(gpsPosition);

        long eventId = savedEvent.getEventId();
        long gpsId = savedGps.getGpsId();

        eventRepository.deleteById(eventId);

        //When & Then
        assertThrows(EventNotExistsException.class, () -> eventService.addGpsPosition(eventId, gpsId));

        //CleanUp
        gpsPositionRepository.deleteById(gpsId);
    }

    @Test
    void shouldThrowExceptionsWhenAddGpsPositionWithIncorrectGpsPositionId() {
        //Given
        Event event = new Event(null, "Baby Shower", LocalDate.now(), LocalTime.now(), null,
                "The best party!");
        GpsPosition gpsPosition = new GpsPosition(null, "test place", 51.7569, 17.9874);

        Event savedEvent = eventRepository.save(event);
        GpsPosition savedGps = gpsPositionRepository.save(gpsPosition);

        long eventId = savedEvent.getEventId();
        long gpsId = savedGps.getGpsId();

        gpsPositionRepository.deleteById(gpsId);

        //When & Then
        assertThrows(GpsPositionNotFoundException.class, () -> eventService.addGpsPosition(eventId, gpsId));

        //CleanUp
        eventRepository.deleteById(eventId);
    }

    @Test
    @Transactional
    void shouldAddGpsPositionToGpsPositionsListInEventObjectAndAddEventToEventsListInGpsPositionObject() throws EventNotExistsException, GpsPositionNotFoundException {
        //Given
        Event event = new Event(null, "Baby Shower", LocalDate.now(), LocalTime.now(), null,
                "The best party!");
        GpsPosition gpsPosition = new GpsPosition(null, "test place", 51.7569, 17.9874);

        Event savedEvent = eventRepository.save(event);
        GpsPosition savedGps = gpsPositionRepository.save(gpsPosition);

        int gpsListSize = savedEvent.getGpsPositions().size();
        int eventsListSize = savedGps.getEvents().size();

        long eventId = savedEvent.getEventId();
        long gpsId = savedGps.getGpsId();

        //When
        eventService.addGpsPosition(eventId, gpsId);

        //Then
        Event eventWithGpsPositionsList = eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotExistsException("Event with id " + eventId + " not exists")
        );
        Set<GpsPosition> gpsPositionsListInEvent = eventWithGpsPositionsList.getGpsPositions();
        int gpsPositionsListSizeInEvent = gpsPositionsListInEvent.size();

        GpsPosition gpsWithEventsList = gpsPositionRepository.findById(gpsId).orElseThrow(
                () -> new GpsPositionNotFoundException("GPS position with id " + gpsId + " not exists")
        );
        List<Event> eventsListInGpsPosition = gpsWithEventsList.getEvents();
        int eventListSizeInGpsPosition = eventsListInGpsPosition.size();

        assertEquals(0, gpsListSize);
        assertEquals(1, gpsPositionsListSizeInEvent);
        assertNotEquals(gpsListSize, gpsPositionsListSizeInEvent);

        assertEquals(0, eventsListSize);
        assertEquals(1, eventListSizeInGpsPosition);
        assertNotEquals(eventsListSize, eventListSizeInGpsPosition);

        //CleanUp
        eventRepository.deleteById(eventId);
        gpsPositionRepository.deleteById(gpsId);
    }

    @Test
    @Transactional
    void shouldRemoveGpsPositionFromGpsPositionsListInEventObjectAndRemoveEventFromEventsListInGpsPositionObject() throws EventNotExistsException, GpsPositionNotFoundException {
        //Given
        Event event = new Event(null, "Baby Shower", LocalDate.now(), LocalTime.now(), null,
                "The best party!");
        GpsPosition gpsPosition = new GpsPosition(null, "test place", 51.7569, 17.9874);

        Event savedEvent = eventRepository.save(event);
        GpsPosition savedGps = gpsPositionRepository.save(gpsPosition);

        int gpsListSize = savedEvent.getGpsPositions().size();
        int eventsListSize = savedGps.getEvents().size();

        long eventId = savedEvent.getEventId();
        long gpsId = savedGps.getGpsId();

        //When
        eventService.removeGpsPosition(eventId, gpsId);

        //Then
        GpsPosition locationWithEventsList = gpsPositionRepository.findById(gpsId).orElseThrow(
                () -> new GpsPositionNotFoundException("Gps with id" + gpsId + " not exists")
        );
        int eventsList = locationWithEventsList.getEvents().size();

        Event eventWithGpsPositions = eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotExistsException("Event with id " + eventId + " not exists")
        );
        int locationsList = eventWithGpsPositions.getGpsPositions().size();

        assertEquals(0, locationsList);
        assertEquals(0, eventsList);
        assertEquals(gpsListSize, locationsList);
        assertEquals(eventsListSize, eventsList);

        //CleanUp
        gpsPositionRepository.deleteById(gpsId);
        eventRepository.deleteById(eventId);
    }

    @Test
    void shouldThrowExceptionWhenRemoveGpsPositionWithIncorrectId() {
        //Given
        Event event = new Event(null, "Baby Shower", LocalDate.now(), LocalTime.now(), null,
                "The best party!");
        GpsPosition gpsPosition = new GpsPosition(null, "test place", 51.7569, 17.9874);

        Event savedEvent = eventRepository.save(event);
        GpsPosition savedGps = gpsPositionRepository.save(gpsPosition);

        long eventId = savedEvent.getEventId();
        long gpsId = savedGps.getGpsId();

        gpsPositionRepository.deleteById(gpsId);

        //When & Then
        assertThrows(GpsPositionNotFoundException.class, () -> eventService.removeGpsPosition(eventId, gpsId));

        //CleanUp
        eventRepository.deleteById(eventId);
    }
}
