package inflearn.interview.videolike.domain;

import lombok.Getter;

@Getter
public class VideoLikeRequest {
    private Long userId;
    private Long videoId;
}
