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
    private String userName;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public PostCommentCreateResponse(Long commentId, Long userId, String userName, String content, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static PostCommentCreateResponse from(PostComment postComment, User user) {
        return PostCommentCreateResponse.builder()
                .commentId(postComment.getId())
                .userId(user.getId())
                .userName(user.getName())
                .content(postComment.getContent())
                .createdAt(postComment.getCreatedAt())
                .build();
    }
}
