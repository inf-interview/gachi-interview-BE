package inflearn.interview.videolike.infrastructure;

import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.video.infrastructure.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoLikeJpaRepository extends JpaRepository<VideoLikeEntity, Long> {

    Optional<VideoLikeEntity> findByUserEntityAndVideoEntity(UserEntity userEntity, VideoEntity videoEntity);
}
