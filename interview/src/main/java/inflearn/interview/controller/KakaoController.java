package inflearn.interview.controller;

import inflearn.interview.domain.User;
import inflearn.interview.service.AuthenticationService;
import inflearn.interview.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Value("${spring.kakao.client_id}")
    private String clientId;

    @GetMapping("/user/getAuth")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        //인가 코드 받기
        String url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+ clientId + "&redirect_uri=http://localhost:8080/user/login";
        response.sendRedirect(url);
    }

    @GetMapping("/user/login")
    public ResponseEntity<String[]> kakaoGetInfo(@RequestParam String code) {

        User user = userService.loginKakao(code);
        log.info("user = {}", user);

        //받은 유저 정보로 jwt 토큰 생성, 반환
        String[] tokens = authenticationService.register(user);
        log.info("accessToken = {}", tokens[0]);
        return ResponseEntity.status(HttpStatus.OK).body(tokens); // accessToken, refreshToken
    }
}
