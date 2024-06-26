package inflearn.interview.common.filter;

import inflearn.interview.common.domain.ErrorResponse;
import inflearn.interview.common.exception.TokenNotValidateException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenNotValidateException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, request, response, ex);
        }
    }

    private void setErrorResponse(HttpStatus httpStatus, HttpServletRequest request, HttpServletResponse response, Throwable ex) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json; charset=UTF-8");

        response.getWriter().write(new ErrorResponse(httpStatus.value(), "Unauthorized", ex.getMessage(), request.getRequestURI()).convertToJson());
    }
}
