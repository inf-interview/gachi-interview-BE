package inflearn.interview.user.infrastructure;

import inflearn.interview.user.domain.User;
import inflearn.interview.video.domain.Video;
import inflearn.interview.videocomment.domain.VideoComment;
import inflearn.interview.videolike.domain.VideoLike;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_table")
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email
    private String email;

    private String social;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private String image;

    private String refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Video> videos = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<VideoComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<VideoLike> likes = new ArrayList<>();

    private String role;

    private Integer questionGptCallCount = 0;

    private Integer interviewGptCallCount = 0;

    private LocalDateTime questionGptCallTime;

    private LocalDateTime interviewGptCallTime;

    public static UserEntity fromModel(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.id = user.getId();
        userEntity.name = user.getName();
        userEntity.email = user.getEmail();
        userEntity.social = user.getSocial();
        userEntity.createdAt = user.getCreatedAt();
        userEntity.updatedAt = user.getCreatedAt();
        userEntity.image = user.getImage();
        userEntity.refreshToken = user.getRefreshToken();
        userEntity.role = user.getRole();
        userEntity.questionGptCallCount = user.getQuestionGptCallCount();
        userEntity.interviewGptCallCount = user.getInterviewGptCallCount();
        userEntity.questionGptCallTime = user.getQuestionGptCallTime();
        userEntity.interviewGptCallTime = user.getInterviewGptCallTime();
        return userEntity;
    }

    public User toModel() {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .social(social)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .image(image)
                .refreshToken(refreshToken)
                .role(role)
                .questionGptCallCount(questionGptCallCount)
                .interviewGptCallCount(interviewGptCallCount)
                .questionGptCallTime(questionGptCallTime)
                .interviewGptCallTime(interviewGptCallTime)
                .build();
    }



}
