package inflearn.interview.postcomment.controller.response;

import inflearn.interview.postcomment.domain.PostComment;
import inflearn.interview.postcomment.infrastructure.PostCommentEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostCommentListResponse {

    private Long commentId;
    private Long userId;
    private String username;
    private String image;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public PostCommentListResponse(Long commentId, Long userId, String username, String image, String content, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.userId = userId;
        this.username = username;
        this.image = image;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static List<PostCommentListResponse> from(List<PostCommentEntity> postComments) {
        return postComments.stream().map(postComment -> PostCommentListResponse.builder()
                .commentId(postComment.getId())
                .userId(postComment.getUserEntity().getId())
                .username(postComment.getUserEntity().getName())
                .image(postComment.getUserEntity().getImage())
                .content(postComment.getContent())
                .createdAt(postComment.getCreatedAt())
                .build()).collect(Collectors.toList());
    }

}
