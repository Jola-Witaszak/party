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

    private final GsmServiceConfig gsmServiceConfig;
    private final RestTemplate restTemplate;


    public SmsSendResponseDto send (Sms sms) {

        URI url = getUri(sms);
        return restTemplate.postForObject(url,null, SmsSendResponseDto.class);
    }

    private URI getUri(Sms sms) {
        return UriComponentsBuilder.fromHttpUrl(gsmServiceConfig.getBaseUrl())
                .queryParam("login", gsmServiceConfig.getLogin())
                .queryParam("pass", gsmServiceConfig.getPassword())
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
