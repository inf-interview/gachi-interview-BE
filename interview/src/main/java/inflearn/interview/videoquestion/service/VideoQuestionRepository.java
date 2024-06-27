package inflearn.interview.videoquestion.service;

import inflearn.interview.video.infrastructure.VideoEntity;
import inflearn.interview.videoquestion.domain.VideoQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoQuestionRepository extends JpaRepository<VideoQuestion, Long> {

    List<VideoQuestion> findAllByVideo(VideoEntity videoEntity);
}
