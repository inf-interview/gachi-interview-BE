package inflearn.interview.fcm.controller;

import inflearn.interview.fcm.domain.FcmTokenDTO;
import inflearn.interview.fcm.service.FcmTokenService;
import inflearn.interview.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FcmTokenService fcmTokenService;

    //fcmToken
    @PostMapping("/user/fcm/token")
    public ResponseEntity<?> getFcmToken(@RequestBody FcmTokenDTO tokenDTO, @AuthenticationPrincipal User user) {
        fcmTokenService.registerToken(user, tokenDTO.getFcmToken());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
