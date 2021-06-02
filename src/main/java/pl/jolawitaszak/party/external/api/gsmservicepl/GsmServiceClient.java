package pl.jolawitaszak.party.external.api.gsmservicepl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jolawitaszak.party.domain.Sms;
import pl.jolawitaszak.party.domain.SmsSendResponseDto;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class GsmServiceClient {

    private static final String BASE_URL = "https://api.gsmservice.pl/v5/send.php";
    private static final String LOGIN = "ja_witas13";
    private static final String PASSWORD = "DRRT8QvBXbt8eQV";

    private final RestTemplate restTemplate;
    private Sms sms;

    public SmsSendResponseDto send (Sms sms) {

        URI url = getUri();
        return restTemplate.postForObject(url,null, SmsSendResponseDto.class);
    }

    private URI getUri() {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("login", LOGIN)
                .queryParam("pass", PASSWORD)
                .queryParam("recipient", sms.getSmsTo())
                .queryParam("message", sms.getMessage())
                .queryParam("sender", sms.getSender())
                .queryParam("msg_type", sms.getMsg_type())
                .queryParam("sandbox", sms.getSandbox())
                .build()
                .encode()
                .toUri();
    }
}
