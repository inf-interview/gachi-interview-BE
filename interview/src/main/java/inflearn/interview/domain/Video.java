package inflearn.interview.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "video")
public class Video {

    @Id
    @Column(name = "video_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long videoId;
    @Column(name = "CREATED_AT")
    LocalDateTime time;
    @Column(name = "UPDATED_AT")
    LocalDateTime updatedTime;
    @NotNull
    @Column(name = "public")
    Boolean exposure;
    @NotNull
    @Column(name = "video_title")
    String videoTitle;
    @Column(name = "img_link")
    String thumbnailLink;

    @NotNull
    @Column(name = "video_link")
    String videoLink;
    @Column(name = "tag")
    String rawTags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE)
    List<VideoComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE)
    List<VideoLike> likes = new ArrayList<>();
}