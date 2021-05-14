package pl.jolawitaszak.party.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jolawitaszak.party.domain.Event;
import pl.jolawitaszak.party.domain.User;
import pl.jolawitaszak.party.domain.UserDto;
import pl.jolawitaszak.party.mapper.UserMapper;
import pl.jolawitaszak.party.repository.EventRepository;
import pl.jolawitaszak.party.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public UserDto addUser(UserDto userDto) {
        User newUser = userMapper.mapToUser(userDto);
        User savedUser = userRepository.save(newUser);
        return userMapper.mapToUserDto(savedUser);
    }

    public UserDto update(UserDto userDto) throws UserNotExistsException {
        Optional<User> findUser = userRepository.findById(userDto.getUserId());
        if (findUser.isPresent()) {
            User userToUpdate = userMapper.mapToUser(userDto);
            User updatedUser = userRepository.save(userToUpdate);
            return userMapper.mapToUserDto(updatedUser);
        } else {
            throw new UserNotExistsException("User with id " + userDto.getUserId() + " not exists");
        }
    }

    public void remove(final long userId) throws UserNotExistsException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotExistsException("User with id " + userId + " not exists"));
        userRepository.deleteById(user.getUserId());
    }

    public UserDto getUser(final long userId) throws UserNotExistsException {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotExistsException("User with id " + userId + " not exists"));
        return userMapper.mapToUserDto(findUser);
    }

    public List<UserDto> getAll() {
        List<User> users =  userRepository.findAll();
        return userMapper.mapToUsersDtoList(users);
    }

     public void confirmParticipation(long userId, long eventId) throws UserNotExistsException, EventNotExistsException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotExistsException("User with id " + userId + " not exists"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotExistsException("Event with id " + eventId + " not exists"));
        user.setAttendingParty(true);
        userRepository.save(user);
    }
}
