package inflearn.interview.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.feedback.domain.FeedbackDTO;

public interface GptService {
    void GPTFeedback(Long videoId, User user, FeedbackDTO dto) throws JsonProcessingException;
    String[] GPTWorkBook(String job) throws JsonProcessingException;

}
