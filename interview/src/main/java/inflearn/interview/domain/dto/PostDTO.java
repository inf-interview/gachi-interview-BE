package inflearn.interview.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import inflearn.interview.domain.Post;
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


    private LocalDateTime time;
    private LocalDateTime updateTime;

    private int numOfLike;
    private int numOfComment;

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
        this.time = post.getCreatedAt();
        this.updateTime = post.getUpdatedAt();
    }

    @QueryProjection
    public PostDTO(Long userId, String userName, Long postId, String postTitle, String content, String category, LocalDateTime time, LocalDateTime updateTime, int numOfLike, Long numOfComment, String tag) {
        this.userId = userId;
        this.userName = userName;
        this.postId = postId;
        this.postTitle = postTitle;
        this.content = content;
        this.category = category;
        this.time = time;
        this.updateTime = updateTime;
        this.numOfLike = numOfLike;
        this.numOfComment = Math.toIntExact(numOfComment);
        if (tag != null) {
            this.tag = entityToDtoTagConverter(tag);
        }
    }

    public PostDTO() {
    }

    private String[] entityToDtoTagConverter(String tag) {
        return tag.split("[.]");
    }
}
