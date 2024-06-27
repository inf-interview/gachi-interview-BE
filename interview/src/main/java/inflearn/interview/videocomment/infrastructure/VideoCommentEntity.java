package inflearn.interview.videocomment.infrastructure;

import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.video.infrastructure.VideoEntity;
import inflearn.interview.videocomment.domain.VideoComment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video_comment")
public class VideoCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "created_AT")
    private LocalDateTime time;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private VideoEntity videoEntity;

    public static VideoCommentEntity fromModel(VideoComment videoComment) {
        VideoCommentEntity videoCommentEntity = new VideoCommentEntity();
        videoCommentEntity.id = videoComment.getId();
        videoCommentEntity.content = videoComment.getContent();
        videoCommentEntity.time = videoComment.getTime();
        videoCommentEntity.updatedTime = videoComment.getUpdatedTime();
        videoCommentEntity.userEntity = UserEntity.fromModel(videoComment.getUser());
        videoCommentEntity.videoEntity = VideoEntity.fromModel(videoComment.getVideo());
        return videoCommentEntity;
    }

    public VideoComment toModel() {
        return VideoComment.builder()
                .id(id)
                .content(content)
                .time(time)
                .updatedTime(updatedTime)
                .user(userEntity.toModel())
                .video(videoEntity.toModel())
                .build();
    }
}