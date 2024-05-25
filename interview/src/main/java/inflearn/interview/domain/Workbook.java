package inflearn.interview.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Setter
public class Workbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "workbook", cascade = CascadeType.REMOVE)
    private List<Question> questions;

    private String title;

    private int numOfQuestion;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Workbook(User user, String title) {
        this.user = user;
        this.title = title;
        this.numOfQuestion = 0;
        this.createdAt = LocalDateTime.now();
    }

    public void increaseNumOfQuestion() {
        this.numOfQuestion++;
    }

    public void decreaseNumOfQuestion() {
        this.numOfQuestion--;
    }
}
