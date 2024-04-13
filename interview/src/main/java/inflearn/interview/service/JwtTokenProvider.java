package inflearn.interview.service;

import inflearn.interview.domain.User;
import inflearn.interview.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    @Value("${spring.jwt.secret_key}")
    private String secretKey;

    private final UserRepository userRepository;

    public String createAccessToken(UserDetails userDetails) {

        Date now = new Date();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (30 * 60 * 1000L))) // 30분
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(UserDetails userDetails) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (60 * 60 * 24 * 7 * 1000L))) //일주일
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { //claims 객체를 받아서 사용자가 원하는 타입 T로 반환하는 Function 사용
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token); // 토큰으로부터 유저네임을 분리 한 후
        return (username.equals(userDetails.getUsername())); // 가져온 유저네임이 유저 디테일에 있는것이 맞는지 체크
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody(); // io.jsonwebtoken.Claims 반환
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes); // 키를 디코드해서 시크릿 키를 가져옴
    }

    @Transactional(readOnly = true)
    public String validateRefreshToken(String refreshToken, String oldAccessToken) { //refreshToken과 만료된 AccessToken을 사용하여 refreshToken 검증
        try {
            // DB에 있는 refreshToken과 같은지
            Long id = Long.parseLong(extractUsername(refreshToken));
            User findUser = userRepository.findById(id).get();

            if (refreshToken.equals(findUser.getRefreshToken())) {
                log.info("리프레시 토큰 동일");
                // refreshToken 형태가 맞는지 만료되지 않았는지
                if (!isTokenExpired(refreshToken) && isTokenValid(refreshToken, findUser)) {
                    log.info("사용자 정보 동일, RefreshToken 유효기간 만료되지 않음");
                    //AccessToken 재생성
                    String newAccessToken = recreateAccessToken(id);
                    return newAccessToken;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    private String recreateAccessToken(Long id) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (30 * 60 * 1000L)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
