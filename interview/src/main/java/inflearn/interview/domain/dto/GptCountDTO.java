package inflearn.interview.domain.dto;

import lombok.Getter;

@Getter
public class GptCountDTO {

    private Integer maxCount;
    private Integer currentCount;

    public GptCountDTO(Integer maxCount, Integer currentCount) {
        this.maxCount = maxCount;
        this.currentCount = currentCount;
    }
}
