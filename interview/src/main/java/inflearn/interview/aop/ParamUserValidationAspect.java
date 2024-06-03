package inflearn.interview.aop;

import inflearn.interview.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ParamUserValidationAspect {

    @Pointcut(value = "@annotation(ValidateUserParam) && (args(.., userId) || args(userId, ..))")
    public void validateUserPointcut(Long userId) {
    }

    @Before(value = "validateUserPointcut(userId)", argNames = "userId")
    public void validate(Long userId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User getUser = (User) authentication.getPrincipal();

        if (userId.equals(getUser.getUserId())) {
            log.info("유저 아이디 검증 성공");
        }

//        if (userId == null) {
//            if (user.getUserId().equals(getUser.getUserId())) {
//                log.info("유저 검증 성공");
//            }
//        } else {
//            if (userId.equals(getUser.getUserId())) {
//                log.info("유저 아이디 검증 성공");
//            }
//        }
    }
}
