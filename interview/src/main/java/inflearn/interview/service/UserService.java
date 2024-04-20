package inflearn.interview.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import inflearn.interview.domain.PostComment;
import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.MyPostDTO;
import inflearn.interview.domain.dto.PostCommentDTO;
import inflearn.interview.domain.dto.PostDTO;
import inflearn.interview.repository.PostCommentRepository;
import inflearn.interview.repository.PostRepository;
import inflearn.interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final KakaoProvider kakaoProvider;
    private final GoogleProvider googleProvider;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;


    public Object[] loginKakao(String code) { // 반환 값 User, RefreshToken
        String[] tokens = kakaoProvider.getAccessToken(code);
        String kakaoInfo = kakaoProvider.getKakaoInfo(tokens[0]);

        JsonObject jsonObject = JsonParser.parseString(kakaoInfo).getAsJsonObject();

        //이메일 추출
        String email = jsonObject.getAsJsonObject("kakao_account").get("email").getAsString();

        //닉네임 추출
        String nickname = jsonObject.getAsJsonObject("properties").get("nickname").getAsString();

        //유저 정보가 DB에 있는지 체크
        Optional<User> findUser = userRepository.findUserByEmail(email);
        if (findUser.isEmpty()) {
            User user = new User();
            user.setName(nickname);
            user.setEmail(email);
            user.setSocial("KAKAO");
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
            return new Object[]{user, tokens[1]};
        }
        return new Object[]{findUser.get(), tokens[1]}; // User, RefreshToken 반환

    }

    public Object[] reLoginKakao(String refreshToken) {
        String[] tokens = kakaoProvider.getAccessTokenByRefreshToken(refreshToken); // accessToken, refreshToken(Optional)

        //유저 정보 가져오기
        String kakaoInfo = kakaoProvider.getKakaoInfo(tokens[0]);

        JsonObject jsonObject = JsonParser.parseString(kakaoInfo).getAsJsonObject();

        String email = jsonObject.getAsJsonObject("kakao_account").get("email").getAsString();

        Optional<User> findUser = userRepository.findUserByEmail(email);
        if (findUser.isEmpty()) {
            //쿠키에는 refreshToken이 있지만 찾아온 유저 정보가 없는경우
        }

        return new Object[]{findUser.get(), tokens[1]};

    }

    public Object[] loginGoogle(String code) {

        String[] tokens = googleProvider.getAccessToken(code);
        String googleInfo = googleProvider.getGoogleInfo(tokens[0]);

        log.info("info={}", googleInfo);

        JsonObject jsonObject = JsonParser.parseString(googleInfo).getAsJsonObject();

        String name = jsonObject.get("name").getAsString();
        String email = jsonObject.get("email").getAsString();

        Optional<User> findUser = userRepository.findUserByEmail(email);
        if (findUser.isEmpty()) {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setSocial("GOOGLE");
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
            return new Object[]{user, tokens[1]};
        }
        return new Object[]{findUser.get(), tokens[1]};
    }

    public User reLoginGoogle(String refreshToken) {
        String accessToken = googleProvider.getAccessTokenByRefreshToken(refreshToken);
        String googleInfo = googleProvider.getGoogleInfo(accessToken);

        JsonObject jsonObject = JsonParser.parseString(googleInfo).getAsJsonObject();

        String email = jsonObject.get("email").getAsString();

        Optional<User> findUser = userRepository.findUserByEmail(email);
        if (findUser.isEmpty()) {
            //쿠키에는 refreshToken이 있지만 찾아온 유저 정보가 없는경우
        }

        return findUser.get();
    }

    public List<MyPostDTO> getMyPost(Long userId) {
        return postRepository.findPostByUserId(userId);
    }

    public List<PostCommentDTO> getMyComment(Long userId) {
        List<PostComment> comments = postCommentRepository.findCommentByUserId(userId);
        return comments.stream().map(comment -> new PostCommentDTO(comment)).toList();
    }
}
