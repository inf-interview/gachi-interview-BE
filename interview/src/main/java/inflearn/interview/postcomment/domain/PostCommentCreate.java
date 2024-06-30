package inflearn.interview.postcomment.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCommentCreate {
    private Long userId;
    private String content;

    @Builder
    public PostCommentCreate(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }
}
