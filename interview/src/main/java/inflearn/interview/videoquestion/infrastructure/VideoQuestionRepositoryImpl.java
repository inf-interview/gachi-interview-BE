package inflearn.interview.videoquestion.infrastructure;

import inflearn.interview.videoquestion.domain.QuestionFromVideoQuestion;
import inflearn.interview.videoquestion.domain.VideoQuestion;
import inflearn.interview.videoquestion.service.VideoQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VideoQuestionRepositoryImpl implements VideoQuestionRepository {

    private final VideoQuestionJapRepository videoQuestionJapRepository;


    @Override
    public List<QuestionFromVideoQuestion> findQuestionsByVideoId(Long videoId) {
        return QuestionFromVideoQuestion.from(videoQuestionJapRepository.findAllByVideoEntityId(videoId));
    }

    @Override
    public VideoQuestion save(VideoQuestion videoQuestion) {
        return videoQuestionJapRepository.save(VideoQuestionEntity.fromModel(videoQuestion)).toModel();
    }
}
