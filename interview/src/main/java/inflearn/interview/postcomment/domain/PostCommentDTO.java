package inflearn.interview.postcomment.domain;

import inflearn.interview.common.domain.BaseDTO;
import inflearn.interview.videocomment.domain.VideoComment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostCommentDTO implements BaseDTO {

    public interface create{}
    public interface update{}
    public interface delete{}

    @NotNull(message = "댓글 아이디 누락", groups = {update.class, delete.class})
    private Long commentId;

    @NotNull(message = "유저 아이디 누락", groups = {create.class, update.class, delete.class})
    private Long userId;

    private String username;

    @NotBlank(message = "내용은 필수항목 입니다.", groups = {create.class, update.class})
    private String content;

    private LocalDateTime createdAt;

    private String image;

    private Long postId;
    private String category;

    public PostCommentDTO() {
    }

    public PostCommentDTO(PostComment postComment) {
        this.commentId = postComment.getPostCommentId();
        this.userId = postComment.getUserEntity().getUserId();
        this.username = postComment.getUserEntity().getName();
        this.content = postComment.getContent();
        this.postId = postComment.getPost().getPostId();
        this.category = postComment.getPost().getCategory();
    }

    public PostCommentDTO(VideoComment videoComment) {
        this.commentId = videoComment.getId();
        this.userId = videoComment.getUserEntity().getUserId();
        this.username = videoComment.getUserEntity().getName();
        this.image = videoComment.getUserEntity().getImage();
        this.content = videoComment.getContent();
        this.createdAt = videoComment.getTime();
    }
}
