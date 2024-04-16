package inflearn.interview.filter;

import inflearn.interview.service.CustomUserDetailsService;
import inflearn.interview.service.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
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
import org.springframework.security.core.userdetails.UserDetailsService;
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

        try {
            jwt = authHeader.substring(7);
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
        } catch (ExpiredJwtException e) {
            String id = recreateAccessToken(request, response, e);
            response.setHeader("userId", id);
        }
        filterChain.doFilter(request,response);
    }

    private String recreateAccessToken(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        try {
            log.info("기존 토큰 만료");
            String refreshToken = request.getHeader("Refresh-Token");
            if (refreshToken == null) {
                throw exception;
            }

            String rawOldAccessToken = request.getHeader("Authorization"); //만료된 토큰
            String oldAccessToken = rawOldAccessToken.substring(7);

            String newAccessToken = jwtTokenProvider.validateRefreshToken(refreshToken, oldAccessToken);

            String id = jwtTokenProvider.extractUsername(newAccessToken);
            UserDetails userDetails = userDetailsService.loadUserById(Long.parseLong(id));

            response.setHeader("New-Access-Token", newAccessToken);
            log.info("newAccessToken={}", newAccessToken);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // 자격 증명
                    null
            );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);

            return id;

        } catch (Exception e) {
            request.setAttribute("exception", e);
            return null;
        }

    }
}
