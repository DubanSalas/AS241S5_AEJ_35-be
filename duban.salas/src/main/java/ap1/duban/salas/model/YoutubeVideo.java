package ap1.duban.salas.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(name = "youtube_video")
public class YoutubeVideo {

    @Id
    private Long id; // Identificador único

    private String query; // Término de búsqueda
    private String title; // Título del video
    private String channel; // Canal de YouTube

    @Column("video_id")
    private String videoId; // ID del video en YouTube

    @Column("thumbnail_url")
    private String thumbnailUrl; // URL de la miniatura

    @Column("creation_date")
    private LocalDateTime creationDate; // Fecha de creación

    @Column("update_date")
    private LocalDateTime updateDate; // Fecha de actualización
}
