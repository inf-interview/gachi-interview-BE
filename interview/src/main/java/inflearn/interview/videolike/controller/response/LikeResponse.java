package inflearn.interview.videolike.controller.response;

import inflearn.interview.video.domain.Video;
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

    public static LikeResponse from(boolean isLiked, Video video) {
        return LikeResponse.builder()
                .numOfLike(video.getNumOfLike())
                .isLiked(isLiked)
                .build();
    }
}
