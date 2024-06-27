package inflearn.interview.feedback.service;

import inflearn.interview.feedback.domain.Feedback;
import inflearn.interview.video.infrastructure.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByVideo(VideoEntity videoEntity);
}
