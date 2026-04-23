package ap1.duban.salas.repository;

import ap1.duban.salas.model.YoutubeVideo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YoutubeVideoRepository extends ReactiveCrudRepository<YoutubeVideo, Long> {
}
