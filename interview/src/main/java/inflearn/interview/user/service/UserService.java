package inflearn.interview.user.service;

import inflearn.interview.common.service.JwtTokenProvider;
import inflearn.interview.notice.controller.response.NoticeResponse;
import inflearn.interview.notice.infrastructure.NoticeRepository;
import inflearn.interview.post.domain.Post;
import inflearn.interview.user.domain.User;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.user.infrastructure.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;

    public String[] register(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        updateRefreshToken(user, refreshToken);

        String accessToken = jwtTokenProvider.createAccessToken(user);
        return new String[]{accessToken, refreshToken};
    }

    private void updateRefreshToken(User user, String refreshToken) {
        User updatedUser = user.setRefreshToken(refreshToken);
        userRepository.save(updatedUser);
    }

    public List<NoticeResponse> getMyNotice(Long userId) { // TODO 수정 필요
        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        return noticeRepository.findByUserEntity(UserEntity.fromModel(user)).stream().map(NoticeResponse::new).sorted(Comparator.comparing(NoticeResponse::getCreatedAt).reversed()).toList();
    }

}
