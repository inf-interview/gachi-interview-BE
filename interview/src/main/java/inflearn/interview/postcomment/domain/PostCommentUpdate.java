package inflearn.interview.postcomment.domain;

import lombok.Getter;

@Getter
public class PostCommentUpdate {
    private Long userId;
    private Long commentId;
    private String content;
}
