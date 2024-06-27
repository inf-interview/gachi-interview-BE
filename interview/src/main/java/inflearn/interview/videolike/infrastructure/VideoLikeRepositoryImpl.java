package inflearn.interview.videolike.infrastructure;

import inflearn.interview.user.domain.User;
import inflearn.interview.user.infrastructure.UserEntity;
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
    public void deleteByUserAndVideo(User user, Video video) {

    }

    @Override
    public Optional<VideoLike> findByUserAndVideo(User user, Video video) {
        return videoLikeJpaRepository.findByUserEntityAndVideoEntity(UserEntity.fromModel(user), VideoEntity.fromModel(video)).map(VideoLikeEntity::toModel);
    }

    @Override
    public Long countAllByVideo(Video video) {
        return null;
    }

    @Override
    public VideoLike save(VideoLike videoLike) {
        return videoLikeJpaRepository.save(VideoLikeEntity.fromModel(videoLike)).toModel();
    }

    @Override
    public void delete(VideoLike videoLike) {
        videoLikeJpaRepository.delete(VideoLikeEntity.fromModel(videoLike));
    }
}
