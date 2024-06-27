package inflearn.interview.common.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import inflearn.interview.common.service.GptCallCountService;
import inflearn.interview.common.service.GptService;
import inflearn.interview.feedback.domain.FeedbackDTO;
import inflearn.interview.feedback.service.FeedbackRepository;
import inflearn.interview.question.service.QuestionRepository;
import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.video.service.VideoRepository;
import inflearn.interview.videocomment.service.VideoCommentService;
import inflearn.interview.videocomment.domain.VideoCommentDTO;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.feedback.domain.Feedback;
import inflearn.interview.video.infrastructure.VideoEntity;
import inflearn.interview.videoquestion.domain.VideoQuestion;
import inflearn.interview.videoquestion.service.VideoQuestionRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static inflearn.interview.common.constant.GptCount.INTERVIEW_MAX_COUNT;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GptServiceImpl implements GptService {
    private final FeedbackRepository feedbackRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final VideoQuestionRepository videoQuestionRepository;
    private final QuestionRepository questionRepository;
    private final VideoCommentService videoCommentService;
    private final GptCallCountService callCountService;


    @Value("${python.server.url}")
    private String pythonServerURL;

    @Value("${api.key}")
    private String apiKey;

    RestTemplate restTemplate = new RestTemplate();

    ObjectMapper objectMapper = new ObjectMapper();

    public void GPTFeedback(Long videoId, UserEntity userEntity, FeedbackDTO dto) throws JsonProcessingException {

        if (dto.getContent().isEmpty()) {
            sendFailComment(videoId);
            return;
        }

        if (callCountService.getInterviewCount(userEntity.getUserId()) < INTERVIEW_MAX_COUNT) {

            callCountService.plusInterviewCallCount(userEntity.getUserId());

            VideoEntity videoEntity = videoRepository.findById(videoId).orElseThrow(OptionalNotFoundException::new);
            List<VideoQuestion> videoQuestions = videoQuestionRepository.findAllByVideo(videoEntity);
            StringBuilder sb = new StringBuilder();

            for (VideoQuestion videoQuestion : videoQuestions) {
                sb.append("question").append(": ").append(videoQuestion.getQuestion());
            }

            String question = sb.toString();

            String result = sendGptFeedback(question, dto.getContent());
            sendCompleteComment(result, videoId);

        }

    }

    public String[] GPTWorkBook(String job) throws JsonProcessingException {
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        Map<String, Object> bodyMap = writeWorkbookPrompt(job);

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

            String[] answerSplit = result.split("Answer: ");
            String[] questionSplit = answerSplit[0].split("Question: ");

            return new String[]{questionSplit[1].trim(), answerSplit[1].trim()};
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    private String sendGptFeedback(String question, String answer) throws JsonProcessingException {
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        Map<String, Object> bodyMap = writeFeedbackPrompt(question, answer);

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

        return result;
    }

    private Map<String, Object> writeFeedbackPrompt(String question, String answer) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("model", "gpt-3.5-turbo");

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "system");
        userMessage.put("content", "You are a friendly and supportive interview coach who gives feedback on interview answers. Never change the answer, do not proceed with the interview. "
                + "Do not ask any additional questions. Just divide 좋은점 and 개선할점 and explain them with reasons in a continuous, flowing manner without using bullet points or numbers. "
                + "Do not explain typos and interpret yourself. Answer in Korean with a positive and encouraging tone. "
                + "Avoid commenting on the transition between topics. The feedback must be at least 750 characters and no more than 1000 characters.");

        Map<String, String> assistantMessage = new HashMap<>();
        assistantMessage.put("role", "system");
        assistantMessage.put("content", "Question: " + question + ", Answer: " + answer);

        messages.add(userMessage);
        messages.add(assistantMessage);

        bodyMap.put("messages", messages);
        return bodyMap;
    }

    private Map<String, Object> writeWorkbookPrompt(String job) {

        String[] splitJob = job.split("/");

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("model", "gpt-3.5-turbo");

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "system");
        userMessage.put("content", "You are a highly trained interview coach. " +
                "Please write just one interview question and example answer in Korean for the given job_group and job. " +
                "Question should not be dependent on anything like a project, Answer should be at least 200 characters " +
                "Make sure to clearly distinguish between the 'Question' and 'Answer' so they can be split into strings easily.");

        Map<String, String> assistantMessage = new HashMap<>();
        assistantMessage.put("role", "system");
        assistantMessage.put("content", "job_group: " + splitJob[0] + ", job: " + splitJob[1]);

        messages.add(userMessage);
        messages.add(assistantMessage);

        bodyMap.put("messages", messages);
        return bodyMap;
    }

    private void sendCompleteComment(String result, Long videoId) {
        VideoCommentDTO videoCommentDTO = new VideoCommentDTO();
        UserEntity admin = userRepository.findAdmin("ADMIN");
        videoCommentDTO.setUserId(admin.getUserId());
        videoCommentDTO.setContent(result);
        videoCommentService.create(videoId, videoCommentDTO);
    }

    private void sendFailComment(Long videoId) {
        VideoCommentDTO videoCommentDTO = new VideoCommentDTO();
        UserEntity admin = userRepository.findAdmin("ADMIN");
        videoCommentDTO.setUserId(admin.getUserId());
        String failMessage1 = "올바른 영상이 제공되지 않았습니다. 피드백을 받기 위해 더 긴 영상을 업로드해주세요. \n\n같이면접 서비스에서 권장되는 영상길이는 1~5분입니다.\n영상녹화에 적합한 환경인지 확인해주세요.";
        String failMessage2 = "음성 입력이 제대로 되지 않았습니다. 음성이 명확하게 들리는 영상을 업로드해주세요. \n\n같이면접 서비스에서 권장되는 영상길이는 1~5분입니다.\n영상녹화에 적합한 환경인지 확인해주세요.";
        String failMessage3 = "피드백을 받기 위해 영상의 음질과 길이를 확인해주세요. 명확한 음성과 충분한 길이의 영상을 업로드해 주세요. \n\n같이면접 서비스에서 권장되는 영상길이는 1~5분입니다.\n영상녹화에 적합한 환경인지 확인해주세요.";

        Random random = new Random();
        int i = random.nextInt(3);
        switch (i) {
            case 0 -> videoCommentDTO.setContent(failMessage1);
            case 1 -> videoCommentDTO.setContent(failMessage2);
            case 2 -> videoCommentDTO.setContent(failMessage3);
        }
        videoCommentService.create(videoId, videoCommentDTO);
    }


    public void deleteFeedback(Long videoId) {
        feedbackRepository.deleteById(videoId);
    }

    public List<Feedback> getFeedbacks(Long userId) {
        UserEntity findUserEntity = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        List<VideoEntity> findVideoEntities = videoRepository.findByUser_UserId(findUserEntity.getUserId());
        List<Feedback> feedbacks = new ArrayList<>();
        for (VideoEntity videoEntity : findVideoEntities) {
            feedbacks.addAll(feedbackRepository.findByVideo(videoRepository.findById(videoEntity.getId()).orElseThrow(OptionalNotFoundException::new)));
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

    /** 기존 Python 서버로 음성추출 보내던 부분
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
     **/

}