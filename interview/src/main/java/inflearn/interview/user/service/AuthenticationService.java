package inflearn.interview.user.service;

import inflearn.interview.common.service.JwtTokenProvider;
import inflearn.interview.user.domain.User;
import inflearn.interview.common.exception.OptionalNotFoundException;
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
        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        user.setRefreshToken(refreshToken);

        String accessToken = jwtTokenProvider.createAccessToken(user);
        return new String[]{accessToken, refreshToken};
    }
}
