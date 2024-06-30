package inflearn.interview.workbook.domain;

import inflearn.interview.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Workbook {
    private Long id;
    private User user;
    private String title;
    private int numOfQuestion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Workbook(Long id, User user, String title, int numOfQuestion, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.numOfQuestion = numOfQuestion;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Workbook from(User user, WorkbookCreate workbookCreate) {
        return Workbook.builder()
                .user(user)
                .title(workbookCreate.getTitle())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Workbook plusNumOfQuestion() {
        return Workbook.builder()
                .id(id)
                .user(user)
                .title(title)
                .numOfQuestion(numOfQuestion + 1)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public Workbook minusNumOfQuestion() {
        return Workbook.builder()
                .id(id)
                .user(user)
                .title(title)
                .numOfQuestion(numOfQuestion - 1)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
