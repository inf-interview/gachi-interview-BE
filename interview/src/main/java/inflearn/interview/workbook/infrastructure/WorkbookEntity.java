package inflearn.interview.workbook.infrastructure;

import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.workbook.domain.Workbook;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Setter
@Table(name = "workbook")
public class WorkbookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private String title;

    private int numOfQuestion;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static WorkbookEntity fromModel(Workbook workbook) {
        WorkbookEntity workbookEntity = new WorkbookEntity();
        workbookEntity.id = workbook.getId();
        workbookEntity.userEntity = UserEntity.fromModel(workbook.getUser());
        workbookEntity.title = workbook.getTitle();
        workbookEntity.numOfQuestion = workbook.getNumOfQuestion();
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
