package inflearn.interview.videolike.infrastructure;

import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.video.infrastructure.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VideoLikeJpaRepository extends JpaRepository<VideoLikeEntity, Long> {

    @Query("select vl from VideoLikeEntity vl where vl.userEntity.id=:userId and vl.videoEntity.id=:videoId")
    Optional<VideoLikeEntity> findVideoLikeByUserIdAndPostId(@Param("userId") Long userId,@Param("videoId") Long videoId);
}
