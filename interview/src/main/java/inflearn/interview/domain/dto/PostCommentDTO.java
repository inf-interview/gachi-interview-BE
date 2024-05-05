package inflearn.interview.domain.dto;

import inflearn.interview.domain.PostComment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostCommentDTO {

    public interface valid1{} // create, delete
    public interface valid2{} // update

    @NotNull(message = "댓글 아이디 누락", groups = valid2.class)
    private Long commentId;

    @NotNull(message = "유저 아이디 누락", groups = {valid1.class, valid2.class})
    private Long userId;

    private String username;

    @NotBlank(message = "내용은 필수항목 입니다.", groups = {valid1.class, valid2.class})
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
