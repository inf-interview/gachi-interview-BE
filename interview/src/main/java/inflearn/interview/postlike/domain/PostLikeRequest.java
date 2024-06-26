package inflearn.interview.postlike.domain;

import lombok.Getter;

@Getter
public class PostLikeRequest {
    private Long postId;
    private Long userId;
}
