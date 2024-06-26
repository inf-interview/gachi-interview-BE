package inflearn.interview.post.controller.response;

import inflearn.interview.post.domain.Post;
import lombok.Builder;

import java.time.LocalDateTime;

public class PostCreateResponse {
    private Long postId;
    private Long userId;
    private String postTitle;
    private String content;
    private String[] tag;
    private LocalDateTime time;

    @Builder
    public PostCreateResponse(Long postId, Long userId, String postTitle, String content, String[] tag, LocalDateTime time) {
        this.postId = postId;
        this.userId = userId;
        this.postTitle = postTitle;
        this.content = content;
        this.tag = tag;
        this.time = time;
    }

    public static PostCreateResponse from(Post post) {
        return PostCreateResponse.builder()
                .postId(post.getId())
                .userId(post.getUser().getId())
                .postTitle(post.getTitle())
                .content(post.getContent())
                .tag(tagConverter(post.getTag()))
                .time(post.getCreatedAt())
                .build();
    }

    private static String[] tagConverter(String tag) {
        return tag.split("[.]");
    }
}

