package inflearn.interview.service;

import inflearn.interview.domain.User;
import inflearn.interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    public String register(User user) {
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        User findUser = userRepository.findById(user.getUserId()).get();
        findUser.setRefreshToken(refreshToken);

        return jwtTokenProvider.createAccessToken(user);// 토큰 생성하여 JwtTokenResponse로 반환
    }
}
