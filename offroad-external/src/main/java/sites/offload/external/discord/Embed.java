package sites.offload.external.discord;

public record Embed(String title, String description) {

    public Embed {
        //title 최대 256자
        title = title.length() > 256 ? title.substring(0, 256) : title;

        //description 최대 2048자
        description = description.length() > 2048 ? description.substring(0, 2048) : description;
    }
}
