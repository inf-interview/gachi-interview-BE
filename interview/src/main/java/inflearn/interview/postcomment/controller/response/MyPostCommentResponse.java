package inflearn.interview.postcomment.controller.response;

import inflearn.interview.postcomment.infrastructure.PostCommentEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MyPostCommentResponse {
    private Long commentId;
    private Long postId;
    private Long userId;
    private String username;
    private String content;

    @Builder
    public MyPostCommentResponse(Long commentId, Long postId, Long userId, String username, String content) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.username = username;
        this.content = content;
    }

    public static List<MyPostCommentResponse> from(List<PostCommentEntity> postComments) {
        return postComments.stream().map(postCommentEntity -> MyPostCommentResponse.builder()
                .commentId(postCommentEntity.getId())
                .postId(postCommentEntity.getPostEntity().getId())
                .userId(postCommentEntity.getUserEntity().getId())
                .username(postCommentEntity.getUserEntity().getName())
                .content(postCommentEntity.getContent()).build()).collect(Collectors.toList());
    }
}
