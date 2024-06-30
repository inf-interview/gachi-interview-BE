package inflearn.interview.post.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDelete {
    private Long postId;
    private Long userId;

    @Builder
    public PostDelete(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }
}
