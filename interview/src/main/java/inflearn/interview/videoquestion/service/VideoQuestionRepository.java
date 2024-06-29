package inflearn.interview.videoquestion.service;

import inflearn.interview.video.domain.Video;
import inflearn.interview.video.infrastructure.VideoEntity;
import inflearn.interview.videoquestion.domain.QuestionFromVideoQuestion;
import inflearn.interview.videoquestion.domain.VideoQuestion;
import inflearn.interview.videoquestion.infrastructure.VideoQuestionEntity;

import java.util.List;

public interface VideoQuestionRepository {

    List<QuestionFromVideoQuestion> findQuestionsByVideoId(Long videoId);

    VideoQuestion save(VideoQuestion videoQuestion);
}
