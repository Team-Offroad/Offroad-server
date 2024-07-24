package sites.offload.external.discord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import sites.offload.external.enums.RestClientUseCase;
import sites.offload.external.exception.RestClientException;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiscordService {

    @Value("${discord.webhook-url}")
    private String WEBHOOK_URL;

    public void sendMessage(final DiscordWebhookRequest discordWebhookRequest) {
        final RestClient restClient = RestClient.create();
        try {
            restClient.post()
                    .uri(WEBHOOK_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(discordWebhookRequest)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            (request, response) -> {
                                throw new RestClientException(RestClientUseCase.DISCORD_WEBHOOK,
                                        response.getBody().toString(), (HttpStatus) response.getStatusCode());
                            }
                    );
        } catch (RestClientException exception) {
            log.error(exception.getMessage(), exception);
        }
    }
}

