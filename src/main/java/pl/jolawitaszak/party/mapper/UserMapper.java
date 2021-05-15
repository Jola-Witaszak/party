package pl.jolawitaszak.party.mapper;

import org.springframework.stereotype.Service;
import pl.jolawitaszak.party.domain.User;
import pl.jolawitaszak.party.domain.UserDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getUserId(),
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getAttendingParty());
    }

    public UserDto mapToUserDto(final User user) {
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getAttendingParty());
    }

    public List<UserDto> mapToUsersDtoList(List<User> users){
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    public Set<UserDto> mapToUsersDtoSet(Set<User> usersDto) {
        return usersDto.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toSet());
    }

    public List<User> mapTuUsersList(List<UserDto> usersDto) {
        return usersDto.stream()
                .map(this::mapToUser)
                .collect(Collectors.toList());
    }
}
