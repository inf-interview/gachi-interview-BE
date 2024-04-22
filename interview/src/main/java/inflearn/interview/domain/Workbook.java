package inflearn.interview.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Workbook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String title;
    private int numOfQuestion = 0;


    public Workbook(User user, String title) {
        this.user = user;
        this.title = title;
        this.numOfQuestion = 0;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void increaseNumOfQuestion() {
        this.numOfQuestion++;
    }

    public void decreaseNumOfQuestion() {
        this.numOfQuestion--;
    }
}
