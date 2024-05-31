package inflearn.interview.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class VideoQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    public VideoQuestion(Video video, Question question) {
        this.video = video;
        this.question = question;
    }

    public VideoQuestion() {
    }
}
