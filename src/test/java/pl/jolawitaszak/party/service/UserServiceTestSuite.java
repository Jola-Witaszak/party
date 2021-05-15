package pl.jolawitaszak.party.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jolawitaszak.party.domain.Event;
import pl.jolawitaszak.party.domain.User;
import pl.jolawitaszak.party.domain.UserDto;
import pl.jolawitaszak.party.mapper.UserMapper;
import pl.jolawitaszak.party.repository.EventRepository;
import pl.jolawitaszak.party.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTestSuite {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @Test
    void shouldAddUserToDatabase() {
        //Given
        User user = new User(null, "Monica", "moni@mail.pl", null);
        UserDto userDto = userMapper.mapToUserDto(user);
        //When
        UserDto savedUser = userService.addUser(userDto);
        long userId = savedUser.getUserId();

        //Then
        assertNotEquals(0, userId);
        assertTrue(userRepository.findById(userId).isPresent());

        //CleanUp
        userRepository.deleteById(userId);
    }

    @Test
    void shouldUpdateUser() throws UserNotExistsException {
        //Given
        User user = new User(null, "Monica", "moni@mail.pl", null);
        User savedUser = userRepository.save(user);
        long userId = savedUser.getUserId();

        User userToUpdate = new User(userId, "Monica", "moni@mail.pl", true);
        UserDto userToUpdateDto = userMapper.mapToUserDto(userToUpdate);

        //When
        UserDto updatedUserDto = userService.update(userToUpdateDto);
        User updatedUser = userMapper.mapToUser(updatedUserDto);

        //Then
        assertEquals(true, updatedUser.getAttendingParty());
        assertEquals(userId, updatedUser.getUserId());

        //CleanUp
        userRepository.deleteById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUpdateUserWithIncorrectId() {
        //Given
        User user = new User(null, "Monica", "moni@mail.pl", null);
        User savedUser = userRepository.save(user);
        long userId = savedUser.getUserId();

        User userToUpdate = new User(userId, "Monica", "moni@mail.pl", true);
        UserDto userToUpdateDto = userMapper.mapToUserDto(userToUpdate);
        User testUser = userMapper.mapToUser(userToUpdateDto);
        userRepository.deleteById(testUser.getUserId());

        //When
        //Then
        assertThrows(UserNotExistsException.class, () -> userService.update(userToUpdateDto));
    }

    @Test
    void shouldRemoveUser() throws UserNotExistsException {
        //Given
        User user = new User(null, "Monica", "moni@mail.pl", null);
        User user2 = new User(null, "Alice", "ala@mail.pl", null);
        User savedUser2 = userRepository.save(user2);
        User savedUser = userRepository.save(user);
        long userId = savedUser.getUserId();
        long userId2 = savedUser2.getUserId();
        int usersSize = userRepository.findAll().size();

        //When
        userService.remove(userId);

        //Then
        int userSizeAfterRemoval = userRepository.findAll().size();

        assertNotEquals(usersSize, userSizeAfterRemoval);
        assertEquals(1, userSizeAfterRemoval);

        //CleanUp
        userRepository.deleteById(userId2);
    }

    @Test
    void shouldThrowExceptionWhenDeleteUserWithIncorrectId() {
        //Given
        User user = new User(null, "Monica", "moni@mail.pl", null);
        User savedUser = userRepository.save(user);
        long userId = savedUser.getUserId();
        userRepository.deleteById(userId);

        //When & Then
        assertThrows(UserNotExistsException.class, () -> userService.remove(userId));
    }

    @Test
    void shouldFetchOneUser() throws UserNotExistsException {
        //Given
        User user = new User(null, "Monica", "moni@mail.pl", null);
        User savedUser = userRepository.save(user);
        long userId = savedUser.getUserId();
        int usersSize = userRepository.findAll().size();

        //When
        UserDto userDto = userService.getUser(userId);
        User fetchedUser = userMapper.mapToUser(userDto);

        //Then
        assertNotEquals(0, userId);
        assertEquals(1, usersSize);
        assertEquals("Monica", fetchedUser.getUsername());

        //Cleanup
        userRepository.deleteById(userId);
    }

    @Test
    void shouldThrowExceptionWhenGetUserWithIncorrectId() {
        //Given
        User user = new User(null, "Monica", "moni@mail.pl", null);
        User user2 = new User(null, "Alice", "ala@mail.pl", null);
        User savedUser2 = userRepository.save(user2);
        User savedUser = userRepository.save(user);
        long userId = savedUser.getUserId();
        long userId2 = savedUser2.getUserId();
        userRepository.deleteById(userId);

        //When
        //Then
        assertThrows(UserNotExistsException.class, () -> userService.getUser(userId));

        //CleanUp
        userRepository.deleteById(userId2);
    }

    @Test
    void shouldFetchAllUsers() {
        //Given
        User user = new User(null, "Monica", "moni@mail.pl", null);
        User user2 = new User(null, "Alice", "ala@mail.pl", null);
        User savedUser2 = userRepository.save(user2);
        User savedUser = userRepository.save(user);
        long userId = savedUser.getUserId();
        long userId2 = savedUser2.getUserId();

        int usersNumber = userRepository.findAll().size();

        //When
        int fetchedUsers = userService.getAll().size();

        //Then
        assertEquals(usersNumber, fetchedUsers);

        //CleanUp
        userRepository.deleteById(userId);
        userRepository.deleteById(userId2);
    }

    @Test
    void shouldReturnEmptyListWhenThereIsNoUsersInDatabase() {
        //Given & When
        List<UserDto> emptyList = userService.getAll();
        int emptyListSize = emptyList.size();

        //Then
        assertEquals(0, emptyListSize);
    }

    @Test
    void shouldSetFieldAttendingPartyTrueWhenUserConfirmYoursParticipation() throws UserNotExistsException, EventNotExistsException {
        //Given
        Event event = new Event(null, "Test name", LocalDate.now(), LocalTime.now(),
                LocalDate.now().plusDays(1), "description");
        User user = new User(null, "Alan", "alan@mail.pl",null);

        Event savedEvent = eventRepository.save(event);
        long eventId = savedEvent.getEventId();

        User savedUser = userRepository.save(user);
        long userId = savedUser.getUserId();

        //When
        userService.confirmParticipation(userId, eventId);

        //Then
        User participant = userRepository.findById(savedUser.getUserId())
                .orElseThrow(() -> new UserNotExistsException("User with id " + userId + " not exists"));

        assertNotNull(participant.getAttendingParty());
        assertTrue(participant.getAttendingParty());

        //CleanUp
        userRepository.deleteById(userId);
        eventRepository.deleteById(eventId);
    }
}
