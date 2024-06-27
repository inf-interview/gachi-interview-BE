package inflearn.interview.videocomment.domain;

import lombok.Getter;

@Getter
public class VideoCommentCreate {
    private Long userId;
    private String content;
}
