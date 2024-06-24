package inflearn.interview.service;

import inflearn.interview.TestRepository;
import inflearn.interview.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class AuthenticationServiceTest {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private AuthenticationService authenticationService;


    @Test
    void 유저는_register메서드로_엑세스토큰과_리프레시토큰을_생성할_수_있다() {
        // given
        User user = testRepository.saveUser();

        // when
        String[] tokens = authenticationService.register(user.getUserId());

        // then
        assertThat(tokens).isNotEmpty();
    }
}