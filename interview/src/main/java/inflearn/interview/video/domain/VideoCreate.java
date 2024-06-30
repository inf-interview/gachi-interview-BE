package inflearn.interview.video.domain;

import lombok.Builder;
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

    @Builder
    public VideoCreate(Long userId, boolean exposure, String videoLink, String videoTitle, Long[] questions, String[] tags, String thumbnailLink) {
        this.userId = userId;
        this.exposure = exposure;
        this.videoLink = videoLink;
        this.videoTitle = videoTitle;
        this.questions = questions;
        this.tags = tags;
        this.thumbnailLink = thumbnailLink;
    }
}
