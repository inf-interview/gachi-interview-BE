package inflearn.interview.videocomment.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoCommentDelete {
    private Long userId;
    private Long commentId;

    @Builder
    public VideoCommentDelete(Long userId, Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }
}
