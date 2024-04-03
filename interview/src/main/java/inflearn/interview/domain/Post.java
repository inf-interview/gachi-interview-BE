package inflearn.interview.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    private String content;

    private String tag;

    private String category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Post(String title, String content, String tag, String category) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }
}