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
    public String[] register(Long userId) {
        User user = userRepository.findById(userId).get();
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        user.setRefreshToken(refreshToken);

        String accessToken = jwtTokenProvider.createAccessToken(user);
        return new String[]{accessToken, refreshToken};
    }
}
