package inflearn.interview.post.controller.response;

import inflearn.interview.post.domain.Post;
import lombok.Builder;

public class PostUpdateResponse {

    private Long postId;


    @Builder
    public PostUpdateResponse(Long postId) {
        this.postId = postId;
    }

    public static PostUpdateResponse from(Post post) {
        return PostUpdateResponse.builder()
                .postId(post.getId())
                .build();
    }
}
