package inflearn.interview.post.domain;

import lombok.Getter;

@Getter
public class PostCreate {

    private Long userId;
    private String postTitle;
    private String content;
    private String category;
    private String[] tag;

}
