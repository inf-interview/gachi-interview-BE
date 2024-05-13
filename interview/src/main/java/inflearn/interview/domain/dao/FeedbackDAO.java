package inflearn.interview.domain.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "feedback")
public class FeedbackDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String question;

    String content;

    LocalDateTime time;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserDAO user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private VideoDAO video;
}
