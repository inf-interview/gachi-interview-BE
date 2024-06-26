package inflearn.interview.post.domain;

import lombok.Getter;

@Getter
public class PostUpdate {
    private Long userId;
    private String postTitle;
    private String category;
    private String[] tag;
    private String content;
}
