package inflearn.interview.notice.infrastructure;

import inflearn.interview.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    private String content;

    private LocalDateTime createdAt;

    public Notice(UserEntity userEntity, String content) {
        this.userEntity = userEntity;
        this.content = content;
    }
}
