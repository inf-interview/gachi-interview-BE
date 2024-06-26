package inflearn.interview.postcomment.domain;

import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.post.domain.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public PostComment(UserEntity userEntity, Post post, String content) {
        this.userEntity = userEntity;
        this.post = post;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
