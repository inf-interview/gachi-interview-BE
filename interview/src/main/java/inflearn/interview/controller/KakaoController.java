package inflearn.interview.controller;

import inflearn.interview.domain.User;
import inflearn.interview.service.AuthenticationService;
import inflearn.interview.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class KakaoController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Value("${spring.kakao.client_id}")
    private String clientId;

    @GetMapping("/getAuth")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        //인가 코드 받기
        String url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+ clientId + "&redirect_uri=http://localhost:8080/user/login";
        response.sendRedirect(url);
    }

    @GetMapping("/login")
    public void kakaoGetInfo(@RequestParam String code) {

        User user = userService.loginKakao(code);
        log.info("user = {}", user);

        //받은 유저 정보로 jwt 토큰 생성, 반환
        String token = authenticationService.register(user);
        log.info("token = {}", token);

    }
}
