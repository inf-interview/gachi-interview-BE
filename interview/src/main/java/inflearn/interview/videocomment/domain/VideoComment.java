package inflearn.interview.videocomment.domain;

import inflearn.interview.user.domain.User;
import inflearn.interview.video.domain.Video;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VideoComment {
    private Long id;
    private String content;
    private LocalDateTime time;
    private LocalDateTime updatedTime;
    private User user;
    private Video video;

    @Builder
    public VideoComment(Long id, String content, LocalDateTime time, LocalDateTime updatedTime, User user, Video video) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.updatedTime = updatedTime;
        this.user = user;
        this.video = video;
    }

    public static VideoComment from(VideoCommentCreate videoCommentCreate, User user, Video video) {
        return VideoComment.builder()
                .content(videoCommentCreate.getContent())
                .time(LocalDateTime.now())
                .user(user)
                .video(video)
                .build();
    }

    public VideoComment update(VideoCommentUpdate videoCommentUpdate) {
        return VideoComment.builder()
                .id(id)
                .content(videoCommentUpdate.getContent())
                .time(time)
                .updatedTime(LocalDateTime.now())
                .user(user)
                .video(video)
                .build();
    }
}
