package inflearn.interview.user.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserCreate {

    private String name;
    private String email;
    private String social;
    private LocalDateTime createdAt;
    private String image;
    private String role;

    @Builder
    public UserCreate(String name, String email, String social, LocalDateTime createdAt, String image, String role) {
        this.name = name;
        this.email = email;
        this.social = social;
        this.createdAt = createdAt;
        this.image = image;
        this.role = role;
    }
}
