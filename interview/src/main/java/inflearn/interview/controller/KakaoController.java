package inflearn.interview.controller;

import inflearn.interview.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class KakaoController {

    private final UserService userService;

    @Value("${spring.kakao.client_id}")
    private String clientId;

    @GetMapping("/login")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+ clientId + "&redirect_uri=http://localhost:8080/user/getInfo";
        response.sendRedirect(url);
    }

    @GetMapping("/getInfo")
    public void kakaoGetInfo(@RequestParam String code) {
        userService.loginKakao(code);
    }
}
