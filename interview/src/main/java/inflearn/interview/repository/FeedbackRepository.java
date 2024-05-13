package inflearn.interview.repository;

import inflearn.interview.domain.dao.FeedbackDAO;
import inflearn.interview.domain.dao.VideoDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<FeedbackDAO, Long> {
    List<FeedbackDAO> findByVideo(VideoDAO video);
}
