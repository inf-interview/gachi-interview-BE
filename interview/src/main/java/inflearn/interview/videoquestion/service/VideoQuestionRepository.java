package inflearn.interview.videoquestion.service;

import inflearn.interview.videoquestion.domain.QuestionFromVideoQuestion;
import inflearn.interview.videoquestion.domain.VideoQuestion;

import java.util.List;

public interface VideoQuestionRepository {

    List<QuestionFromVideoQuestion> findQuestionsByVideoId(Long videoId);

    VideoQuestion save(VideoQuestion videoQuestion);
}
