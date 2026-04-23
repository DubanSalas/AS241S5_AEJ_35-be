package ap1.duban.salas.rest;

import ap1.duban.salas.model.SpotifyTrack;
import ap1.duban.salas.service.SpotifyTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/spotify")
public class SpotifyTrackRest {

    private final SpotifyTrackService service;

    @Autowired
    public SpotifyTrackRest(SpotifyTrackService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<SpotifyTrack> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<SpotifyTrack> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/save")
    public Mono<SpotifyTrack> save(@RequestBody TrackRequest request) {
        return service.search(request.artist(), request.title());
    }

    @PutMapping("/update/{id}")
    public Mono<SpotifyTrack> update(@PathVariable Long id, @RequestBody TrackRequest request) {
        return service.update(id, request.artist(), request.title());
    }

    public record TrackRequest(String artist, String title) {}
}
