package inflearn.interview.user.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import inflearn.interview.common.service.GoogleProvider;
import inflearn.interview.common.service.KakaoProvider;
import inflearn.interview.user.controller.response.LoginResponse;
import inflearn.interview.user.domain.User;
import inflearn.interview.user.domain.UserCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final KakaoProvider kakaoProvider;
    private final GoogleProvider googleProvider;
    private final UserRepository userRepository;


    public LoginResponse loginKakao(String code, String isLocal) { // 반환 값 User, RefreshToken
        String accessToken = kakaoProvider.getAccessToken(code, isLocal);
        String kakaoInfo = kakaoProvider.getKakaoInfo(accessToken);

        JsonObject jsonObject = JsonParser.parseString(kakaoInfo).getAsJsonObject();

        //이메일 추출
        String email = jsonObject.getAsJsonObject("kakao_account").get("email").getAsString();

        //닉네임 추출
        String nickname = jsonObject.getAsJsonObject("properties").get("nickname").getAsString();

        //이미지 추출
        String image = jsonObject.getAsJsonObject("properties").get("thumbnail_image").getAsString();

        //유저 정보가 DB에 있는지 체크
        Optional<User> findUser = userRepository.findUserByEmailAndSocial(email, "KAKAO");
        if (findUser.isEmpty()) {
            UserCreate userCreate = create(nickname, email, "KAKAO", image, "USER");
            User user = User.from(userCreate);
            User saved = userRepository.save(user);

            return createLoginResponse(nickname, image, saved.getId());

        }

        return createLoginResponse(nickname, image, findUser.get().getId());
    }

    public LoginResponse loginGoogle(String code, String isLocal) {

        String accessToken = googleProvider.getAccessToken(code, isLocal);
        String googleInfo = googleProvider.getGoogleInfo(accessToken);

        JsonObject jsonObject = JsonParser.parseString(googleInfo).getAsJsonObject();

        String name = jsonObject.get("name").getAsString();
        String email = jsonObject.get("email").getAsString();
        String image = jsonObject.get("picture").getAsString();


        Optional<User> findUser = userRepository.findUserByEmailAndSocial(email, "GOOGLE");
        if (findUser.isEmpty()) {
            UserCreate userCreate = create(name, email, "GOOGLE", image, "USER");
            User user = User.from(userCreate);
            User saved = userRepository.save(user);

            return createLoginResponse(name, image, saved.getId());
        }

        return createLoginResponse(name, image, findUser.get().getId());

    }

    private LoginResponse createLoginResponse(String username, String image, Long userId) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(username);
        loginResponse.setImage(image);
        loginResponse.setUserId(userId);
        return loginResponse;
    }

    private UserCreate create(String name, String email, String social, String image, String role) {
        return UserCreate.builder()
                .name(name)
                .email(email)
                .social(social)
                .image(image)
                .role(role)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
