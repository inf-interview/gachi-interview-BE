package inflearn.interview.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.FeedbackDTO;

public interface GptService {
    void GPTFeedback(Long videoId, User user, FeedbackDTO dto) throws JsonProcessingException;
    String[] GPTWorkBook(String job) throws JsonProcessingException;

}
