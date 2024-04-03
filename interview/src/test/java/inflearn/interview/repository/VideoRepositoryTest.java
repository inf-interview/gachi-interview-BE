package inflearn.interview.repository;

import inflearn.interview.AppConfig;
import inflearn.interview.domain.dao.UserDAO;
import inflearn.interview.domain.dao.VideoDAO;
import inflearn.interview.repository.UserRepository;
import inflearn.interview.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class VideoRepositoryTest {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    VideoRepository videoRepository;
    UserRepository userRepository;

    VideoDAO expected;
    VideoDAO expected2;

    UserDAO person1;
    UserDAO person2;

    /**
     * 초기값 설정
     */
    @BeforeEach
    void before(){
        videoRepository = (VideoRepository) ac.getBean("videoRepository");
        userRepository = (UserRepository) ac.getBean("userRepository");

        person1 = new UserDAO();
        person1.setName("권우현");
        person1.setUserId(1L);
        person1.setEmail("kwh1208@naver.com");
        person1.setSocial("네이버");
        person1.setTime(LocalDateTime.of(2024,4,2,12,0,0));


        person2 = new UserDAO();
        person2.setName("고경희");
        person2.setUserId(2L);
        person2.setEmail("kwh871005@gmail.com");
        person2.setSocial("구글");
        person2.setTime(LocalDateTime.of(2024,4,2,12,0,0));


        /*
         * 첫번째 기댓값
         */
        expected = new VideoDAO();
        expected.setVideoId(1L);
        expected.setTime(LocalDateTime.of(2024, 4,2,12,0,0));
        expected.setExposure(true);
        expected.setVideoTitle("비디오 제목");
        expected.setVideoLink("비디오 링크");
        expected.setRawTags("삼성.카카오.네이버");
        expected.setUser(person1);

        /*
         * 두번째 기댓값
         */
        expected2 = new VideoDAO();
        expected2.setVideoId(2L);
        expected2.setTime(LocalDateTime.of(2024,4,2,12,0,0));
        expected2.setExposure(true);
        expected2.setVideoTitle("비디오 제목");
        expected2.setVideoLink("비디오 링크");
        expected2.setRawTags("삼성.카카오.네이버");
        expected2.setUser(person2);

        userRepository.save(person1);
        userRepository.save(person2);
    }
    /**
     * 저장
     */
    @Test
    void videoSaveTest(){
        videoRepository.save(expected);
        Optional<VideoDAO> video = videoRepository.findById(1L);

        VideoDAO result = video.get();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    /**
     * 수정
     */
    @Test
    void videoEditTest(){
        VideoDAO saved = videoRepository.save(expected);
        saved.setRawTags("삼성.카카오.네이버.LG");

        Optional<VideoDAO> video = videoRepository.findById(1L);

        VideoDAO result = video.get();

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);

        //다른 테스트 위해 원복
        saved.setRawTags("삼성.카카오.네이버");
    }

    /**
     * 삭제
     */
    @Test
    void videoDeleteTest(){
        videoRepository.save(expected);
        videoRepository.delete(expected);

        List<VideoDAO> result = videoRepository.findAll();

        assertThat(result.size()).isEqualTo(0);
    }

    /**
     * 모두 조회
     */
    @Test
    void findAllVideoTest(){
        videoRepository.save(expected);
        videoRepository.save(expected2);

        List<VideoDAO> results = videoRepository.findAll();
        for (VideoDAO result : results) {
            if (result.getVideoId()==1L){
                assertThat(result).usingRecursiveComparison().isEqualTo(expected);
            }
            else assertThat(result).usingRecursiveComparison().isEqualTo(expected2);
        }
    }
}