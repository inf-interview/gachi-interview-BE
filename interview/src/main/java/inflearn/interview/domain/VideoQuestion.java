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
    @JoinColumn(name = "video_id")
    private Video video;

    //GPT에 전달할 Question의 id를 찾기 위해 저장
    private Long questionId;

    //GPT에 전달할 질문 내용
    private String question;

    public VideoQuestion(Video video, Long questionId, String question) {
        this.video = video;
        this.questionId = questionId;
        this.question = question;
    }

    public VideoQuestion() {
    }
}
