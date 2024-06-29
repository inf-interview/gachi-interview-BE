package inflearn.interview.question.domain;

import inflearn.interview.workbook.domain.Workbook;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Question {
    private Long id;
    private Workbook workbook;
    private String content;
    private String answer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Question(Long id, Workbook workbook, String content, String answer, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.workbook = workbook;
        this.content = content;
        this.answer = answer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Question from(Workbook workbook, CreateQuestion createQuestion) {
        return Question.builder()
                .workbook(workbook)
                .content(createQuestion.getQuestionContent())
                .answer(createQuestion.getAnswerContent())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
