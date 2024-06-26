package inflearn.interview.videoquestion.service;

import inflearn.interview.video.domain.Video;
import inflearn.interview.videoquestion.domain.VideoQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoQuestionRepository extends JpaRepository<VideoQuestion, Long> {

    List<VideoQuestion> findAllByVideo(Video video);
}
