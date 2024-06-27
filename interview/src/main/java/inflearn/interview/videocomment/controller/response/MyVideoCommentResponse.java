package inflearn.interview.videocomment.controller.response;

import inflearn.interview.videocomment.infrastructure.VideoCommentEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MyVideoCommentResponse {
    private Long commentId;
    private Long userId;
    private Long videoId;
    private String userName;
    private String content;

    @Builder
    public MyVideoCommentResponse(Long commentId, Long userId, Long videoId, String userName, String content) {
        this.commentId = commentId;
        this.userId = userId;
        this.videoId = videoId;
        this.userName = userName;
        this.content = content;
    }

    public static List<MyVideoCommentResponse> from(List<VideoCommentEntity> videoComments) {
        return videoComments.stream().map(videoCommentEntity -> MyVideoCommentResponse.builder()
                .commentId(videoCommentEntity.getId())
                .userId(videoCommentEntity.getUserEntity().getId())
                .videoId(videoCommentEntity.getVideoEntity().getId())
                .userName(videoCommentEntity.getUserEntity().getName())
                .content(videoCommentEntity.getContent()).build()).collect(Collectors.toList());
    }
}
