package inflearn.interview.videolike.service;

import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.video.infrastructure.VideoEntity;
import inflearn.interview.videolike.domain.VideoLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoLikeRepository extends JpaRepository<VideoLike, Long> {
    void deleteByUserAndVideo(UserEntity userEntity, VideoEntity videoEntity);

    Optional<VideoLike> findByUserAndVideo(UserEntity userEntity, VideoEntity videoEntity);

    Long countAllByVideo(VideoEntity videoEntity);

    @Query("select count(vl) from VideoLike vl where vl.video.videoId=:videoId")
    Long countByVideoId(@Param("videoId") Long videoId);
}
