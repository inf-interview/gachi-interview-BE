package inflearn.interview.videoquestion.infrastructure;

import inflearn.interview.video.infrastructure.VideoEntity;
import inflearn.interview.videoquestion.domain.VideoQuestion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "video_question")
public class VideoQuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private VideoEntity videoEntity;

    //GPT에 전달할 Question의 id를 찾기 위해 저장
    private Long questionId;

    //GPT에 전달할 질문 내용
    private String question;

    public static VideoQuestionEntity fromModel(VideoQuestion videoQuestion) {
        VideoQuestionEntity videoQuestionEntity = new VideoQuestionEntity();
        videoQuestionEntity.id = videoQuestion.getId();
        videoQuestionEntity.videoEntity = VideoEntity.fromModel(videoQuestion.getVideo());
        videoQuestionEntity.questionId = videoQuestion.getQuestionId();
        videoQuestionEntity.question = videoQuestion.getQuestion();
        return videoQuestionEntity;
    }

    public VideoQuestion toModel() {
        return VideoQuestion.builder()
                .id(id)
                .video(videoEntity.toModel())
                .questionId(questionId)
                .question(question)
                .build();
    }
}
