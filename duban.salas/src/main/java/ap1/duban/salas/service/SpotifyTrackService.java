package ap1.duban.salas.service;

import ap1.duban.salas.model.SpotifyTrack;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SpotifyTrackService {
    Flux<SpotifyTrack> findAll();
    Mono<SpotifyTrack> findById(Long id);
    Mono<SpotifyTrack> search(String artist, String title);
    Mono<SpotifyTrack> update(Long id, String artist, String title);
}
