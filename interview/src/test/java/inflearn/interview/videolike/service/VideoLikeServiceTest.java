package inflearn.interview.videolike.service;

import inflearn.interview.question.domain.Question;
import inflearn.interview.question.domain.QuestionCreate;
import inflearn.interview.question.service.QuestionRepository;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.domain.UserCreate;
import inflearn.interview.user.service.UserRepository;
import inflearn.interview.video.domain.Video;
import inflearn.interview.video.domain.VideoCreate;
import inflearn.interview.video.service.VideoRepository;
import inflearn.interview.video.service.VideoServiceImpl;
import inflearn.interview.videolike.domain.VideoLikeRequest;
import inflearn.interview.workbook.domain.Workbook;
import inflearn.interview.workbook.domain.WorkbookCreate;
import inflearn.interview.workbook.service.WorkbookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class VideoLikeServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkbookRepository workbookRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private VideoServiceImpl videoServiceImpl;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoLikeService videoLikeService;



    private Long userId;
    private Long workbookId;
    private Long questionId;

    @BeforeEach
    void initData() {
        UserCreate userCreate = UserCreate.builder()
                .name("상욱")
                .email("thstkddnr20@naver.com")
                .social("KAKAO")
                .createdAt(LocalDateTime.now())
                .image("imageLink")
                .role("USER")
                .build();

        User user = User.from(userCreate);
        user = userRepository.save(user);
        this.userId = user.getId();

        WorkbookCreate workbookCreate = WorkbookCreate.builder()
                .userId(userId)
                .title("워크북입니다")
                .job("개발/BE")
                .build();
        Workbook workbook = Workbook.from(user, workbookCreate);
        workbook = workbookRepository.save(workbook);
        this.workbookId = workbook.getId();

        QuestionCreate questionCreate = QuestionCreate.builder()
                .userId(userId)
                .questionContent("질문1")
                .answerContent("답변1")
                .build();

        Question question = Question.from(workbook, questionCreate);
        question = questionRepository.save(question);
        this.questionId = question.getId();

    }

    @Test
    @DisplayName("사용자는 비디오 게시글에 좋아요를 누를 수 있다")
    void test1() {
        VideoCreate videoCreate = VideoCreate.builder()
                .userId(userId)
                .exposure(true)
                .videoLink("videoLink.com")
                .videoTitle("제목입니다")
                .questions(new Long[]{questionId})
                .tags(new String[]{"백엔드", "BE"})
                .thumbnailLink("thumbnail.com")
                .build();

        Long id = videoServiceImpl.create(videoCreate);

        Video video = videoRepository.findById(id).orElseThrow();

        VideoLikeRequest videoLikeRequest = VideoLikeRequest.builder()
                .userId(userId)
                .videoId(video.getId())
                .build();

        videoLikeService.addLike(videoLikeRequest);
        video = videoRepository.findById(id).orElseThrow();

        assertThat(video.getNumOfLike()).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자는 좋아요를 한번 더 누르면 좋아요 취소를 할 수 있다")
    void test2() {
        VideoCreate videoCreate = VideoCreate.builder()
                .userId(userId)
                .exposure(true)
                .videoLink("videoLink.com")
                .videoTitle("제목입니다")
                .questions(new Long[]{questionId})
                .tags(new String[]{"백엔드", "BE"})
                .thumbnailLink("thumbnail.com")
                .build();

        Long id = videoServiceImpl.create(videoCreate);

        Video video = videoRepository.findById(id).orElseThrow();

        VideoLikeRequest videoLikeRequest = VideoLikeRequest.builder()
                .userId(userId)
                .videoId(video.getId())
                .build();

        videoLikeService.addLike(videoLikeRequest);
        videoLikeService.addLike(videoLikeRequest);
        video = videoRepository.findById(id).orElseThrow();

        assertThat(video.getNumOfLike()).isEqualTo(0);
    }
}