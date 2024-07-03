package inflearn.interview.videolike.infrastructure;

import inflearn.interview.video.domain.Video;
import inflearn.interview.video.infrastructure.VideoEntity;
import inflearn.interview.videolike.domain.VideoLike;
import inflearn.interview.videolike.service.VideoLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VideoLikeRepositoryImpl implements VideoLikeRepository {

    private final VideoLikeJpaRepository videoLikeJpaRepository;

    @Override
    public Optional<VideoLike> findVideoLike(Long userId, Long videoId) {
        return videoLikeJpaRepository.findVideoLikeByUserIdAndPostId(userId, videoId).map(VideoLikeEntity::toModel);
    }

    @Override
    public VideoLike save(VideoLike videoLike) {
        return videoLikeJpaRepository.save(VideoLikeEntity.fromModel(videoLike)).toModel();
    }

    @Override
    public void delete(VideoLike videoLike) {
        videoLikeJpaRepository.delete(VideoLikeEntity.fromModel(videoLike));
    }

    @Override
    public void deleteByVideo(Video video) {
        videoLikeJpaRepository.deleteAllByVideoEntityId(video.getId());
    }
}
