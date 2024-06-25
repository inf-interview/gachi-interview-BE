package inflearn.interview.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.TestRepository;
import inflearn.interview.domain.User;
import inflearn.interview.domain.Video;
import inflearn.interview.domain.dto.FeedbackDTO;
import inflearn.interview.repository.UserRepository;
import inflearn.interview.repository.VideoQuestionRepository;
import inflearn.interview.repository.VideoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class GptServiceTest {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private GptCallCountService gptCallCountService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoQuestionRepository videoQuestionRepository;

    @Test
    @Transactional
    void 사용자는_AI_피드백_서비스를_이용할_수_있고_이용시_gptCallCount가_1올라간다() throws JsonProcessingException {
        // given
        GptServiceTestImpl gptService = new GptServiceTestImpl(gptCallCountService, videoRepository, videoQuestionRepository);

        User user = new User();
        user.setEmail("thstkddnr20@naver.com");
        user.setName("손상욱");
        user.setCreatedAt(LocalDateTime.now());
        user.setRole("USER");
        user.setSocial("KAKAO");
        testRepository.save(user);

        Video video = new Video();
        video.setUser(user);
        video.setThumbnailLink("thumb");
        video.setVideoTitle("title");
        video.setExposure(true);
        video.setTime(LocalDateTime.now());
        video.setVideoLink("link");
        testRepository.save(video);

        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setContent("안녕하세요");

        // when
        gptService.GPTFeedback(video.getVideoId(), user, feedbackDTO);


        // then
        assertThat(video.getUser().getInterviewGptCallCount()).isEqualTo(1);

    }
}
