package inflearn.interview.domain.dto;

import inflearn.interview.domain.Post;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDTO {

    private Long userId;

    private String userName;

    private Long postId;

    private String postTitle;

    private String category;

    private String[] tag;

    private String content;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int numOfLike;

    private int commentCount;

    public PostDTO(Post post) {
        this.userId = post.getUser().getUserId();
        this.userName = post.getUser().getName();
        this.postId = post.getPostId();
        this.postTitle = post.getTitle();
        this.category = post.getCategory();
        if (post.getTag() != null) {
            this.tag = entityToDtoTagConverter(post.getTag());
        }
        this.numOfLike = post.getNumOfLike();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

    public PostDTO() {
    }

    private String[] entityToDtoTagConverter(String tag) {
        return tag.split("[.]");
    }
}
