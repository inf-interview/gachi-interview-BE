package inflearn.interview.domain.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video")
public class VideoDAO {

    @Id
    Long videoId;
    @Column(name = "CREATED_AT")
    LocalDateTime time;
    @Column(name = "UPDATED_AT")
    LocalDateTime updatedTime;
    @NotNull
//    @Column(name = "public")
    Boolean exposure;
    @NotNull
    @Column(name = "video_title")
    String videoTitle;
    @NotNull
    @Column(name = "video_link")
    String videoLink;
    @Column(name = "tag")
    String rawTags;


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_id")
    UserDAO user;
}