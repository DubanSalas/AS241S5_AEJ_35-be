package ap1.duban.salas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${rapidapi.apikey}")
    private String rapidApiKey;

    @Value("${rapidapi.youtube.url}")
    private String youtubeUrl;

    @Value("${rapidapi.youtube.host}")
    private String youtubeHost;

    @Value("${rapidapi.spotify.url}")
    private String spotifyUrl;

    @Value("${rapidapi.spotify.host}")
    private String spotifyHost;

    @Bean(name = "youtubeWebClient")
    public WebClient youtubeWebClient() {
        return WebClient.builder()
                .baseUrl(youtubeUrl)
                .defaultHeader("x-rapidapi-host", youtubeHost)
                .defaultHeader("x-rapidapi-key", rapidApiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Bean(name = "spotifyWebClient")
    public WebClient spotifyWebClient() {
        return WebClient.builder()
                .baseUrl(spotifyUrl)
                .defaultHeader("x-rapidapi-host", spotifyHost)
                .defaultHeader("x-rapidapi-key", rapidApiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
