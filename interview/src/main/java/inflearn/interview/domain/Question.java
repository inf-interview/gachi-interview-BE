package inflearn.interview.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workbook_id")
    private Workbook workbook;

    private String content;

    private String answer;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public Question() {
    }

    public Question(Workbook workbook, String content, String answer) {
        this.workbook = workbook;
        this.content = content;
        this.answer = answer;
        this.createdAt = LocalDateTime.now();
    }
}
