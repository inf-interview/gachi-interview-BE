package inflearn.interview.repository;

import inflearn.interview.domain.dao.UserDAO;
import inflearn.interview.domain.dao.VideoDAO;
import inflearn.interview.domain.dao.VideoLikeDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoLikeRepository extends JpaRepository<VideoLikeDAO, Long> {
    void deleteByUserAndVideo(UserDAO user, VideoDAO video);

    Long countAllByVideo(VideoDAO video);
}
