package inflearn.interview.video.domain;

import lombok.Getter;

@Getter
public class VideoUpdate {
    private Long userId;
    private Long videoId;
    private boolean exposure;
    private String videoTitle;
    private String[] tags;
}
