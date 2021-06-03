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
import pl.jolawitaszak.party.domain.EventDto;
import pl.jolawitaszak.party.domain.GpsPositionDto;
import pl.jolawitaszak.party.domain.UserDto;
import pl.jolawitaszak.party.service.EventService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    void shouldDeleteEvent() throws Exception {
        //Given
        Event event = new Event(1L, "Party", LocalDate.of(2021, 5, 30),
                LocalTime.of(18,30), LocalDate.of(2021, 5, 31), "description");

        doNothing().when(eventService).removeEvent(event.getEventId());

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/events/1"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldFetchOneEvent() throws Exception {
        //Given
        EventDto eventDto = new EventDto(3L, "Party", LocalDate.of(2021, 5, 30),
                LocalTime.of(18,30), LocalDate.of(2021, 5, 31), "description");

        when(eventService.getEvent(3)).thenReturn(eventDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/events/3"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventId", Matchers.is(3)));
    }

    @Test
    void shouldFetchAllEvents() throws Exception {
        //Given
        List<EventDto> eventsDto = List.of(new EventDto(1L, "Party", LocalDate.of(2021, 5, 30),
                LocalTime.of(18,30), LocalDate.of(2021, 5, 31), "description"),
                new EventDto(2L, "test party", LocalDate.of(2021, 5, 30),
                        LocalTime.of(18,30), LocalDate.of(2021, 5, 31), " test description"));

        when(eventService.getAll()).thenReturn(eventsDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/events"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void shouldAddGuest() throws Exception {
        //Given
        EventDto eventDto = new EventDto(1L, "event", LocalDate.now(), LocalTime.now(), null, "description");
        UserDto anyUserDto = new UserDto(1L, "Adam", "adam@mail.pl", null, 123);

        Set<UserDto> guests = new HashSet<>();
        guests.add(anyUserDto);

        when(eventService.inviteGuests(eventDto.getEventId(), 2L)).thenReturn(guests);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(guests);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/v1/events/guests?eventId=1&userId=1")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .characterEncoding("utf-8")
                    .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldRemoveGuest() throws Exception {
        //Given
        EventDto eventDto = new EventDto(1L, "event", LocalDate.now(), LocalTime.now(), null, "description");
        UserDto anyUserDto = new UserDto(1L, "Adam", "adam@mail.pl", null, 123);

        doNothing().when(eventService).removeGuests(eventDto.getEventId(), anyUserDto.getUserId());

        //  When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/events/guests/1/1"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldAddLocation() throws Exception {
        //Given
        EventDto eventDto = new EventDto(2L, "Test party", LocalDate.of(2021, 5, 29),
                LocalTime.of(20, 30), null, "description");
        GpsPositionDto gpsPositionDto = new GpsPositionDto(1L, "test place", 51.26, 18.32);
        Set<GpsPositionDto> locations = new HashSet<>();
        locations.add(gpsPositionDto);
        when(eventService.addGpsPosition(eventDto.getEventId(), gpsPositionDto.getGpsId())).thenReturn(locations);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(locations);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/events/location?eventId=2&gpsPositionId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldRemoveLocation() throws Exception {
        //Given
        EventDto eventDto = new EventDto(2L, "Test party", LocalDate.of(2021, 5, 29),
                LocalTime.of(20, 30), null, "description");
        GpsPositionDto gpsPositionDto = new GpsPositionDto(1L, "test place", 51.26, 18.32);

        doNothing().when(eventService).removeGpsPosition(eventDto.getEventId(), gpsPositionDto.getGpsId());

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/events/location/2/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
