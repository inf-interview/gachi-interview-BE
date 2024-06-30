package inflearn.interview.video.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoUpdate {
    private Long userId;
    private Long videoId;
    private boolean exposure;
    private String videoTitle;
    private String[] tags;

    @Builder
    public VideoUpdate(Long userId, Long videoId, boolean exposure, String videoTitle, String[] tags) {
        this.userId = userId;
        this.videoId = videoId;
        this.exposure = exposure;
        this.videoTitle = videoTitle;
        this.tags = tags;
    }
}
