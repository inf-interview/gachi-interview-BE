package inflearn.interview.workbook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.question.controller.response.QuestionListResponse;
import inflearn.interview.question.domain.Question;
import inflearn.interview.question.domain.QuestionCreate;
import inflearn.interview.question.service.QuestionRepository;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.domain.UserCreate;
import inflearn.interview.user.service.UserRepository;
import inflearn.interview.workbook.domain.Workbook;
import inflearn.interview.workbook.domain.WorkbookCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class QuestionServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkbookService workbookService;

    @Autowired
    private WorkbookRepository workbookRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private Long userId;
    private Long workbookId;

    @BeforeEach
    void initData() throws JsonProcessingException {
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
                .job("")
                .title("제목입니다")
                .userId(userId)
                .build();

        Workbook workbook = workbookService.createWorkbook(workbookCreate);
        this.workbookId = workbook.getId();
    }

    @Test
    @DisplayName("사용자는 Question(질문,답변)을 생성할 수 있다")
    void test1() {
        QuestionCreate questionCreate = QuestionCreate.builder()
                .userId(userId)
                .questionContent("질문이에요")
                .answerContent("답변이에요")
                .build();

        Question question = workbookService.createQuestion(workbookId, questionCreate);

        assertThat(question.getWorkbook().getId()).isEqualTo(workbookId);
        assertThat(question.getContent()).isEqualTo("질문이에요");
        assertThat(question.getAnswer()).isEqualTo("답변이에요");
        assertThat(question.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("사용자는 Question을 삭제할 수 있다")
    void test2() {
        QuestionCreate questionCreate = QuestionCreate.builder()
                .userId(userId)
                .questionContent("질문이에요")
                .answerContent("답변이에요")
                .build();

        Question question = workbookService.createQuestion(workbookId, questionCreate);

        workbookService.deleteQuestion(workbookId, question.getId());

        Optional<Question> getQuestion = questionRepository.findById(question.getId());

        assertThat(getQuestion).isEmpty();
    }

    @Test
    @DisplayName("사용자는 Workbook에 들어있는 Question 리스트를 조회할 수 있다")
    void test3() {
        QuestionCreate questionCreate1 = QuestionCreate.builder()
                .userId(userId)
                .questionContent("질문이에요")
                .answerContent("답변이에요")
                .build();

        workbookService.createQuestion(workbookId, questionCreate1);

        QuestionCreate questionCreate2 = QuestionCreate.builder()
                .userId(userId)
                .questionContent("질문이에요")
                .answerContent("답변이에요")
                .build();

        workbookService.createQuestion(workbookId, questionCreate2);

        List<QuestionListResponse> response = workbookService.findQuestionList(workbookId);

        assertThat(response.get(0).getQuestionContent()).isEqualTo("질문이에요");
        assertThat(response.get(0).getAnswerContent()).isEqualTo("답변이에요");
        assertThat(response.get(1).getQuestionContent()).isEqualTo("질문이에요");
        assertThat(response.get(1).getAnswerContent()).isEqualTo("답변이에요");


    }

    @Test
    @DisplayName("Question을 Workbook에 추가할 경우 Workbook의 numOfQuestion이 1 늘어난다")
    void test4() {
        QuestionCreate questionCreate = QuestionCreate.builder()
                .userId(userId)
                .questionContent("질문이에요")
                .answerContent("답변이에요")
                .build();

        workbookService.createQuestion(workbookId, questionCreate);

        Workbook workbook = workbookRepository.findById(workbookId).orElseThrow();

        assertThat(workbook.getNumOfQuestion()).isEqualTo(1);
    }

    @Test
    @DisplayName("Question이 Workbook에서 삭제될 경우 Workbook의 numOfQuestion이 1 줄어든다")
    void test5() {
        QuestionCreate questionCreate = QuestionCreate.builder()
                .userId(userId)
                .questionContent("질문이에요")
                .answerContent("답변이에요")
                .build();

        Question question = workbookService.createQuestion(workbookId, questionCreate);

        workbookService.deleteQuestion(workbookId, question.getId());

        Workbook workbook = workbookRepository.findById(workbookId).orElseThrow();

        assertThat(workbook.getNumOfQuestion()).isEqualTo(0);
    }

    @Test
    @DisplayName("Question이 들어있는 Workbook이 삭제되면 Question도 함께 삭제된다")
    void test6() {
        QuestionCreate questionCreate = QuestionCreate.builder()
                .userId(userId)
                .questionContent("질문이에요")
                .answerContent("답변이에요")
                .build();

        Question question = workbookService.createQuestion(workbookId, questionCreate);

        workbookService.deleteWorkbook(workbookId);

        Optional<Question> getQuestion = questionRepository.findById(question.getId());

        assertThat(getQuestion).isEmpty();
    }


}
