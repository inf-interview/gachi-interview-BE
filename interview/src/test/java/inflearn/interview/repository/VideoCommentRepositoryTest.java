package inflearn.interview.repository;

import inflearn.interview.domain.dao.UserDAO;
import inflearn.interview.domain.dao.VideoCommentDAO;
import inflearn.interview.domain.dao.VideoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VideoCommentRepositoryTest {
    @Autowired VideoRepository videoRepository;
    @Autowired UserRepository userRepository;
    @Autowired VideoCommentRepository commentRepository;

    VideoDAO expected;
    VideoDAO expected2;

    VideoCommentDAO comment1;
    VideoCommentDAO comment2;

    UserDAO person1;
    UserDAO person2;

    @BeforeEach
    void before(){
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


        comment1 = new VideoCommentDAO();
        comment1.setId(1L);
        comment1.setContent("ㅎㅎ");
        comment1.setTime(LocalDateTime.of(2024,4,2,12,0,0));
        comment1.setUpdatedTime(LocalDateTime.of(2024,4,2,12,0,0));
        comment1.setUser(person1);
        comment1.setVideo(expected);

        comment2 = new VideoCommentDAO();
        comment2.setId(2L);
        comment2.setContent("ㅋㅋ");
        comment2.setTime(LocalDateTime.of(2024,4,2,12,0,0));
        comment2.setUpdatedTime(LocalDateTime.of(2024,4,2,12,0,0));
        comment2.setUser(person2);
        comment2.setVideo(expected2);

        videoRepository.save(expected);
        videoRepository.save(expected2);

        userRepository.save(person1);
        userRepository.save(person2);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
    }


        /**
         * 저장
         */
        @Test
        @Transactional
        void videoCommentSaveTest(){
            VideoCommentDAO result = commentRepository.findById(1L).get();

            assertThat(result).usingRecursiveComparison().isEqualTo(comment1);
        }

        /**
         * 수정
         */
        @Test
        @Transactional
        void videoCommentEditTest(){
            VideoCommentDAO saved = commentRepository.findById(1L).get();

            saved.setContent("수정했습니다.");

            VideoCommentDAO result = commentRepository.findById(1L).get();

            assertThat(result.getContent()).isEqualTo("수정했습니다.");
        }

        /**
         * 삭제
         */
        @Test
        void videoCommentDeleteTest(){
            commentRepository.delete(comment1);

            List<VideoCommentDAO> result = commentRepository.findAll();

            assertThat(result.size()).isEqualTo(1);
        }

        /**
         * 모두 조회
         */
        @Test
        void findAllVideoCommentTest(){
            List<VideoCommentDAO> results = commentRepository.findAll();
            assertThat(results.size()).isEqualTo(2);
        }

}

