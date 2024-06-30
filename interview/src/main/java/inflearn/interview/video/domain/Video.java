package inflearn.interview.video.domain;

import inflearn.interview.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Video {
    private Long id;
    private LocalDateTime time;
    private LocalDateTime updatedTime;
    private Boolean exposure;
    private String videoTitle;
    private String thumbnailLink;
    private int numOfLike;
    private String videoLink;
    private String tag;
    private User user;

    @Builder
    public Video(Long id, LocalDateTime time, LocalDateTime updatedTime, Boolean exposure, String videoTitle, String thumbnailLink, int numOfLike, String videoLink, String tag, User user) {
        this.id = id;
        this.time = time;
        this.updatedTime = updatedTime;
        this.exposure = exposure;
        this.videoTitle = videoTitle;
        this.thumbnailLink = thumbnailLink;
        this.numOfLike = numOfLike;
        this.videoLink = videoLink;
        this.tag = tag;
        this.user = user;
    }

    public static Video from(VideoCreate videoCreate, User user) {
        return Video.builder()
                .user(user)
                .time(LocalDateTime.now())
                .exposure(videoCreate.isExposure())
                .videoLink(videoCreate.getVideoLink())
                .videoTitle(videoCreate.getVideoTitle())
                .tag(tagConverter(videoCreate.getTags()))
                .thumbnailLink(videoCreate.getThumbnailLink())
                .numOfLike(0)
                .build();
    }

    public Video update(VideoUpdate videoUpdate) {
        return Video.builder()
                .id(id)
                .user(user)
                .exposure(videoUpdate.isExposure())
                .time(time)
                .updatedTime(LocalDateTime.now())
                .videoLink(videoLink)
                .videoTitle(videoUpdate.getVideoTitle())
                .tag(tagConverter(videoUpdate.getTags()))
                .thumbnailLink(thumbnailLink)
                .build();
    }

    public Video plusLike() {
        return Video.builder()
                .id(id)
                .time(time)
                .updatedTime(updatedTime)
                .exposure(exposure)
                .videoTitle(videoTitle)
                .thumbnailLink(thumbnailLink)
                .numOfLike(numOfLike + 1)
                .videoLink(videoLink)
                .tag(tag)
                .user(user)
                .build();
    }

    public Video minusLike() {
        return Video.builder()
                .id(id)
                .time(time)
                .updatedTime(updatedTime)
                .exposure(exposure)
                .videoTitle(videoTitle)
                .thumbnailLink(thumbnailLink)
                .numOfLike(numOfLike + 1)
                .videoLink(videoLink)
                .tag(tag)
                .user(user)
                .build();
    }

    private static String tagConverter(String[] tags) {
        if (tags != null) {
            StringBuilder tagMaker = new StringBuilder();
            for (String tag : tags) {
                tagMaker.append(tag).append(".");
            }
            return tagMaker.toString();
        } else {
            return null;
        }
    }
}
