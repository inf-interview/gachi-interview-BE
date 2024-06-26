package inflearn.interview.post.controller.response;

import java.time.LocalDateTime;

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
}
