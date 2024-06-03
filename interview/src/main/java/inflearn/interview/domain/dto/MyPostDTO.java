package inflearn.interview.domain.dto;

import inflearn.interview.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyPostDTO {

    private Long postId;

    private String postTitle;

    private int numOfLike;

    private String content;

    private String[] tag;

    private LocalDateTime time;

    private int numOfComment;

    private String category;

    private String[] entityToDtoTagConverter(String tag) {
        return tag.split("[.]");
    }

    public MyPostDTO(Post post, Long commentCount) {
        this.postId = post.getPostId();
        this.postTitle = post.getTitle();
        this.numOfLike = post.getNumOfLike();
        this.content = post.getContent();
        this.tag = entityToDtoTagConverter(post.getTag());
        this.time = post.getCreatedAt();
        this.numOfComment = commentCount.intValue();
        this.category = post.getCategory();
    }

    public MyPostDTO() {
    }
}
