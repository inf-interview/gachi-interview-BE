package inflearn.interview.domain.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_table")
public class UserDAO {
    @Id
    @Column(name = "id")
    Long userId;
    @NotNull
    String name;
    @Email
    String email;
    @NotNull
    String social;
    @NotNull
    @Column(name = "created_at")
    LocalDateTime time;
    @Column(name = "updated_at")
    LocalDateTime updatedTime;
}
