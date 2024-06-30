package inflearn.interview.video.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoDelete {
    private Long userId;
    private Long videoId;

    @Builder
    public VideoDelete(Long userId, Long videoId) {
        this.userId = userId;
        this.videoId = videoId;
    }
}
