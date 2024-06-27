package inflearn.interview.videocomment.controller.response;

import inflearn.interview.videocomment.infrastructure.VideoCommentEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class VideoCommentListResponse {
    private Long commentId;
    private Long userId;
    private String username;
    private String image;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public VideoCommentListResponse(Long commentId, Long userId, String username, String image, String content, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.userId = userId;
        this.username = username;
        this.image = image;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static List<VideoCommentListResponse> from(List<VideoCommentEntity> commentList) {
        return commentList.stream().map(videoCommentEntity -> VideoCommentListResponse.builder()
                .commentId(videoCommentEntity.getId())
                .userId(videoCommentEntity.getUserEntity().getId())
                .username(videoCommentEntity.getUserEntity().getName())
                .image(videoCommentEntity.getUserEntity().getImage())
                .content(videoCommentEntity.getContent())
                .createdAt(videoCommentEntity.getTime()).build()).collect(Collectors.toList());
    }
}
