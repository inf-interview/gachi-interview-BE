package inflearn.interview.repository;

import inflearn.interview.domain.VideoQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoQuestionRepository extends JpaRepository<VideoQuestion, Long> {
}
