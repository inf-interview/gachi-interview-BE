package inflearn.interview.videocomment.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoCommentCreate {
    private Long userId;
    private String content;

    @Builder
    public VideoCommentCreate(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public static VideoCommentCreate create(Long userId, String content) {
        return VideoCommentCreate.builder()
                .userId(userId)
                .content(content)
                .build();
    }
}
