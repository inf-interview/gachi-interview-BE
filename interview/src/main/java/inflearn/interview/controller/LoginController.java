package inflearn.interview.controller;

import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.FcmTokenDTO;
import inflearn.interview.domain.dto.LoginResponse;
import inflearn.interview.service.AuthenticationService;
import inflearn.interview.service.FcmTokenService;
import inflearn.interview.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final FcmTokenService fcmTokenService;

    @Value("${spring.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.google.client_id}")
    private String googleClientId;

    /**
     * 카카오 로그인
     */
    @GetMapping("/user/kakao")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakaoClientId + "&redirect_uri=http://localhost:8080/user/kakao/login";
        response.sendRedirect(url);
    }

    @GetMapping("/user/kakao/login")
    public ResponseEntity<LoginResponse> kakaoGetInfo(@RequestParam String code, HttpServletRequest request) {
        String isLocal = kakaoCheckLocal(request);

        LoginResponse loginResponse = userService.loginKakao(code, isLocal);

        String[] tokens = authenticationService.register(loginResponse.getUserId());
        log.info("accessToken = {}", tokens[0]);

        loginResponse.setAccessToken(tokens[0]);
        loginResponse.setRefreshToken(tokens[1]);

        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
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
    public ResponseEntity<LoginResponse> googleGetInfo(@RequestParam String code, HttpServletRequest request) {
        String isLocal = googleCheckLocal(request);

        LoginResponse loginResponse = userService.loginGoogle(code, isLocal);

        String[] tokens = authenticationService.register(loginResponse.getUserId());

        loginResponse.setAccessToken(tokens[0]);
        loginResponse.setRefreshToken(tokens[1]);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse); // accessToken, refreshToken 반환
    }

    //fcmToken
    @PostMapping("/user/fcm/token")
    public ResponseEntity<?> getFcmToken(@RequestBody FcmTokenDTO tokenDTO, @AuthenticationPrincipal User user) {
        boolean isAlreadyRegister = fcmTokenService.registerToken(user, tokenDTO.getFcmToken());
        if (isAlreadyRegister) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

    }
    private String kakaoCheckLocal(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        String isLocal = "PUBLISH";
        if (referer == null) {
            isLocal = "BE";
            log.info("BE 로컬 접속");
        } else if (referer.contains("localhost:3000")) {
            isLocal = "FE";
            log.info("FE 로컬 접속");
        }
        return isLocal;
    }

    private String googleCheckLocal(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        String isLocal = "PUBLISH";
        if (referer.contains("accounts.google.com")) {
            isLocal = "BE";
            log.info("BE 로컬 접속");
        } else if (referer.contains("localhost:3000")) {
            isLocal = "FE";
            log.info("FE 로컬 접속");
        }
        return isLocal;
    }
}
