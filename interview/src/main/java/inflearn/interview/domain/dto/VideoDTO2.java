package inflearn.interview.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import inflearn.interview.domain.Video;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoDTO2 implements BaseDTO{

    public interface patch{}
    public interface delete{}

    public interface like{}

    @NotNull(groups = {patch.class, delete.class, like.class})
    private Long userId;
    private boolean exposure = true;
    private String videoLink;
    private String videoTitle;
    private Long[] questions;
    private String[] tags;
    private String thumbnailLink;

    private String userName;
    @NotNull(groups = {delete.class, like.class})
    private Long videoId;
    private LocalDateTime time;
    private LocalDateTime updateTime;
    private int numOfLike;
    private String image;

    private boolean isLiked;

    @QueryProjection
    public VideoDTO2(Long userId, String userName, Long videoId, String videoLink, String videoTitle, LocalDateTime time, LocalDateTime updateTime, int numOfLike, String tag, String thumbnailLink, String image) {
        this.userId = userId;
        this.userName = userName;
        this.videoId = videoId;
        this.videoLink = videoLink;
        this.videoTitle = videoTitle;
        this.time = time;
        this.updateTime = updateTime;
        this.numOfLike = numOfLike;
        if (tag != null) {
            this.tags = entityToDtoTagConverter(tag);
        }
        this.thumbnailLink = thumbnailLink;
        this.image = image;
    }

    public VideoDTO2(Video video) {
        userId = video.getUser().getUserId();
        userName = video.getUser().getName();
        videoId = video.getVideoId();
        exposure = video.getExposure();
        videoTitle = video.getVideoTitle();
        time = video.getTime();
        updateTime = video.getUpdatedTime();
        numOfLike = video.getNumOfLike();
        thumbnailLink = video.getThumbnailLink();
        image = video.getUser().getImage();
        if (video.getTag() != null) {
            this.tags = entityToDtoTagConverter(video.getTag());
        }
        videoLink = video.getVideoLink();
    }



    private String[] entityToDtoTagConverter(String tag) {
        return tag.split("[.]");
    }

}
