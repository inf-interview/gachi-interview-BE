package inflearn.interview.post.infrastructure;

import inflearn.interview.post.domain.Post;
import inflearn.interview.postcomment.domain.PostComment;
import inflearn.interview.postlike.infrastructure.PostLikeEntity;
import inflearn.interview.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostComment> postComments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostLikeEntity> postLikeEntities;

    private String title;

    private String content;

    private String tag;

    private String category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int numOfLike;

    public static PostEntity fromModel(Post post) {
        PostEntity postEntity = new PostEntity();
        postEntity.id = post.getId();
        postEntity.userEntity = UserEntity.fromModel(post.getUser());
        postEntity.title = post.getTitle();
        postEntity.content = post.getContent();
        postEntity.tag = post.getTag();
        postEntity.category = post.getCategory();
        postEntity.createdAt = post.getCreatedAt();
        postEntity.updatedAt = post.getUpdatedAt();
        postEntity.numOfLike = post.getNumOfLike();
        return postEntity;
    }

    public Post toModel() {
        return Post.builder()
                .id(id)
                .user(userEntity.toModel())
                .title(title)
                .content(content)
                .tag(tag)
                .category(category)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .numOfLike(numOfLike)
                .build();
    }

    @Builder
    public PostEntity(Long id, UserEntity userEntity, String title, String content, String tag, String category, LocalDateTime createdAt, LocalDateTime updatedAt, int numOfLike) {
        this.id = id;
        this.userEntity = userEntity;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.numOfLike = numOfLike;
    }

    public PostEntity() {

    }
}
