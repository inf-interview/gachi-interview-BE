package inflearn.interview.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoDTO2 implements BaseDTO{

    private Long userId;
    private boolean exposure = true;
    private String videoLink;
    private String videoTitle;
    private Long[] questions;
    private String[] tags;
    private String thumbnailLink;

    private String userName;
    private Long videoId;
    private LocalDateTime time;
    private LocalDateTime updateTime;
    private int numOfLike;

    @QueryProjection
    public VideoDTO2(Long userId, String userName, Long videoId, String videoLink, String videoTitle, LocalDateTime time, LocalDateTime updateTime, int numOfLike, String tag, String thumbnailLink) {
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

    }

    private String[] entityToDtoTagConverter(String tag) {
        return tag.split("[.]");
    }

}
