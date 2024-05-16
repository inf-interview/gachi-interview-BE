package inflearn.interview.repository;

import inflearn.interview.domain.Feedback;
import inflearn.interview.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByVideo(Video video);
}
