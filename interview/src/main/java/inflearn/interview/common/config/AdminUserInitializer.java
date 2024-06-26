package inflearn.interview.common.config;

import inflearn.interview.user.domain.User;
import inflearn.interview.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class AdminUserInitializer {

    private final UserRepository userRepository;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            if (userRepository.findAdmin("ADMIN") == null) {
                String name = "같이면접 AI 서비스";

                User admin = new User();
                admin.setName(name);
                admin.setSocial("ADMIN");
                admin.setEmail("thstkddnr20@naver.com");
                admin.setCreatedAt(LocalDateTime.now());
                admin.setRole("ADMIN");
                admin.setImage("https://inf-video.s3.ap-northeast-2.amazonaws.com/KakaoTalk_Photo_2024-06-06-13-54-05.png");
                userRepository.save(admin);
            }
        };
    }
}
