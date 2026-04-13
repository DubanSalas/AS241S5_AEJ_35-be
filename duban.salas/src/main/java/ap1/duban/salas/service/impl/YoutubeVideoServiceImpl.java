package ap1.duban.salas.service.impl;

import ap1.duban.salas.model.YoutubeVideo;
import ap1.duban.salas.repository.YoutubeVideoRepository;
import ap1.duban.salas.service.YoutubeVideoService;
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
public class YoutubeVideoServiceImpl implements YoutubeVideoService {

    private final YoutubeVideoRepository repository;
    private final WebClient webClient;

    public YoutubeVideoServiceImpl(YoutubeVideoRepository repository,
                                   @Qualifier("youtubeWebClient") WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    @Override
    public Flux<YoutubeVideo> findAll() {
        log.info("Listando todos los videos de YouTube");
        return repository.findAll();
    }

    @Override
    public Mono<YoutubeVideo> findById(Long id) {
        log.info("Buscando video por ID = {}", id);
        return repository.findById(id);
    }

    @Override
    public Flux<YoutubeVideo> search(String query) {
        log.info("Buscando en YouTube: {}", query);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("query", query)
                        .queryParam("type", "video")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .doOnNext(json -> log.info("YouTube response: {}", json))
                .flatMapMany(json -> Flux.fromIterable(json.path("data")))
                .map(item -> {
                    YoutubeVideo video = new YoutubeVideo();
                    video.setQuery(query);
                    video.setVideoId(item.path("videoId").asText());
                    video.setTitle(item.path("title").asText());
                    video.setChannel(item.path("channelTitle").asText());
                    // Intentar diferentes paths para el thumbnail
                    String thumb = item.path("thumbnail").path(0).path("url").asText();
                    if (thumb.isEmpty()) thumb = item.path("thumbnail").path("thumbnails").path(0).path("url").asText();
                    video.setThumbnailUrl(thumb);                    video.setCreationDate(LocalDateTime.now());
                    video.setUpdateDate(LocalDateTime.now());
                    return video;
                })
                .flatMap(repository::save)
                .doOnNext(v -> log.info("Guardado: {}", v.getTitle()));
    }

    @Override
    public Mono<YoutubeVideo> update(Long id, String query) {
        log.info("Actualizando video ID = {}", id);
        return repository.findById(id)
                .flatMap(existing ->
                    webClient.get()
                            .uri(uriBuilder -> uriBuilder
                                    .path("/search")
                                    .queryParam("query", query)
                                    .queryParam("type", "video")
                                    .build())
                            .retrieve()
                            .bodyToMono(JsonNode.class)
                            .map(json -> {
                                JsonNode item = json.path("data").path(0);
                                existing.setQuery(query);
                                existing.setVideoId(item.path("videoId").asText());
                                existing.setTitle(item.path("title").asText());
                                existing.setChannel(item.path("channelTitle").asText());
                                existing.setThumbnailUrl(item.path("thumbnail").path("thumbnails").path(0).path("url").asText());
                                existing.setUpdateDate(LocalDateTime.now());
                                return existing;
                            })
                            .flatMap(repository::save)
                );
    }
}
