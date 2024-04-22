package inflearn.interview.domain.dto;

import inflearn.interview.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyPostDTO {

    private Long postId;

    private String title;

    private int numOfLike;

    private String content;

    private String[] tags;

    private LocalDateTime time;

    private int commentCount;

    private String[] entityToDtoTagConverter(String tag) {
        return tag.split("[.]");
    }

    public MyPostDTO(Post post, Long commentCount) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.numOfLike = post.getNumOfLike();
        this.content = post.getContent();
        this.tags = entityToDtoTagConverter(post.getTag());
        this.time = post.getCreatedAt();
        this.commentCount = commentCount.intValue();
    }

    public MyPostDTO() {
    }
}
