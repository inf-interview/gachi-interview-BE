package inflearn.interview.service;

import inflearn.interview.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    public String register(User user) {
        return jwtTokenProvider.createToken(user);// 토큰 생성하여 JwtTokenResponse로 반환
    }
}
