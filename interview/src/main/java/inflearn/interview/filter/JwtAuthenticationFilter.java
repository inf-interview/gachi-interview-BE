package inflearn.interview.filter;

import inflearn.interview.exception.TokenNotValidateException;
import inflearn.interview.service.CustomUserDetailsService;
import inflearn.interview.service.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); // 요청을 하면 헤더가 토큰과 함께 패스해야한다.
        final String jwt;
        final String userId;

        if (authHeader == null || !authHeader.startsWith("Bearer ")){ // 조건이 충족되지 않으면 추가 처리 없이 필터체인을 계속한다.
            filterChain.doFilter(request,response); // 다음 필터로 넘어감
            return;
        }

        jwt = authHeader.substring(7);

        try {
            userId = jwtTokenProvider.extractUsername(jwt);


            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) { // userId가 비어있지않고, 사용자가 이미 인증을 받지 않았다면
                UserDetails userDetails = this.userDetailsService.loadUserById(Long.parseLong(userId)); // userId을 사용하여 userDetails를 불러온다
                if (jwtTokenProvider.isTokenValid(jwt, userDetails)) {
                    if (!jwtTokenProvider.isTokenExpired(jwt)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, // 자격 증명
                                null
                        );

                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        response.setHeader("userId", userId);
                    }
                }
            }
        } catch (ExpiredJwtException e) { //AccessToken 만료
            String rawRefreshToken = request.getHeader("RefreshToken");
            try {
                if (rawRefreshToken != null) {
                    String refreshToken = rawRefreshToken.substring(7);
                    if (!jwtTokenProvider.isTokenExpired(refreshToken)) {
                        recreateAccessToken(request, response, refreshToken);
                        return;
                    }
                }
            } catch (ExpiredJwtException expiredRefreshToken) {
                throw new TokenNotValidateException("리프레시 토큰이 만료되어 재로그인이 필요합니다", expiredRefreshToken);
            }
            throw new TokenNotValidateException("만료된 AccessToken, RefreshToken이 필요합니다", e);

        } catch (UnsupportedJwtException e) { //지원되지 않는 jwt 토큰
            throw new TokenNotValidateException("지원되지 않는 JWT 토큰입니다", e);
        } catch (MalformedJwtException e) { //잘못된 서명(변조)
            throw new TokenNotValidateException("잘못된 서명의 JWT 토큰입니다", e);
        }

        filterChain.doFilter(request,response);
    }

    private void recreateAccessToken(HttpServletRequest request, HttpServletResponse response, String refreshToken) {

        log.info("기존 Access 토큰 만료");
        UserDetails getUser = jwtTokenProvider.validateRefreshToken(refreshToken);//refreshToken 검증 후 유저정보 가져옴

        try {
            String accessToken = jwtTokenProvider.createAccessToken(getUser);

            String id = jwtTokenProvider.extractUsername(accessToken);
            UserDetails userDetails = userDetailsService.loadUserById(Long.parseLong(id));

            log.info("newAccessToken={}", accessToken);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // 자격 증명
                    null
            );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);

            response.setHeader("Authorization", "Bearer " + accessToken);
            response.setHeader("userId", id);

        } catch (Exception e) {
            log.info("exception {}", e.getMessage());
            throw new TokenNotValidateException("엑세스 토큰 재생성중 오류 발생", e);
        }

    }
}
