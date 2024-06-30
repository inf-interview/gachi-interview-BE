package inflearn.interview.videocomment.infrastructure;

import inflearn.interview.videocomment.domain.VideoComment;
import inflearn.interview.videocomment.service.VideoCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VideoCommentRepositoryImpl implements VideoCommentRepository {

    private final VideoCommentJpaRepository videoCommentJpaRepository;

    @Override
    public VideoComment save(VideoComment videoComment) {
        return videoCommentJpaRepository.save(VideoCommentEntity.fromModel(videoComment)).toModel();
    }

    @Override
    public Optional<VideoComment> findById(Long id) {
        return videoCommentJpaRepository.findById(id).map(VideoCommentEntity::toModel);
    }

    @Override
    public void delete(VideoComment videoComment) {
        videoCommentJpaRepository.delete(VideoCommentEntity.fromModel(videoComment));
    }

    @Override
    public List<VideoCommentEntity> findCommentList(Long videoId) {
        return videoCommentJpaRepository.findCommentList(videoId);
    }

    @Override
    public List<VideoCommentEntity> findMyComment(Long userId) {
        return videoCommentJpaRepository.findMyComment(userId);
    }
}
