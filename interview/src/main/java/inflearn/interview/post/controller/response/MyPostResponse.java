package inflearn.interview.post.controller.response;

import inflearn.interview.post.infrastructure.PostEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyPostResponse {

    private Long postId;

    private String postTitle;

    private int numOfLike;

    private String content;

    private String[] tag;

    private LocalDateTime time;

    private int numOfComment;

    private String category;

    private String[] tagConverter(String tag) {
        return tag.split("[.]");
    }

    public MyPostResponse(PostEntity post, Long commentCount) {
        this.postId = post.getId();
        this.postTitle = post.getTitle();
        this.numOfLike = post.getNumOfLike();
        this.content = post.getContent();
        this.tag = tagConverter(post.getTag());
        this.time = post.getCreatedAt();
        this.numOfComment = commentCount.intValue();
        this.category = post.getCategory();
    }

    public MyPostResponse() {
    }
}
