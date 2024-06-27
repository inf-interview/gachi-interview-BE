package inflearn.interview.postcomment.domain;

import lombok.Getter;

@Getter
public class PostCommentCreate {
    private Long userId;
    private String content;
}
