package inflearn.interview.postcomment.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCommentUpdate {
    private Long userId;
    private Long commentId;
    private String content;

    @Builder
    public PostCommentUpdate(Long userId, Long commentId, String content) {
        this.userId = userId;
        this.commentId = commentId;
        this.content = content;
    }
}
