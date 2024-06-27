package inflearn.interview.videolike.infrastructure;

import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.video.infrastructure.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VideoLikeJpaRepository extends JpaRepository<VideoLikeEntity, Long> {

    @Query("select count(vl) from VideoLikeEntity vl where vl.video.videoId=:videoId")
    Long countByVideoId(@Param("videoId") Long videoId);

    Optional<VideoLikeEntity> findByUserEntityAndVideoEntity(UserEntity userEntity, VideoEntity videoEntity);
}
