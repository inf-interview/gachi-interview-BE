package inflearn.interview.service;

import inflearn.interview.domain.User;
import inflearn.interview.exception.OptionalNotFoundException;
import inflearn.interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
