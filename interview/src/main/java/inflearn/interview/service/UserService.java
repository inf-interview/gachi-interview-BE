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
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    public User loginKakao(String code) {
        String accessToken = kakaoProvider.getAccessToken(code);
        String kakaoInfo = kakaoProvider.getKakaoInfo(accessToken);

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
            return user;
        }
        else {
            return findUser.get();
        }
    }

    public List<MyPostDTO> getMyPost(Long userId) {
        return postRepository.findPostByUserId(userId);
    }

    public List<PostCommentDTO> getMyComment(Long userId) {
        List<PostComment> comments = postCommentRepository.findCommentByUserId(userId);
        return comments.stream().map(comment -> new PostCommentDTO(comment)).toList();
    }
}
