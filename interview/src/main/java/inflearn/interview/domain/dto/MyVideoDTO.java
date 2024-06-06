package inflearn.interview.domain.dto;

import inflearn.interview.domain.Video;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyVideoDTO {
    private Long userId;
    private String userName;
    private Long videoId;
    private String videoLink;
    private String videoTitle;
    private LocalDateTime time;
    private LocalDateTime updateTime;
    private int numOfLike;
    private String[] tags;
    private String thumbnailLink;
    private boolean exposure;
    private int numOfComment;

    private String[] entityToDtoTagConverter(String tag) {
        return tag.split("[.]");
    }
    public MyVideoDTO(Video video, Long count) {
        this.userId = video.getUser().getUserId();
        this.userName = video.getUser().getName();
        this.videoId = video.getVideoId();
        this.videoLink = video.getVideoLink();
        this.videoTitle = video.getVideoTitle();
        this.time = video.getTime();
        this.updateTime = video.getUpdatedTime();
        this.numOfLike = video.getNumOfLike();
        this.tags = entityToDtoTagConverter(video.getTag());
        this.thumbnailLink = video.getThumbnailLink();
        this.exposure = video.getExposure();
        this.numOfComment = count.intValue();
    }
}
