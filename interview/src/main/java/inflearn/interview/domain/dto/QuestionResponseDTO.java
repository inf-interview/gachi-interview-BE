package inflearn.interview.domain.dto;

import inflearn.interview.domain.Question;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionResponseDTO {

    private Long questionId;
    private String questionContent;
    private String answerContent;

    public QuestionResponseDTO(Question question) {
        this.questionId = question.getId();
        this.questionContent = question.getContent();
        this.answerContent = question.getAnswer();
    }
}
