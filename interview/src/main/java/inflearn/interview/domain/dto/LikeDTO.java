package inflearn.interview.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDTO {
    private int numOfLike;
    private boolean isLiked;

    public LikeDTO(int numOfLike, boolean isLiked) {
        this.numOfLike = numOfLike;
        this.isLiked = isLiked;
    }
}
