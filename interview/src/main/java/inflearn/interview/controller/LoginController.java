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
public class LoginController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Value("${spring.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.google.client_id}")
    private String googleClientId;

    /**
     * 카카오 로그인
     */
    @GetMapping("/user/kakao")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+ kakaoClientId + "&redirect_uri=http://localhost:8080/user/kakao/login";
        response.sendRedirect(url);
    }

    @GetMapping("/user/kakao/login")
    public ResponseEntity<String[]> kakaoGetInfo(@RequestParam String code) {
        User user = userService.loginKakao(code);

        String[] tokens = authenticationService.register(user);
        log.info("accessToken = {}", tokens[0]);
        return ResponseEntity.status(HttpStatus.OK).body(tokens); // accessToken, refreshToken 반환
    }

    /**
     * 구글 로그인
     */
    @GetMapping("/user/google")
    public void googleLogin(HttpServletResponse response) throws IOException {
        String url = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId + "&redirect_uri=http://localhost:8080/user/google/login&response_type=code&scope=email%20profile%20openid&access_type=offline&prompt=consent";
        response.sendRedirect(url);
    }

    @GetMapping("/user/google/login")
    public ResponseEntity<String[]> googleGetInfo(@RequestParam String code) {
        User user = userService.loginGoogle(code);

        String[] tokens = authenticationService.register(user);
        log.info("accessToken = {}", tokens[0]);
        return ResponseEntity.status(HttpStatus.OK).body(tokens); // accessToken, refreshToken 반환
    }

}
