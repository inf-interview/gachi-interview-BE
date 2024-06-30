package inflearn.interview.post.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreate {

    private Long userId;
    private String postTitle;
    private String content;
    private String category;
    private String[] tag;

    @Builder
    public PostCreate(Long userId, String postTitle, String content, String category, String[] tag) {
        this.userId = userId;
        this.postTitle = postTitle;
        this.content = content;
        this.category = category;
        this.tag = tag;
    }
}
