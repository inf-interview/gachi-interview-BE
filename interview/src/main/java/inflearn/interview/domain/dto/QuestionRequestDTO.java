package inflearn.interview.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequestDTO implements BaseDTO{

    private Long userId;
    private String questionContent;
    private String answerContent;

    public QuestionRequestDTO() {
    }

    public QuestionRequestDTO(Long userId, String questionContent, String answerContent) {
        this.userId = userId;
        this.questionContent = questionContent;
        this.answerContent = answerContent;
    }
}
