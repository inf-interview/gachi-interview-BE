package inflearn.interview.video.domain;

import inflearn.interview.video.infrastructure.VideoEntity;
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
    private String image;

    private String[] entityToDtoTagConverter(String tag) {
        return tag.split("[.]");
    }
    public MyVideoDTO(VideoEntity videoEntity, Long count) {
        this.userId = videoEntity.getUserEntity().getUserId();
        this.userName = videoEntity.getUserEntity().getName();
        this.videoId = videoEntity.getId();
        this.videoLink = videoEntity.getVideoLink();
        this.videoTitle = videoEntity.getVideoTitle();
        this.time = videoEntity.getTime();
        this.updateTime = videoEntity.getUpdatedTime();
        this.numOfLike = videoEntity.getNumOfLike();
        this.tags = entityToDtoTagConverter(videoEntity.getTag());
        this.thumbnailLink = videoEntity.getThumbnailLink();
        this.exposure = videoEntity.getExposure();
        this.numOfComment = count.intValue();
        this.image = videoEntity.getUserEntity().getImage();
    }
}
