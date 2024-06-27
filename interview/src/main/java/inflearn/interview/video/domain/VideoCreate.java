package inflearn.interview.video.domain;

import lombok.Getter;

@Getter
public class VideoCreate {
    private Long userId;
    private boolean exposure;
    private String videoLink;
    private String videoTitle;
    private Long[] questions;
    private String[] tags;
    private String thumbnailLink;
}
