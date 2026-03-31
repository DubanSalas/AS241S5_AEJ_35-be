package ap1.duban.salas.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(name = "spotify_track")
public class SpotifyTrack {

    @Id
    private Long id; // Identificador único

    private String artist; // Artista
    private String title; // Título de la canción
    private String lyrics; // Letra de la canción
    private String album; // Álbum

    @Column("creation_date")
    private LocalDateTime creationDate; // Fecha de creación

    @Column("update_date")
    private LocalDateTime updateDate; // Fecha de actualización
}
