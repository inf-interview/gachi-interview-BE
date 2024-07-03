package inflearn.interview.workbook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.domain.UserCreate;
import inflearn.interview.user.service.UserRepository;
import inflearn.interview.workbook.controller.response.WorkbookListResponse;
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
class WorkbookServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkbookService workbookService;

    @Autowired
    private WorkbookRepository workbookRepository;

    private Long userId;

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

    }

    @Test
    @DisplayName("사용자는 Workbook(질문답변목록)을 생성할 수 있다")
    void test1() throws JsonProcessingException {
        WorkbookCreate workbookCreate = WorkbookCreate.builder()
                .job("")
                .title("제목입니다")
                .userId(userId)
                .build();

        Workbook workbook = workbookService.createWorkbook(workbookCreate);

        assertThat(workbook.getUser().getId()).isEqualTo(userId);
        assertThat(workbook.getTitle()).isEqualTo("제목입니다");
        assertThat(workbook.getNumOfQuestion()).isEqualTo(0);


    }

    @Test
    @DisplayName("사용자는 Workbook 리스트를 조회할 수 있다")
    void test2() throws JsonProcessingException {
        WorkbookCreate workbookCreate1 = WorkbookCreate.builder()
                .job("")
                .title("제목입니다")
                .userId(userId)
                .build();

        workbookService.createWorkbook(workbookCreate1);

        WorkbookCreate workbookCreate2 = WorkbookCreate.builder()
                .job("")
                .title("제목입니다")
                .userId(userId)
                .build();

        workbookService.createWorkbook(workbookCreate2);

        List<WorkbookListResponse> list = workbookService.getWorkbookList(userId);

        assertThat(list).size().isEqualTo(2);
        assertThat(list.get(0).getTitle()).isEqualTo("제목입니다");
        assertThat(list.get(1).getTitle()).isEqualTo("제목입니다");
    }

    @Test
    @DisplayName("사용자는 Workbook을 삭제할 수 있다")
    void test3() throws JsonProcessingException {
        WorkbookCreate workbookCreate = WorkbookCreate.builder()
                .job("")
                .title("제목입니다")
                .userId(userId)
                .build();

        Workbook workbook = workbookService.createWorkbook(workbookCreate);

        workbookService.deleteWorkbook(workbook.getId());

        Optional<Workbook> getWorkbook = workbookRepository.findById(workbook.getId());

        assertThat(getWorkbook).isEmpty();
    }

}