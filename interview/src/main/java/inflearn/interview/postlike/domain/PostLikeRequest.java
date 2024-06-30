package inflearn.interview.postlike.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostLikeRequest {
    private Long postId;
    private Long userId;

    @Builder
    public PostLikeRequest(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }
}
