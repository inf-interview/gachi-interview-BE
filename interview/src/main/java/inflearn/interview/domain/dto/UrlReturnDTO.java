package inflearn.interview.domain.dto;

import lombok.Getter;

@Getter
public class UrlReturnDTO {

    private String videoUrl;
    private String thumbnailUrl;

    public UrlReturnDTO(String videoUrl, String thumbnailUrl) {
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }
}
