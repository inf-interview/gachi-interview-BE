package inflearn.interview.user.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import inflearn.interview.notice.domain.NoticeDTO;
import inflearn.interview.notice.service.NoticeRepository;
import inflearn.interview.post.domain.MyPostDTO;
import inflearn.interview.post.service.PostRepository;
import inflearn.interview.postcomment.domain.PostComment;
import inflearn.interview.postcomment.domain.PostCommentDTO;
import inflearn.interview.common.service.GoogleProvider;
import inflearn.interview.common.service.KakaoProvider;
import inflearn.interview.postcomment.service.PostCommentRepository;
import inflearn.interview.user.domain.LoginResponse;
import inflearn.interview.user.domain.User;
import inflearn.interview.video.domain.MyVideoDTO;
import inflearn.interview.video.service.VideoRepository;
import inflearn.interview.videocomment.domain.VideoComment;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.videocomment.domain.VideoCommentDTO;
import inflearn.interview.videocomment.service.VideoCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final KakaoProvider kakaoProvider;
    private final GoogleProvider googleProvider;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final VideoCommentRepository videoCommentRepository;
    private final NoticeRepository noticeRepository;
    private final VideoRepository videoRepository;

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
            User user = new User();
            user.setName(nickname);
            user.setEmail(email);
            user.setSocial("KAKAO");
            user.setCreatedAt(LocalDateTime.now());
            user.setImage(image);
            user.setRole("USER");
            userRepository.save(user);

            return createLoginResponse(nickname, image, user.getUserId());

        }

        return createLoginResponse(nickname, image, findUser.get().getUserId());
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
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setSocial("GOOGLE");
            user.setCreatedAt(LocalDateTime.now());
            user.setImage(image);
            user.setRole("USER");
            userRepository.save(user);

            return createLoginResponse(name, image, user.getUserId());
        }

        return createLoginResponse(name, image, findUser.get().getUserId());

    }

    public List<MyPostDTO> getMyPost(Long userId, String category) {
        return postRepository.findPostByUserId(userId, category);
    }

    public List<MyVideoDTO> getMyVideo(Long userId) {
        return videoRepository.findVideoByUserId(userId);
    }


    public List<PostCommentDTO> getMyComment(Long userId) {
        List<PostComment> postComments = postCommentRepository.findCommentByUserId(userId);
        return postComments.stream().map(comment -> new PostCommentDTO(comment)).toList();
    }

    public List<VideoCommentDTO> getMyVideoComment(Long userId) {
        List<VideoComment> videoComments = videoCommentRepository.findCommentByUserId(userId);
        return videoComments.stream().map(comment -> new VideoCommentDTO(comment)).toList();
    }

    private LoginResponse createLoginResponse(String username, String image, Long userId) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(username);
        loginResponse.setImage(image);
        loginResponse.setUserId(userId);
        return loginResponse;
    }

    public List<NoticeDTO> getMyNotice(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        return noticeRepository.findByUser(user).stream().map(NoticeDTO::new).sorted(Comparator.comparing(NoticeDTO::getCreatedAt).reversed()).toList();
    }
}
