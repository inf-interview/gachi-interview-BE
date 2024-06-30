package inflearn.interview.post.controller.response;

import inflearn.interview.post.domain.Post;
import inflearn.interview.postcomment.domain.PostComment;
import inflearn.interview.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDetailResponse {

    private Long userId;
    private String userName;
    private Long postId;
    private String postTitle;
    private String category;
    private LocalDateTime time;
    private LocalDateTime updateTime;
    private int numOfLike;
    private int numOfComment;
    private String[] tag;
    private String image;
    private String content;
    private boolean isLiked;

    @Builder
    public PostDetailResponse(Long userId, String userName, Long postId, String postTitle, String category, LocalDateTime time, LocalDateTime updateTime, int numOfLike, int numOfComment, String[] tag, String image, String content, boolean isLiked) {
        this.userId = userId;
        this.userName = userName;
        this.postId = postId;
        this.postTitle = postTitle;
        this.category = category;
        this.time = time;
        this.updateTime = updateTime;
        this.numOfLike = numOfLike;
        this.numOfComment = numOfComment;
        this.tag = tag;
        this.image = image;
        this.content = content;
        this.isLiked = isLiked;
    }

    public static PostDetailResponse from(User writer, Post post, int numOfComment, boolean isLiked) {
        return PostDetailResponse.builder()
                .userId(writer.getId())
                .userName(writer.getName())
                .postId(post.getId())
                .postTitle(post.getTitle())
                .category(post.getCategory())
                .time(post.getCreatedAt())
                .updateTime(post.getUpdatedAt())
                .numOfLike(post.getNumOfLike())
                .numOfComment(numOfComment)
                .tag(tagConverter(post.getTag()))
                .image(writer.getImage())
                .content(post.getContent())
                .isLiked(isLiked)
                .build();
    }

    private static String[] tagConverter(String tag) {
        return tag.split("[.]");
    }
}
