package inflearn.interview.feedback.controller.response;

import lombok.Getter;

@Getter
public class GptCountResponse {

    private Integer maxCount;
    private Integer currentCount;

    public GptCountResponse(Integer maxCount, Integer currentCount) {
        this.maxCount = maxCount;
        this.currentCount = currentCount;
    }
}
