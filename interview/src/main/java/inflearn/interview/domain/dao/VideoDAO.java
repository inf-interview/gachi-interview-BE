package inflearn.interview.domain.dao;

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
public class VideoDAO {

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
    @NotNull
    @Column(name = "video_link")
    String videoLink;
    @Column(name = "tag")
    String rawTags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserDAO user;

    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE)
    List<VideoCommentDAO> comments = new ArrayList<>();

    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE)
    List<VideoLikeDAO> likes = new ArrayList<>();
}