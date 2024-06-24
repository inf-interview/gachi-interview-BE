package inflearn.interview.repository;

import inflearn.interview.domain.Video;
import inflearn.interview.domain.VideoQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoQuestionRepository extends JpaRepository<VideoQuestion, Long> {

    List<VideoQuestion> findAllByVideo(Video video);
}
