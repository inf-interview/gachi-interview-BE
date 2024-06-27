package inflearn.interview.videolike.infrastructure;

import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.video.infrastructure.VideoEntity;
import inflearn.interview.videolike.domain.VideoLike;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video_like")
public class VideoLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime time;

    @Column(name = "updated_at")
    private LocalDateTime updatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private VideoEntity videoEntity;

    public static VideoLikeEntity fromModel(VideoLike videoLike) {
        VideoLikeEntity videoLikeEntity = new VideoLikeEntity();
        videoLikeEntity.id = videoLike.getId();
        videoLikeEntity.time = videoLike.getTime();
        videoLikeEntity.updatedTime = videoLike.getUpdatedTime();
        videoLikeEntity.userEntity = UserEntity.fromModel(videoLike.getUser());
        videoLikeEntity.videoEntity = VideoEntity.fromModel(videoLike.getVideo());
        return videoLikeEntity;
    }

    public VideoLike toModel() {
        return VideoLike.builder()
                .id(id)
                .time(time)
                .updatedTime(updatedTime)
                .user(userEntity.toModel())
                .video(videoEntity.toModel())
                .build();
    }
}