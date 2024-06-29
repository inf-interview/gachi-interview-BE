package inflearn.interview.postlike.infrastructure;

import inflearn.interview.post.infrastructure.PostEntity;
import inflearn.interview.postlike.domain.PostLike;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.post.domain.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "post_like")
public class PostLikeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static PostLikeEntity fromModel(PostLike postLike) {
        PostLikeEntity postLikeEntity = new PostLikeEntity();
        postLikeEntity.postEntity = PostEntity.fromModel(postLike.getPost());
        postLikeEntity.userEntity = UserEntity.fromModel(postLike.getUser());
        postLikeEntity.createdAt = postLike.getCreatedAt();
        postLikeEntity.updatedAt = postLike.getUpdatedAt();
        return postLikeEntity;
    }

    public PostLike toModel() {
        return PostLike.builder()
                .id(id)
                .post(postEntity.toModel())
                .user(userEntity.toModel())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
