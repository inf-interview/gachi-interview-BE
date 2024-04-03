package inflearn.interview.domain.DO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {
    @NotNull
    Long userId;
    @NotNull
    String name;
    @NotNull
    String email;
    @NotNull
    String social;
    @NotNull
    String createdAt;
    String updatedAt;
}
