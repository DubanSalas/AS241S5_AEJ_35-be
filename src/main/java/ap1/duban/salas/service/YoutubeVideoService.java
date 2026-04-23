package ap1.duban.salas.service;

import ap1.duban.salas.model.YoutubeVideo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface YoutubeVideoService {
    Flux<YoutubeVideo> findAll();
    Mono<YoutubeVideo> findById(Long id);
    Flux<YoutubeVideo> search(String query);
    Mono<YoutubeVideo> update(Long id, String query);
}
