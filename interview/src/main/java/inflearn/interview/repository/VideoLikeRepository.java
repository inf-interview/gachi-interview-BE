package inflearn.interview.repository;

import inflearn.interview.domain.User;
import inflearn.interview.domain.Video;
import inflearn.interview.domain.VideoLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoLikeRepository extends JpaRepository<VideoLike, Long> {
    void deleteByUserAndVideo(User user, Video video);

    Optional<VideoLike> findByUserAndVideo(User user, Video video);

    Long countAllByVideo(Video video);

    @Query("select count(vl) from VideoLike vl where vl.video.videoId=:videoId")
    Long countByVideoId(@Param("videoId") Long videoId);
}
