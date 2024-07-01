package inflearn.interview.video.service;

import inflearn.interview.common.exception.RequestDeniedException;
import inflearn.interview.question.domain.Question;
import inflearn.interview.question.domain.QuestionCreate;
import inflearn.interview.question.service.QuestionRepository;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.domain.UserCreate;
import inflearn.interview.user.service.UserRepository;
import inflearn.interview.video.controller.response.VideoDetailResponse;
import inflearn.interview.video.domain.*;
import inflearn.interview.videoquestion.domain.QuestionFromVideoQuestion;
import inflearn.interview.videoquestion.service.VideoQuestionRepository;
import inflearn.interview.workbook.domain.Workbook;
import inflearn.interview.workbook.domain.WorkbookCreate;
import inflearn.interview.workbook.service.WorkbookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class VideoServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoServiceImpl videoServiceImpl;

    @Autowired
    private FakeVideoService fakeVideoService;

    @Autowired
    private WorkbookRepository workbookRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private VideoQuestionRepository videoQuestionRepository;

    private Long userId;
    private Long videoId;
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
    @DisplayName("사용자는 비디오 게시글을 생성할 수 있다")
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

        assertThat(video.getId()).isEqualTo(id);
        assertThat(video.getExposure()).isTrue();
        assertThat(video.getTime()).isNotNull();
        assertThat(video.getUpdatedTime()).isNull();
        assertThat(video.getVideoTitle()).isEqualTo("제목입니다");
        assertThat(video.getThumbnailLink()).isEqualTo("thumbnail.com");
        assertThat(video.getTag()).isEqualTo("백엔드.BE.");
        assertThat(video.getVideoLink()).isEqualTo("videoLink.com");
        assertThat(video.getUser().getId()).isEqualTo(userId);
        assertThat(video.getNumOfLike()).isEqualTo(0);
    }

    @Test
    @DisplayName("사용자는 비디오 게시글을 수정할 수 있다")
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

        VideoUpdate videoUpdate = VideoUpdate.builder()
                .userId(userId)
                .videoId(video.getId())
                .exposure(false)
                .videoTitle("제목 수정했어요")
                .tags(new String[]{"엔드백", "EB"})
                .build();

        videoServiceImpl.update(videoUpdate);
        Video updated = videoRepository.findById(id).orElseThrow();

        assertThat(updated.getId()).isEqualTo(id);
        assertThat(updated.getExposure()).isFalse();
        assertThat(updated.getTime()).isNotNull();
        assertThat(updated.getUpdatedTime()).isNotNull();
        assertThat(updated.getVideoTitle()).isEqualTo("제목 수정했어요");
        assertThat(updated.getThumbnailLink()).isEqualTo("thumbnail.com");
        assertThat(updated.getTag()).isEqualTo("엔드백.EB.");
        assertThat(updated.getVideoLink()).isEqualTo("videoLink.com");
        assertThat(updated.getUser().getId()).isEqualTo(userId);
        assertThat(video.getNumOfLike()).isEqualTo(0);

    }

    @Test
    @DisplayName("사용자는 비디오 게시글을 삭제할 수 있다") //S3와 엮여 있어 테스트가 불가했지만 의존성 역전을 이용하여 FakeVideoService로 삭제 테스트 완료
    void test3() {
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

        VideoDelete videoDelete = VideoDelete.builder()
                .userId(userId)
                .videoId(video.getId())
                .build();

        fakeVideoService.delete(videoDelete);

        Optional<Video> findVideo = videoRepository.findById(video.getId());

        assertThat(findVideo).isEmpty();

    }

    @Test
    @DisplayName("사용자는 비디오 게시글 상세보기를 할 수 있다")
    void test4() {
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

        User user = userRepository.findById(userId).orElseThrow();

        VideoDetailResponse response = videoServiceImpl.getVideoById(id, user);

        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getUserName()).isEqualTo(user.getName());
        assertThat(response.getVideoId()).isEqualTo(id);
        assertThat(response.isExposure()).isTrue();
        assertThat(response.getVideoTitle()).isEqualTo("제목입니다");
        assertThat(response.getTime()).isNotNull();
        assertThat(response.getUpdateTime()).isNull();
        assertThat(response.getNumOfLike()).isEqualTo(0);
        assertThat(response.getTags().length).isEqualTo(2);
        assertThat(response.getThumbnailLink()).isEqualTo("thumbnail.com");
        assertThat(response.getVideoLink()).isEqualTo("videoLink.com");
        assertThat(response.getImage()).isEqualTo(user.getImage());
        assertThat(response.isLiked()).isFalse();


    }

    @Test
    @DisplayName("사용자는 비공개 비디오 게시글에는 본인이 아니라면 접근 불가능하다")
    void test5() {
        VideoCreate videoCreate = VideoCreate.builder()
                .userId(userId)
                .exposure(false)
                .videoLink("videoLink.com")
                .videoTitle("제목입니다")
                .questions(new Long[]{questionId})
                .tags(new String[]{"백엔드", "BE"})
                .thumbnailLink("thumbnail.com")
                .build();

        Long id = videoServiceImpl.create(videoCreate);

        UserCreate userCreate = UserCreate.builder()
                .name("창수")
                .email("ckdtn@naver.com")
                .social("KAKAO")
                .createdAt(LocalDateTime.now())
                .image("imageLink")
                .role("USER")
                .build();

        User user = User.from(userCreate);
        user = userRepository.save(user);


        User finalUser = user;
        assertThatThrownBy(() -> videoServiceImpl.getVideoById(id, finalUser)).isExactlyInstanceOf(RequestDeniedException.class);

    }

    @Test
    @DisplayName("사용자는 본인의 비공개 게시글에는 접근 가능하다")
    void test6() {
        VideoCreate videoCreate = VideoCreate.builder()
                .userId(userId)
                .exposure(false)
                .videoLink("videoLink.com")
                .videoTitle("제목입니다")
                .questions(new Long[]{questionId})
                .tags(new String[]{"백엔드", "BE"})
                .thumbnailLink("thumbnail.com")
                .build();

        Long id = videoServiceImpl.create(videoCreate);

        User user = userRepository.findById(userId).orElseThrow();

        VideoDetailResponse response = videoServiceImpl.getVideoById(id, user);

        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getUserName()).isEqualTo(user.getName());
        assertThat(response.getVideoId()).isEqualTo(id);
        assertThat(response.isExposure()).isFalse();
        assertThat(response.getVideoTitle()).isEqualTo("제목입니다");
        assertThat(response.getTime()).isNotNull();
        assertThat(response.getUpdateTime()).isNull();
        assertThat(response.getNumOfLike()).isEqualTo(0);
        assertThat(response.getTags().length).isEqualTo(2);
        assertThat(response.getThumbnailLink()).isEqualTo("thumbnail.com");
        assertThat(response.getVideoLink()).isEqualTo("videoLink.com");
        assertThat(response.getImage()).isEqualTo(user.getImage());
        assertThat(response.isLiked()).isFalse();

    }

    @Test
    @DisplayName("사용자는 공개 게시글의 목록을 sortType과 keyword로 확인할 수 있다")
    void test7() {
        VideoCreate videoCreate1 = VideoCreate.builder()
                .userId(userId)
                .exposure(true)
                .videoLink("videoLink.com")
                .videoTitle("제목입니다")
                .questions(new Long[]{questionId})
                .tags(new String[]{"백엔드", "BE"})
                .thumbnailLink("thumbnail.com")
                .build();

        videoServiceImpl.create(videoCreate1);

        VideoCreate videoCreate2 = VideoCreate.builder()
                .userId(userId)
                .exposure(false)
                .videoLink("videoLink.com")
                .videoTitle("제목입니다")
                .questions(new Long[]{questionId})
                .tags(new String[]{"백엔드", "BE"})
                .thumbnailLink("thumbnail.com")
                .build();

        videoServiceImpl.create(videoCreate2);

        Page<VideoDTO2> list = videoServiceImpl.getVideoList("new", "", 1);

        assertThat(list).size().isEqualTo(1);

    }

    @Test
    @DisplayName("사용자는 비디오 게시글을 생성할 때 AI에게 질문하기위한 VideoQuestion도 같이 만들어진다")
    void test8() {
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

        List<QuestionFromVideoQuestion> questions = videoQuestionRepository.findQuestionsByVideoId(id);

        assertThat(questions).size().isEqualTo(1);
        assertThat(questions.get(0).getQuestion()).isEqualTo("질문1");
    }


}