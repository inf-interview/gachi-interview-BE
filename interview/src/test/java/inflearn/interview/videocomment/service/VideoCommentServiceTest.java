package inflearn.interview.videocomment.service;

import inflearn.interview.question.domain.Question;
import inflearn.interview.question.domain.QuestionCreate;
import inflearn.interview.question.service.QuestionRepository;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.domain.UserCreate;
import inflearn.interview.user.service.UserRepository;
import inflearn.interview.video.domain.VideoCreate;
import inflearn.interview.video.service.VideoServiceImpl;
import inflearn.interview.videocomment.controller.response.VideoCommentListResponse;
import inflearn.interview.videocomment.domain.VideoComment;
import inflearn.interview.videocomment.domain.VideoCommentCreate;
import inflearn.interview.videocomment.domain.VideoCommentDelete;
import inflearn.interview.videocomment.domain.VideoCommentUpdate;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class VideoCommentServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkbookRepository workbookRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private VideoServiceImpl videoServiceImpl;

    @Autowired
    private VideoCommentService videoCommentService;

    @Autowired
    private VideoCommentRepository videoCommentRepository;

    private Long userId;
    private Long workbookId;
    private Long questionId;
    private Long videoId;

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

        VideoCreate videoCreate = VideoCreate.builder()
                .userId(userId)
                .exposure(true)
                .videoLink("videoLink.com")
                .videoTitle("제목입니다")
                .questions(new Long[]{questionId})
                .tags(new String[]{"백엔드", "BE"})
                .thumbnailLink("thumbnail.com")
                .build();

        Long videoId = videoServiceImpl.create(videoCreate);
        this.videoId = videoId;
    }

    @Test
    @DisplayName("사용자는 비디오 게시글에 댓글을 달 수 있다")
    void test1() {
        VideoCommentCreate videoCommentCreate = VideoCommentCreate.create(userId, "안녕하세용");
        VideoComment videoComment = videoCommentService.create(videoId, videoCommentCreate);

        assertThat(videoComment.getContent()).isEqualTo("안녕하세용");
        assertThat(videoComment.getTime()).isNotNull();
        assertThat(videoComment.getUser().getId()).isEqualTo(userId);
        assertThat(videoComment.getVideo().getId()).isEqualTo(videoId);
    }

    @Test
    @DisplayName("사용자는 비디오 댓글을 수정할 수 있다")
    void test2() {
        VideoCommentCreate videoCommentCreate = VideoCommentCreate.create(userId, "안녕하세용");
        VideoComment videoComment = videoCommentService.create(videoId, videoCommentCreate);

        VideoCommentUpdate videoCommentUpdate = VideoCommentUpdate.builder()
                .userId(userId)
                .commentId(videoComment.getId())
                .content("안녕안녕")
                .build();

        videoCommentService.update(videoCommentUpdate);

        VideoComment updated = videoCommentRepository.findById(videoComment.getId()).orElseThrow();

        assertThat(updated.getContent()).isEqualTo("안녕안녕");
        assertThat(updated.getTime()).isNotNull();
        assertThat(updated.getUpdatedTime()).isNotNull();
        assertThat(updated.getUser().getId()).isEqualTo(userId);
        assertThat(updated.getVideo().getId()).isEqualTo(videoId);

    }

    @Test
    @DisplayName("사용자는 비디오 댓글을 삭제할 수 있다")
    void test3() {
        VideoCommentCreate videoCommentCreate = VideoCommentCreate.create(userId, "안녕하세용");
        VideoComment videoComment = videoCommentService.create(videoId, videoCommentCreate);

        VideoCommentDelete videoCommentDelete = VideoCommentDelete.builder()
                .userId(userId)
                .commentId(videoComment.getId())
                .build();

        videoCommentService.delete(videoCommentDelete);

        Optional<VideoComment> deleted = videoCommentRepository.findById(videoComment.getId());

        assertThat(deleted).isEmpty();
    }

    @Test
    @DisplayName("사용자는 비디오 게시글에 달린 댓글 목록을 확인할 수 있다")
    void test4() {
        VideoCommentCreate videoCommentCreate = VideoCommentCreate.create(userId, "안녕하세용");
        videoCommentService.create(videoId, videoCommentCreate);

        VideoCommentCreate videoCommentCreate2 = VideoCommentCreate.create(userId, "안농");
        videoCommentService.create(videoId, videoCommentCreate2);

        List<VideoCommentListResponse> comments = videoCommentService.getComments(videoId);

        assertThat(comments).size().isEqualTo(2);
    }

}