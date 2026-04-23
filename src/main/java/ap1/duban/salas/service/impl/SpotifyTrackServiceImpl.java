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
                .doOnNext(json -> log.info("Spotify response: {}", json))
                .map(json -> {
                    SpotifyTrack result = new SpotifyTrack();
                    result.setArtist(artist);
                    result.setTitle(title);
                    // Los lyrics vienen como array en data[]
                    JsonNode dataNode = json.path("data");
                    if (dataNode.isArray()) {
                        StringBuilder sb = new StringBuilder();
                        dataNode.forEach(line -> sb.append(line.asText()).append("\n"));
                        result.setLyrics(sb.toString().trim());
                    } else {
                        result.setLyrics("");
                    }
                    result.setAlbum("Spotify");
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
                                existing.setArtist(artist);
                                existing.setTitle(title);
                                JsonNode dataNode = json.path("data");
                                if (dataNode.isArray()) {
                                    StringBuilder sb = new StringBuilder();
                                    dataNode.forEach(line -> sb.append(line.asText()).append("\n"));
                                    existing.setLyrics(sb.toString().trim());
                                }
                                existing.setAlbum("Spotify");
                                existing.setUpdateDate(LocalDateTime.now());
                                return existing;
                            })
                            .flatMap(repository::save)
                );
    }
}
