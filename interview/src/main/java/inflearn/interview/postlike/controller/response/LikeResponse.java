package inflearn.interview.postlike.controller.response;

import inflearn.interview.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeResponse {

    private int numOfLike;
    private boolean isLiked;

    @Builder
    public LikeResponse(int numOfLike, boolean isLiked) {
        this.numOfLike = numOfLike;
        this.isLiked = isLiked;
    }

    public static LikeResponse from(boolean isLiked, Post post) {
        return LikeResponse.builder()
                .numOfLike(post.getNumOfLike())
                .isLiked(isLiked)
                .build();
    }
}
