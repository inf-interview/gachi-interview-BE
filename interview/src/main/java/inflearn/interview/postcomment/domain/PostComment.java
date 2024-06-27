package inflearn.interview.postcomment.domain;

import inflearn.interview.post.domain.Post;
import inflearn.interview.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostComment {
    private Long id;
    private Post post;
    private User user;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public PostComment(Long id, Post post, User user, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostComment from(PostCommentCreate postCommentCreate, Post post, User user) {
        return PostComment.builder()
                .post(post)
                .user(user)
                .content(postCommentCreate.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public PostComment update(PostCommentUpdate postCommentUpdate) {
        return PostComment.builder()
                .post(post)
                .user(user)
                .content(postCommentUpdate.getContent())
                .updatedAt(LocalDateTime.now())
                .createdAt(createdAt)
                .build();
    }
}
