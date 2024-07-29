package site.offload.external.discord;

import java.util.List;

public record DiscordWebhookRequest(String content, List<Embed> embeds) {

    public DiscordWebhookRequest {
        // content 최대 2000자
        content = content.length() > 2000 ? content.substring(0, 2000) : content;

        // embeds 최대 10개
        embeds = embeds.size() > 10 ? embeds.subList(0, 10) : embeds;
    }
}
