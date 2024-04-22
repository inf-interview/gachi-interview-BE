package inflearn.interview.domain.dto;

import inflearn.interview.domain.PostComment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostCommentDTO {

    private Long commentId;

    private Long userId;

    private String username;

    private String content;

    private LocalDateTime createdAt;

    public PostCommentDTO() {
    }

    public PostCommentDTO(PostComment postComment) {
        this.commentId = postComment.getPostCommentId();
        this.userId = postComment.getUser().getUserId();
        this.username = postComment.getUser().getName();
        this.content = postComment.getContent();
    }
}
