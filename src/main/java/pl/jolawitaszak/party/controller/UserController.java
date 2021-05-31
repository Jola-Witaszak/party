package pl.jolawitaszak.party.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.jolawitaszak.party.domain.UserDto;
import pl.jolawitaszak.party.service.EventNotExistsException;
import pl.jolawitaszak.party.service.UserNotExistsException;
import pl.jolawitaszak.party.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto create(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PutMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto update(@RequestBody UserDto userDto) throws UserNotExistsException {
        return userService.update(userDto);
    }

    @DeleteMapping(value = "/users/{id}")
    public void delete(@PathVariable long id) throws UserNotExistsException {
        userService.remove(id);
    }

    @GetMapping(value = "/users/{id}")
    public UserDto get(@PathVariable long id) throws UserNotExistsException {
        return userService.getUser(id);
    }

    @GetMapping(value = "/users")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @PutMapping(value = "/users/confirm") //
    public void confirm(@RequestParam long userId, long eventId) throws UserNotExistsException, EventNotExistsException {
        userService.confirmParticipation(userId, eventId);
    }

    @GetMapping(value = "/users/search/{stringFilter}")
    public List<UserDto> search(@PathVariable String stringFilter) {
        return userService.findAll(stringFilter);
    }
}
