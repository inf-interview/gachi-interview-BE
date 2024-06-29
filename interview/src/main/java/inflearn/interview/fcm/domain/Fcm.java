package inflearn.interview.fcm.domain;

import inflearn.interview.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Fcm {
    private Long id;
    private String fcmToken;
    private LocalDateTime createdAt;
    private User user;

    @Builder
    public Fcm(Long id, String fcmToken, LocalDateTime createdAt, User user) {
        this.id = id;
        this.fcmToken = fcmToken;
        this.createdAt = createdAt;
        this.user = user;
    }

    public static Fcm from(String fcmToken, User user) {
        return Fcm.builder()
                .fcmToken(fcmToken)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }

    public Fcm update(String fcmToken) {
        return Fcm.builder()
                .id(id)
                .fcmToken(fcmToken)
                .createdAt(createdAt)
                .user(user)
                .build();
    }
}
