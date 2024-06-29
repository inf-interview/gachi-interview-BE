package inflearn.interview.question.infrastructure;

import inflearn.interview.question.domain.Question;
import inflearn.interview.workbook.infrastructure.WorkbookEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "question")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workbook_id")
    private WorkbookEntity workbookEntity;

    private String content;

    private String answer;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static QuestionEntity fromModel(Question question) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.id = question.getId();
        questionEntity.workbookEntity = WorkbookEntity.fromModel(question.getWorkbook());
        questionEntity.content = question.getContent();
        questionEntity.answer = question.getAnswer();
        questionEntity.createdAt = question.getCreatedAt();
        questionEntity.updatedAt = question.getUpdatedAt();
        return questionEntity;
    }

    public Question toModel() {
        return Question.builder()
                .id(id)
                .workbook(workbookEntity.toModel())
                .content(content)
                .answer(answer)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
