package inflearn.interview.user.service;

import inflearn.interview.user.domain.User;
import inflearn.interview.user.domain.UserCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("소셜 로그인에 성공 한 후 register메서드로 엑세스토큰과 리프레시토큰을 발급받는다")
    void test1() {
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

        String[] tokens = userService.register(user.getId());
        assertThat(tokens[0]).startsWith("ey");
        assertThat(tokens[1]).startsWith("ey");

    }

}