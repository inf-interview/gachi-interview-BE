package inflearn.interview.postlike.domain;

import inflearn.interview.post.domain.Post;
import inflearn.interview.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostLike {
    private Long id;
    private Post post;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public PostLike(Long id, Post post, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostLike from(Post post, User user) {
        return PostLike.builder()
                .post(post)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
