package inflearn.interview.videocomment.domain;

import lombok.Getter;

@Getter
public class VideoCommentUpdate {
    private Long userId;
    private Long commentId;
    private String content;
}
