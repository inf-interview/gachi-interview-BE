package inflearn.interview.fcm.infrastructure;

import inflearn.interview.fcm.domain.Fcm;
import inflearn.interview.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class FcmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String FcmToken;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity userEntity;

    public static FcmEntity fromModel(Fcm fcm) {
        FcmEntity fcmEntity = new FcmEntity();
        fcmEntity.id = fcm.getId();
        fcmEntity.FcmToken = fcm.getFcmToken();
        fcmEntity.createdAt = fcm.getCreatedAt();
        fcmEntity.userEntity = UserEntity.fromModel(fcm.getUser());
        return fcmEntity;
    }

    public Fcm toModel() {
        return Fcm.builder()
                .id(id)
                .fcmToken(FcmToken)
                .createdAt(createdAt)
                .user(userEntity.toModel())
                .build();
    }
}
