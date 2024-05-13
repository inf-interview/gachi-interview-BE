package inflearn.interview.domain.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video_like")
public class VideoLikeDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    @Column(name = "created_at")
    LocalDateTime time;

    @Column(name = "updated_at")
    LocalDateTime updatedTime;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserDAO user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private VideoDAO video;
}