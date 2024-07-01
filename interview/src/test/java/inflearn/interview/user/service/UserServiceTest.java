package inflearn.interview.user.service;

import inflearn.interview.notice.controller.response.NoticeResponse;
import inflearn.interview.notice.infrastructure.Notice;
import inflearn.interview.notice.infrastructure.NoticeRepository;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.domain.UserCreate;
import inflearn.interview.user.infrastructure.UserEntity;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoticeRepository noticeRepository;

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

    @Test
    @DisplayName("(MY) 사용자는 본인의 알림 목록을 조회할 수 있다")
    void test2() {

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

        Notice notice = new Notice(UserEntity.fromModel(user), "알림");
        notice = noticeRepository.save(notice);

        List<NoticeResponse> response = userService.getMyNotice(user.getId());

        assertThat(response).size().isEqualTo(1);
        assertThat(response.get(0).getId()).isEqualTo(notice.getId());
        assertThat(response.get(0).getContent()).isEqualTo("알림");
        assertThat(response.get(0).getCreatedAt()).isEqualTo(notice.getCreatedAt());
    }
}