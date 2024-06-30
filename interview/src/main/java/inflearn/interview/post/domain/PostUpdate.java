package inflearn.interview.post.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdate {
    private Long userId;
    private String postTitle;
    private String category;
    private String[] tag;
    private String content;

    @Builder
    public PostUpdate(Long userId, String postTitle, String category, String[] tag, String content) {
        this.userId = userId;
        this.postTitle = postTitle;
        this.category = category;
        this.tag = tag;
        this.content = content;
    }
}
