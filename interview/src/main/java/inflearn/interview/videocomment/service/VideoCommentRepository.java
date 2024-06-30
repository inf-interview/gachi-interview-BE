package inflearn.interview.videocomment.service;

import inflearn.interview.videocomment.domain.VideoComment;
import inflearn.interview.videocomment.infrastructure.VideoCommentEntity;

import java.util.List;
import java.util.Optional;

public interface VideoCommentRepository {
    VideoComment save(VideoComment videoComment);

    Optional<VideoComment> findById(Long id);

    void delete(VideoComment videoComment);

    List<VideoCommentEntity> findCommentList(Long videoId);

    List<VideoCommentEntity> findMyComment(Long userId);
}