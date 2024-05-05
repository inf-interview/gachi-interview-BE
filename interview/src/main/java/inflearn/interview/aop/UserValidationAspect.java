package inflearn.interview.aop;

import inflearn.interview.domain.User;
import inflearn.interview.domain.dto.BaseDTO;
import inflearn.interview.domain.dto.ErrorResponse;
import inflearn.interview.domain.dto.PostDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class UserValidationAspect {

    @Pointcut("@annotation(ValidateUser) && (args(dto, ..) || args(.., dto))")
    public void validateUserPointcut(BaseDTO dto) {}

    @Around(value = "validateUserPointcut(dto)", argNames = "joinPoint,dto")
    public Object validateUserBefore(ProceedingJoinPoint joinPoint, BaseDTO dto) {

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();

            if (dto instanceof PostDTO) {
                PostDTO postDTO = (PostDTO) dto;
                if (!user.getUserId().equals(postDTO.getUserId())) {
                    ErrorResponse errorResponse = createErrorResponse(request.getRequestURI());
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
                }
            }
            // 추가 가능

            return joinPoint.proceed();
        }

        catch (Throwable throwable) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(throwable.getMessage());
        }

    }

    private ErrorResponse createErrorResponse(String path) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "validation failed", "유저 검증에 실패하였습니다.", path);
    }

}
