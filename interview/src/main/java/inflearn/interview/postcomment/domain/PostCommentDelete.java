package inflearn.interview.postcomment.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCommentDelete {
    private Long userId;
    private Long commentId;

    @Builder
    public PostCommentDelete(Long userId, Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }
}
