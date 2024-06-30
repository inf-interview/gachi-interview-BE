package inflearn.interview.question.domain;

import inflearn.interview.common.domain.BaseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionCreate implements BaseDTO {

    private Long userId;
    private String questionContent;
    private String answerContent;

    public QuestionCreate() {
    }

    @Builder
    public QuestionCreate(Long userId, String questionContent, String answerContent) {
        this.userId = userId;
        this.questionContent = questionContent;
        this.answerContent = answerContent;
    }

}
