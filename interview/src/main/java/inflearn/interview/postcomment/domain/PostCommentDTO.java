package inflearn.interview.postcomment.domain;

import inflearn.interview.common.domain.BaseDTO;
import inflearn.interview.postcomment.infrastructure.PostCommentEntity;
import inflearn.interview.videocomment.infrastructure.VideoCommentEntity;
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

    public PostCommentDTO(PostCommentEntity postCommentEntity) {
        this.commentId = postCommentEntity.getPostCommentId();
        this.userId = postCommentEntity.getUserEntity().getUserId();
        this.username = postCommentEntity.getUserEntity().getName();
        this.content = postCommentEntity.getContent();
        this.postId = postCommentEntity.getPost().getPostId();
        this.category = postCommentEntity.getPost().getCategory();
    }

    public PostCommentDTO(VideoCommentEntity videoCommentEntity) {
        this.commentId = videoCommentEntity.getId();
        this.userId = videoCommentEntity.getUserEntity().getUserId();
        this.username = videoCommentEntity.getUserEntity().getName();
        this.image = videoCommentEntity.getUserEntity().getImage();
        this.content = videoCommentEntity.getContent();
        this.createdAt = videoCommentEntity.getTime();
    }
}
