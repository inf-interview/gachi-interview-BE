package inflearn.interview.service;

import inflearn.interview.TestRepository;
import inflearn.interview.domain.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class GptCallCountServiceTest {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private GptCallCountService gptCallCountService;

    @Autowired
    private EntityManager em;

    private Long id;

    @BeforeEach
    void setUser() {
        User user = new User();
        user.setEmail("thstkddnr20@naver.com");
        user.setName("손상욱");
        user.setCreatedAt(LocalDateTime.now());
        user.setRole("USER");
        user.setSocial("KAKAO");
        testRepository.save(user);
        this.id = user.getUserId();
    }

    @Test
    @DisplayName("사용자는 AI기능을 사용할 경우 gptCallCount가 증가한다")
    void test1() {
        User user = (User) testRepository.findById(User.class, this.id);

        gptCallCountService.plusInterviewCallCount(user.getUserId());
        em.flush();
        em.clear();

        user = (User) testRepository.findById(User.class, this.id);
        assertThat(user.getInterviewGptCallCount()).isEqualTo(1);
    }


}