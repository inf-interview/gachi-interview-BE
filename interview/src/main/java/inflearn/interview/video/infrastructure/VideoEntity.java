package inflearn.interview.video.infrastructure;

import inflearn.interview.video.domain.Video;
import inflearn.interview.videocomment.infrastructure.VideoCommentEntity;
import inflearn.interview.videolike.domain.VideoLike;
import inflearn.interview.videoquestion.domain.VideoQuestion;
import inflearn.interview.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "video")
public class VideoEntity {

    @Id
    @Column(name = "video_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATED_AT")
    private LocalDateTime time;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedTime;

    @Column(name = "public")
    private Boolean exposure;

    @Column(name = "video_title")
    private String videoTitle;

    @Column(name = "img_link")
    private String thumbnailLink;

    private int numOfLike;

    @Column(name = "video_link")
    private String videoLink;

    @Column(name = "tag")
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE)
    List<VideoCommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE)
    List<VideoLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE)
    List<VideoQuestion> videoQuestions = new ArrayList<>();

    public static VideoEntity fromModel(Video video) {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.id = video.getId();
        videoEntity.time = video.getTime();
        videoEntity.updatedTime = video.getUpdatedTime();
        videoEntity.exposure = video.getExposure();
        videoEntity.videoTitle = video.getVideoTitle();
        videoEntity.thumbnailLink = video.getThumbnailLink();
        videoEntity.numOfLike = video.getNumOfLike();
        videoEntity.videoLink = video.getVideoLink();
        videoEntity.tag = video.getTag();
        videoEntity.userEntity = UserEntity.fromModel(video.getUser());
        return videoEntity;
    }

    public Video toModel() {
        return Video.builder()
                .id(id)
                .time(time)
                .updatedTime(updatedTime)
                .exposure(exposure)
                .videoTitle(videoTitle)
                .thumbnailLink(thumbnailLink)
                .numOfLike(numOfLike)
                .videoLink(videoLink)
                .tag(tag)
                .user(userEntity.toModel())
                .build();
    }
}