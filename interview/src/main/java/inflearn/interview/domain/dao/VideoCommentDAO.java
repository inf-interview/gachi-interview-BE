package inflearn.interview.domain.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video_comment")
public class VideoCommentDAO {
    @Id
    Long id;

    String content;
    @Column(name = "created_AT")
    LocalDateTime time;
    @Column(name = "UPDATED_AT")
    LocalDateTime updatedTime;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    UserDAO user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    VideoDAO video;
}