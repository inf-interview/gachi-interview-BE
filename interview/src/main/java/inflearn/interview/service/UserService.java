package inflearn.interview.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import inflearn.interview.domain.User;
import inflearn.interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final KakaoProvider kakaoProvider;
    private final UserRepository userRepository;

    public void loginKakao(String code) {
        String kakaoInfo = kakaoProvider.getKakaoInfo(code);

        Gson gson = new Gson();

        JsonObject jsonObject = JsonParser.parseString(kakaoInfo).getAsJsonObject();

        //이메일 추출
        String email = jsonObject.getAsJsonObject("kakao_account").get("email").getAsString();

        //닉네임 추출
        String nickname = jsonObject.getAsJsonObject("properties").get("nickname").getAsString();

        User user = new User();
        user.setName(nickname);
        user.setEmail(email);
        user.setSocial("KAKAO");
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
