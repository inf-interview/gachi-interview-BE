package inflearn.interview.controller;

import inflearn.interview.domain.User;
import inflearn.interview.service.AuthenticationService;
import inflearn.interview.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/user/kakao")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        //인가 코드 받기
        String url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+ clientId + "&redirect_uri=http://localhost:8080/user/login";
        response.sendRedirect(url);
    }

    @GetMapping("/user/login")
    public ResponseEntity<String[]> kakaoGetInfo(@RequestParam(required = false) String code, HttpServletRequest request, HttpServletResponse response) {
        //쿠키 확인
        Cookie[] cookies = request.getCookies();
        Cookie getCookie = validCookie(cookies);

        //쿠키가 refresh-token이 있을경우
        if (getCookie != null) {
            log.info("재로그인 로직 시작");
            Object[] userAndToken = userService.reLoginKakao(getCookie.getValue());
            if (userAndToken[1] != null) {
                getCookie.setMaxAge(0); // 기존 쿠키 제거

                Cookie newCookie = createCookie((String) userAndToken[1]);
                response.addCookie(newCookie);
            }
            String[] tokens = authenticationService.register((User) userAndToken[0]);
            log.info("accessToken={}", tokens[0]);
            return ResponseEntity.status(HttpStatus.OK).body(tokens);
        }

        log.info("최초로그인 로직 시작");
        //쿠키에 refresh-token이 없으므로 최초 로그인
        Object[] userAndToken = userService.loginKakao(code); //User정보, refreshToken

        Cookie cookie = createCookie((String) userAndToken[1]);
        response.addCookie(cookie);

        //받은 유저 정보로 jwt 토큰 생성, 반환
        String[] tokens = authenticationService.register((User) userAndToken[0]);
        log.info("accessToken = {}", tokens[0]);
        return ResponseEntity.status(HttpStatus.OK).body(tokens); // accessToken, refreshToken
    }

    private Cookie validCookie(Cookie[] cookies) { // 쿠키에 리프레시 토큰이 있는지 확인
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("kakao-refresh-token")) {
                    return cookie;
                }
            }
        }
        return null;
    }

    private Cookie createCookie(String refreshToken) {
        Cookie cookie = new Cookie("kakao-refresh-token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(604800); //1주일 (임시)
        return cookie;
    }
}
