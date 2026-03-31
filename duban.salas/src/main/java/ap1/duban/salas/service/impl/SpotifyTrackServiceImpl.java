package ap1.duban.salas.service.impl;

import ap1.duban.salas.model.SpotifyTrack;
import ap1.duban.salas.repository.SpotifyTrackRepository;
import ap1.duban.salas.service.SpotifyTrackService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
public class SpotifyTrackServiceImpl implements SpotifyTrackService {

    private final SpotifyTrackRepository repository;
    private final WebClient webClient;

    public SpotifyTrackServiceImpl(SpotifyTrackRepository repository,
                                   @Qualifier("spotifyWebClient") WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    @Override
    public Flux<SpotifyTrack> findAll() {
        log.info("Listando todos los tracks de Spotify");
        return repository.findAll();
    }

    @Override
    public Mono<SpotifyTrack> findById(Long id) {
        log.info("Buscando track por ID = {}", id);
        return repository.findById(id);
    }

    @Override
    public Mono<SpotifyTrack> search(String artist, String title) {
        log.info("Buscando en Spotify: {} - {}", artist, title);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/social/spotify/musixmatchsearchlyrics")
                        .queryParam("terms", title)
                        .queryParam("artist", artist)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> {
                    JsonNode track = json.path("message").path("body").path("track_list").path(0).path("track");
                    SpotifyTrack result = new SpotifyTrack();
                    result.setArtist(artist);
                    result.setTitle(track.path("track_name").asText(title));
                    result.setAlbum(track.path("album_name").asText("unknown"));
                    result.setLyrics(track.path("track_share_url").asText(""));
                    result.setCreationDate(LocalDateTime.now());
                    result.setUpdateDate(LocalDateTime.now());
                    return result;
                })
                .flatMap(repository::save)
                .doOnSuccess(t -> log.info("Guardado: {}", t.getTitle()));
    }

    @Override
    public Mono<SpotifyTrack> update(Long id, String artist, String title) {
        log.info("Actualizando track ID = {}", id);
        return repository.findById(id)
                .flatMap(existing ->
                    webClient.get()
                            .uri(uriBuilder -> uriBuilder
                                    .path("/v1/social/spotify/musixmatchsearchlyrics")
                                    .queryParam("terms", title)
                                    .queryParam("artist", artist)
                                    .build())
                            .retrieve()
                            .bodyToMono(JsonNode.class)
                            .map(json -> {
                                JsonNode track = json.path("message").path("body").path("track_list").path(0).path("track");
                                existing.setArtist(artist);
                                existing.setTitle(track.path("track_name").asText(title));
                                existing.setAlbum(track.path("album_name").asText("unknown"));
                                existing.setLyrics(track.path("track_share_url").asText(""));
                                existing.setUpdateDate(LocalDateTime.now());
                                return existing;
                            })
                            .flatMap(repository::save)
                );
    }
}
