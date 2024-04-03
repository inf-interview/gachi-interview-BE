package inflearn.interview.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class PostComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postCommentId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
