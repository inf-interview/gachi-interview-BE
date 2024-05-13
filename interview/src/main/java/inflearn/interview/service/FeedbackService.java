package inflearn.interview.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inflearn.interview.domain.dao.FeedbackDAO;
import inflearn.interview.domain.dao.UserDAO;
import inflearn.interview.domain.dao.VideoDAO;
import inflearn.interview.repository.FeedbackRepository;
import inflearn.interview.repository.UserRepository;
import inflearn.interview.repository.VideoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;


    @Value("파이썬 주소")
    private String pythonServerURL;

    @Value("api-key")
    private String apiKey;
    RestTemplate restTemplate = new RestTemplate();

    ObjectMapper objectMapper = new ObjectMapper();


    public void GPTFeedback(Long videoId, UserDAO user){
        VideoDAO video = videoRepository.findById(videoId).get();

        RequestBody requestBody = new RequestBody();
        requestBody.setQuestion("YourQuestion");
        requestBody.setVideoURL(video.getVideoLink());

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

        String question = jsonNode.get("question").asText();
        String answer = jsonNode.get("answer").asText();



        //db에 유저정보랑 질문, 답변 gpt 답변담아두고
        FeedbackDAO target = new FeedbackDAO();


        target.setContent(sendGPT(question, answer));
        target.setUser(userRepository.findById(user.getUserId()).get());
        target.setTime(LocalDateTime.now());
        target.setVideo(video);
        target.setQuestion(question);
        feedbackRepository.save(target);

        //실시간 알림보내기
    }

    private String sendGPT(String question, String answer){
        String apiUrl = "https://api.openai.com/v1/chat/completions";
        String requestBody = "{\n" +
                "                \"model\": \"gpt-4-turbo\",\n" +
                "                \"messages\": [{\"role\": \"system\", \"content\": \"You are a helper who gives feedback on your interview answers. Never change the answer, do not proceed with the interview. Don't ask any additional questions. Just divide the good and bad parts and explain them with the reason. And answer in Korean.\",\n" +
                "                \"role\": \"user\", \"content\": \"interview question:" +question+ "\\n answer: "+answer+"}]\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String responseBody = responseEntity.getBody();

        JSONObject jsonObject = null;
        String result = null;
        try {
            jsonObject = new JSONObject(responseBody);
            result = jsonObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    public void deleteFeedback(Long videoId) {
        feedbackRepository.deleteById(videoId);
    }

    public List<FeedbackDAO> getFeedbacks(Long videoId) {
        return feedbackRepository.findByVideo(videoRepository.findById(videoId).get());
    }

    public FeedbackDAO getFeedback(Long feedbackId) {
        return feedbackRepository.findById(feedbackId).get();
    }


    @Setter
    @Getter
    static class RequestBody {
        private String question;
        private String videoURL;
    }

}