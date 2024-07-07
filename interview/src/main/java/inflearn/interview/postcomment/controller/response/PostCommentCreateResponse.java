package inflearn.interview.postcomment.controller.response;

import inflearn.interview.postcomment.domain.PostComment;
import inflearn.interview.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCommentCreateResponse {

    private Long commentId;
    private Long userId;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private String image;

    @Builder
    public PostCommentCreateResponse(Long commentId, Long userId, String username, String content, LocalDateTime createdAt, String image) {
        this.commentId = commentId;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
        this.image = image;
    }

    public static PostCommentCreateResponse from(PostComment postComment, User user) {
        return PostCommentCreateResponse.builder()
                .commentId(postComment.getId())
                .userId(user.getId())
                .username(user.getName())
                .content(postComment.getContent())
                .createdAt(postComment.getCreatedAt())
                .image(user.getImage())
                .build();
    }
}
