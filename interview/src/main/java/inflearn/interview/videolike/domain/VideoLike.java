package inflearn.interview.videolike.domain;

import inflearn.interview.user.domain.User;
import inflearn.interview.video.domain.Video;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VideoLike {
    private Long id;
    private LocalDateTime time;
    private LocalDateTime updatedTime;
    private User user;
    private Video video;

    @Builder
    public VideoLike(Long id, LocalDateTime time, LocalDateTime updatedTime, User user, Video video) {
        this.id = id;
        this.time = time;
        this.updatedTime = updatedTime;
        this.user = user;
        this.video = video;
    }

    public static VideoLike from(User user, Video video) {
        return VideoLike.builder()
                .time(LocalDateTime.now())
                .user(user)
                .video(video)
                .build();
    }
}
