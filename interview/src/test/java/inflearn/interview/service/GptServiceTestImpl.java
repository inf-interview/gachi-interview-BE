package inflearn.interview.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.domain.User;
import inflearn.interview.domain.Video;
import inflearn.interview.domain.VideoQuestion;
import inflearn.interview.domain.dto.FeedbackDTO;
import inflearn.interview.exception.OptionalNotFoundException;
import inflearn.interview.repository.VideoQuestionRepository;
import inflearn.interview.repository.VideoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static inflearn.interview.constant.GptCount.INTERVIEW_MAX_COUNT;

@Transactional
public class GptServiceTestImpl implements GptService{


    public GptServiceTestImpl(GptCallCountService callCountService, VideoRepository videoRepository, VideoQuestionRepository videoQuestionRepository) {
        this.callCountService = callCountService;
        this.videoRepository = videoRepository;
        this.videoQuestionRepository = videoQuestionRepository;
    }

    GptCallCountService callCountService;
    VideoRepository videoRepository;
    VideoQuestionRepository videoQuestionRepository;

    @Override
    public void GPTFeedback(Long videoId, User user, FeedbackDTO dto) throws JsonProcessingException {

        if (dto.getContent().isEmpty()) {
            return;
        }

        if (callCountService.getInterviewCount(user.getUserId()) < INTERVIEW_MAX_COUNT) {

            callCountService.plusInterviewCallCount(user.getUserId());

            Video video = videoRepository.findById(videoId).orElseThrow(OptionalNotFoundException::new);
            List<VideoQuestion> videoQuestions = videoQuestionRepository.findAllByVideo(video);
            StringBuilder sb = new StringBuilder();

            for (VideoQuestion videoQuestion : videoQuestions) {
                sb.append("question").append(": ").append(videoQuestion.getQuestion());
            }

            String question = sb.toString();

        }
    }

    @Override
    public String[] GPTWorkBook(String job) throws JsonProcessingException {
        return new String[0];
    }
}
