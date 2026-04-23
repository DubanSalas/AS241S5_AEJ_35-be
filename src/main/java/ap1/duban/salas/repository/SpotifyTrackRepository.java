package ap1.duban.salas.repository;

import ap1.duban.salas.model.SpotifyTrack;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotifyTrackRepository extends ReactiveCrudRepository<SpotifyTrack, Long> {
}
