package inflearn.interview.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {
    Long userId;
    String name;
    String email;
    String social;
    String createdAt;
    String updatedAt;
}
