package inflearn.interview.videolike.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoLikeRequest {
    private Long userId;
    private Long videoId;

    @Builder
    public VideoLikeRequest(Long userId, Long videoId) {
        this.userId = userId;
        this.videoId = videoId;
    }
}
