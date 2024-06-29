package inflearn.interview.videoquestion.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoQuestionJapRepository extends JpaRepository<VideoQuestionEntity, Long> {

    List<VideoQuestionEntity> findAllByVideoEntityId(Long videoId);
}
