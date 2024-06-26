package inflearn.interview.videolike.service;

import inflearn.interview.user.domain.User;
import inflearn.interview.video.domain.Video;
import inflearn.interview.videolike.domain.VideoLike;
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
