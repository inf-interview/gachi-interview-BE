package inflearn.interview.video.controller.response;

import inflearn.interview.user.domain.User;
import inflearn.interview.video.domain.Video;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VideoDetailResponse {
    private Long userId;
    private String userName;
    private Long videoId;
    private boolean exposure;
    private String videoTitle;
    private LocalDateTime time;
    private LocalDateTime updateTime;
    private int numOfLike;
    private String[] tags;
    private String thumbnailLink;
    private String videoLink;
    private String image;
    private boolean isLiked;

    @Builder
    public VideoDetailResponse(Long userId, String userName, Long videoId, boolean exposure, String videoTitle, LocalDateTime time, LocalDateTime updateTime, int numOfLike, String[] tags, String thumbnailLink, String videoLink, String image, boolean isLiked) {
        this.userId = userId;
        this.userName = userName;
        this.videoId = videoId;
        this.exposure = exposure;
        this.videoTitle = videoTitle;
        this.time = time;
        this.updateTime = updateTime;
        this.numOfLike = numOfLike;
        this.tags = tags;
        this.thumbnailLink = thumbnailLink;
        this.videoLink = videoLink;
        this.image = image;
        this.isLiked = isLiked;
    }

    public static VideoDetailResponse from(Video video, User user, boolean isLiked) {
        return VideoDetailResponse.builder()
                .userId(user.getId())
                .userName(user.getName())
                .videoId(video.getId())
                .exposure(video.getExposure())
                .videoTitle(video.getVideoTitle())
                .time(video.getTime())
                .updateTime(video.getUpdatedTime())
                .numOfLike(video.getNumOfLike())
                .tags(tagConverter(video.getTag()))
                .thumbnailLink(video.getThumbnailLink())
                .videoLink(video.getVideoLink())
                .image(user.getImage())
                .isLiked(isLiked)
                .build();
    }

    private static String[] tagConverter(String tag) {
        return tag.split("[.]");
    }
}
