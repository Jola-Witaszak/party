package pl.jolawitaszak.party.controller;

import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.jolawitaszak.party.domain.Event;
import pl.jolawitaszak.party.domain.User;
import pl.jolawitaszak.party.domain.UserDto;
import pl.jolawitaszak.party.mapper.UserMapper;
import pl.jolawitaszak.party.service.UserService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Test
    void shouldCreateUser() throws Exception {
        //Given
        UserDto userDto = new UserDto(null, "Marcus", "marcus@mail.pl", null, 1234);
        User user = new User(1L, "Marcus", "marcus@mail.pl", null, 1234);
        UserDto createdUserDto = new UserDto(1L, "Marcus", "marcus@mail.pl", null, 1234);

        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userService.addUser(userDto)).thenReturn(createdUserDto);
        when(userMapper.mapToUserDto(user)).thenReturn(createdUserDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        //Given
        User createdUserDto = new User(1L, "Marcus", "marcus@mail.pl", false, 1234);
        UserDto updatedUserDto = new UserDto(1L, "Marcus", "marcus@mail.pl", false, 1234);

        when(userService.update(any(UserDto.class))).thenReturn(updatedUserDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedUserDto);

        //When &Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.attendingParty").value(false));
    }

    @Test
    void shouldRemoveUser() throws Exception {
        //Given
        User user = new User(2L, "David", "dav@onet.pl", true, 1236);
        doNothing().when(userService).remove(user.getUserId());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/users/2"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        //Given
        List<UserDto> usersDto = List.of(new UserDto(1L, "Marlon", "marlon@mail.com", true, 258),
                new UserDto(2L, "Monica", "monica@o2.pl", false, 698),
                new UserDto(3L, "Olivia", "oli@o2.pl", true, 789));

        when(userService.getAll()).thenReturn(usersDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));

    }

    @Test
    void shouldFetchOneUser() throws Exception {
        UserDto userDto = new UserDto(5L, "Tom", "tom@onet.pl", true, 569);
        when(userService.getUser(5)).thenReturn(userDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/users/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Tom"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(5))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldConfirmParticipationInEvent() throws Exception {
        //Given
        User user = new User(3L, "Olaf", "olo@onet.pl", null, 369);
        Event event = new Event(1L, "Garden Party", LocalDate.of(2021, 6, 10),
                LocalTime.of(14,0), LocalDate.of(2021, 6, 10), "The best party");

        doNothing().when(userService).confirmParticipation(user.getUserId(), event.getEventId());

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                       .put("/v1/users/confirm?userId=3&eventId=1")
                       .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
