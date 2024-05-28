package inflearn.interview.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeNumDTO {
    private int numOfLike;

    public LikeNumDTO(int numOfLike) {
        this.numOfLike = numOfLike;
    }
}
