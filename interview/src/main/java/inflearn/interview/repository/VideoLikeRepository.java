package inflearn.interview.repository;

import inflearn.interview.domain.User;
import inflearn.interview.domain.Video;
import inflearn.interview.domain.VideoLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoLikeRepository extends JpaRepository<VideoLike, Long> {
    void deleteByUserAndVideo(User user, Video video);

    Long countAllByVideo(Video video);
}
