package inflearn.interview.repository;

import inflearn.interview.AppConfig;
import inflearn.interview.domain.User;
import inflearn.interview.domain.VideoComment;
import inflearn.interview.domain.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class VideoRepositoryTest {
    @Autowired VideoRepository videoRepository;
    @Autowired UserRepository userRepository;

    @Autowired VideoCommentRepository commentRepository;

    Video expected;
    Video expected2;

    User person1;
    User person2;

    VideoComment comment1;
    VideoComment comment2;

    /**
     * 초기값 설정
     */
    @BeforeEach
    void before(){

        person1 = new User();
        person1.setName("권우현");
        person1.setUserId(1L);
        person1.setEmail("kwh1208@naver.com");
        person1.setSocial("네이버");
        person1.setTime(LocalDateTime.of(2024,4,2,12,0,0));


        person2 = new User();
        person2.setName("고경희");
        person2.setUserId(2L);
        person2.setEmail("kwh871005@gmail.com");
        person2.setSocial("구글");
        person2.setTime(LocalDateTime.of(2024,4,2,12,0,0));

        /*
         * 첫번째 기댓값
         */
        expected = new Video();
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
        expected2 = new Video();
        expected2.setVideoId(2L);
        expected2.setTime(LocalDateTime.of(2024,4,2,12,0,0));
        expected2.setExposure(true);
        expected2.setVideoTitle("비디오 제목");
        expected2.setVideoLink("비디오 링크");
        expected2.setRawTags("삼성.카카오.네이버");
        expected2.setUser(person2);

        comment1 = new VideoComment();
        comment1.setId(1L);
        comment1.setContent("ㅎㅎ");
        comment1.setTime(LocalDateTime.of(2024,4,2,12,0,0));
        comment1.setUser(person1);
        comment1.setVideo(expected);

        comment2 = new VideoComment();
        comment2.setId(2L);
        comment2.setContent("ㅋㅋ");
        comment2.setTime(LocalDateTime.of(2024,4,2,12,0,0));
        comment2.setUser(person2);
        comment2.setVideo(expected2);


        userRepository.save(person1);
        videoRepository.save(expected);
        userRepository.save(person2);
        videoRepository.save(expected2);
        comment2.setUser(person2);
        comment2.setVideo(expected2);
    }
    /**
     * 저장
     */
    @Test
    void videoSaveTest(){
        Video result = videoRepository.findById(1L).get();

        assertThat(result.getVideoTitle()).isEqualTo(expected.getVideoTitle());
    }

    /**
     * 수정
     */
    @Test
    @Transactional
    void videoEditTest(){
        Video saved = videoRepository.findById(1L).get();

        saved.setRawTags("삼성.카카오.네이버.LG");

        Video result = videoRepository.findById(1L).get();

        assertThat(result.getRawTags()).isEqualTo("삼성.카카오.네이버.LG");
    }
    /**
     * 삭제
     */
    @Test
    void videoDeleteTest(){
        videoRepository.delete(expected);

        List<Video> result = videoRepository.findAll();

        assertThat(result.size()).isEqualTo(1);
    }

    /**
     * 모두 조회
     */
    @Test
    void findAllVideoTest(){
        List<Video> results = videoRepository.findAll();
        assertThat(results.size()).isEqualTo(2);
    }

    /**
     * 비디오 댓글조회
     */
    @Test
    @Transactional
    void findVideoComment(){
        List<VideoComment> results = videoRepository.findById(1L).get().getComments();

        for (VideoComment result : results) {
            if (result.getId().equals(comment1.getId()))
                assertThat(result.getContent()).isEqualTo(comment1.getContent());
            else assertThat(result.getContent()).isEqualTo(comment2.getContent());
        }
    }
}