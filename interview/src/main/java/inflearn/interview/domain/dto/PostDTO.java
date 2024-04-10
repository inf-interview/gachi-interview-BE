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

    @NotNull
    private String postTitle;

    @NotNull
    private String category;

    private String[] tag;

    @NotNull
    private String content;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int numOfLike;

    public PostDTO(Post post) {
        this.postTitle = post.getTitle();
        this.category = post.getCategory();
        this.tag = entityToDtoTagConverter(post.getTag());
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();

    }

    private String[] entityToDtoTagConverter(String tag) {
        return tag.split(".");
    }
}
