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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GoogleController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Value("${spring.google.client_id}")
    private String clientId;


    @GetMapping("/user/google")
    public void googleLogin(HttpServletResponse response) throws IOException {
        //인가 코드
        String url = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + clientId + "&redirect_uri=http://localhost:8080/user/google/login&response_type=code&scope=email%20profile%20openid&access_type=offline&prompt=consent";
        response.sendRedirect(url);
    }

    @GetMapping("/user/google/login")
    public ResponseEntity<String[]> googleGetInfo(@RequestParam String code) {

        log.info("최초로그인 로직 시작");
        Object[] userAndToken = userService.loginGoogle(code);

        //받은 유저 정보로 jwt 토큰 생성, 반환
        String[] tokens = authenticationService.register((User) userAndToken[0]);
        log.info("accessToken = {}", tokens[0]);
        return ResponseEntity.status(HttpStatus.OK).body(tokens); // accessToken, refreshToken
    }
}
