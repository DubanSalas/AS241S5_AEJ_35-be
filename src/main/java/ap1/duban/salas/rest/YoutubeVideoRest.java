package ap1.duban.salas.rest;

import ap1.duban.salas.model.YoutubeVideo;
import ap1.duban.salas.service.YoutubeVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/youtube")
public class YoutubeVideoRest {

    private final YoutubeVideoService service;

    @Autowired
    public YoutubeVideoRest(YoutubeVideoService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<YoutubeVideo> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<YoutubeVideo> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/save")
    public Flux<YoutubeVideo> save(@RequestBody SearchRequest request) {
        return service.search(request.query());
    }

    @PutMapping("/update/{id}")
    public Mono<YoutubeVideo> update(@PathVariable Long id, @RequestBody SearchRequest request) {
        return service.update(id, request.query());
    }

    public record SearchRequest(String query) {}
}
