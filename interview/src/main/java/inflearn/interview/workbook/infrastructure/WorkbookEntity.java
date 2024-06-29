package inflearn.interview.workbook.infrastructure;

import inflearn.interview.question.infrastructure.QuestionEntity;
import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.workbook.domain.Workbook;
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
public class WorkbookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "workbookEntity", cascade = CascadeType.REMOVE)
    private List<QuestionEntity> questionEntities;

    private String title;

    private int numOfQuestion;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public WorkbookEntity(UserEntity userEntity, String title) {
        this.userEntity = userEntity;
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

    public static WorkbookEntity fromModel(Workbook workbook) {
        WorkbookEntity workbookEntity = new WorkbookEntity();
        workbookEntity.id = workbook.getId();
        workbookEntity.userEntity = UserEntity.fromModel(workbook.getUser());
        workbookEntity.title = workbook.getTitle();
        workbookEntity.numOfQuestion = workbookEntity.getNumOfQuestion();
        workbookEntity.createdAt = workbook.getCreatedAt();
        workbookEntity.updatedAt = workbook.getUpdatedAt();
        return workbookEntity;
    }

    public Workbook toModel() {
        return Workbook.builder()
                .id(id)
                .user(userEntity.toModel())
                .title(title)
                .numOfQuestion(numOfQuestion)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
