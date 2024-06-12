package inflearn.interview.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inflearn.interview.domain.*;
import inflearn.interview.domain.dto.FeedbackDTO;
import inflearn.interview.domain.dto.VideoCommentDTO;
import inflearn.interview.exception.OptionalNotFoundException;
import inflearn.interview.repository.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final VideoQuestionRepository videoQuestionRepository;
    private final QuestionRepository questionRepository;
    private final VideoCommentService videoCommentService;


    @Value("${python.server.url}")
    private String pythonServerURL;

    @Value("${api.key}")
    private String apiKey;

    RestTemplate restTemplate = new RestTemplate();

    ObjectMapper objectMapper = new ObjectMapper();

    public void GPTFeedback(Long videoId, User user, FeedbackDTO dto) throws JsonProcessingException {

        Video video = videoRepository.findById(videoId).orElseThrow(OptionalNotFoundException::new);
        List<VideoQuestion> videoQuestions = videoQuestionRepository.findAllByVideo(video);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < videoQuestions.size(); i++) {
            Question question = questionRepository.findById(videoQuestions.get(i).getQuestion().getId()).orElseThrow(OptionalNotFoundException::new);
            sb.append("question").append(i + 1).append(": ").append(question.getContent());
        }

        String question = sb.toString();

        if (dto.getContent().isEmpty()) {

            RequestBody requestBody = new RequestBody();
            requestBody.setVideo_url(video.getVideoLink());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<RequestBody> requestEntity = new HttpEntity<>(requestBody, headers);

            String response = restTemplate.postForObject(pythonServerURL, requestEntity, String.class);

            JsonNode jsonNode;
            try {
                jsonNode = objectMapper.readTree(response);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            String answer = jsonNode.get("result").asText();


            //db에 유저정보랑 질문, 답변 gpt 답변담아두고
            Feedback target = new Feedback();
            String result = sendGPT(question, answer);

            target.setContent(result);
            target.setUser(user);
            target.setTime(LocalDateTime.now());
            target.setVideo(video);
            target.setQuestion(question);
            feedbackRepository.save(target);

            sendCompleteComment(result, videoId);
        } else {
            String result = sendGPT(question, dto.getContent());
            sendCompleteComment(result, videoId);
        }


    }

    private String sendGPT(String question, String answer) throws JsonProcessingException {
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("model", "gpt-3.5-turbo");

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "system");
        userMessage.put("content", "You are a helper who gives feedback on your interview answers. Never change the answer, do not proceed with the interview. Don't ask any additional questions. Just divide 좋은점 and 개선할점 and explain them with the reason. don't explain typos and interpret yourself. And answer in Korean.");

        Map<String, String> assistantMessage = new HashMap<>();
        assistantMessage.put("role", "system");
        assistantMessage.put("content", "Question: " + question + ", Answer: " + answer);

        messages.add(userMessage);
        messages.add(assistantMessage);

        bodyMap.put("messages", messages);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        String body = objectMapper.writeValueAsString(bodyMap);

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);


        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String responseBody = responseEntity.getBody();

        JSONObject jsonObject;
        String result;
        try {
            jsonObject = new JSONObject(responseBody);
            result = jsonObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        log.info("result{}", result);
        return result;
    }

    private void sendCompleteComment(String result, Long videoId) {
        VideoCommentDTO videoCommentDTO = new VideoCommentDTO();
        User admin = userRepository.findAdmin("ADMIN");
        videoCommentDTO.setUserId(admin.getUserId());
        videoCommentDTO.setContent(result);
        videoCommentService.addComment(videoId, videoCommentDTO);
    }


    public void deleteFeedback(Long videoId) {
        feedbackRepository.deleteById(videoId);
    }

    public List<Feedback> getFeedbacks(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        List<Video> findVideos = videoRepository.findByUser_UserId(findUser.getUserId());
        List<Feedback> feedbacks = new ArrayList<>();
        for (Video video : findVideos) {
            feedbacks.addAll(feedbackRepository.findByVideo(videoRepository.findById(video.getVideoId()).orElseThrow(OptionalNotFoundException::new)));
        }
        return feedbacks;
    }

    public Feedback getFeedback(Long feedbackId) {
        return feedbackRepository.findById(feedbackId).orElseThrow(OptionalNotFoundException::new);
    }


    @Setter
    @Getter
    static class RequestBody {
        private String video_url;
    }

}