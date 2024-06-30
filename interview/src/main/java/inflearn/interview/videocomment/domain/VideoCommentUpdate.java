package inflearn.interview.videocomment.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoCommentUpdate {
    private Long userId;
    private Long commentId;
    private String content;

    @Builder
    public VideoCommentUpdate(Long userId, Long commentId, String content) {
        this.userId = userId;
        this.commentId = commentId;
        this.content = content;
    }
}
