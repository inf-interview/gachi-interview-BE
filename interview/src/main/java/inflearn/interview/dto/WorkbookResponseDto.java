package inflearn.interview.dto;

import inflearn.interview.domain.Workbook;
import lombok.Data;

@Data
public class WorkbookResponseDto {
    Long workbookId;
    Long userId;
    String userName;
    String title;
    int numOfQuestion;

//    public WorkbookResponseDto(Workbook workbook) {
//        this.workbookId = workbook.getId();
//        this.userId = workbook.getUser().getUserId();
//        this.userName = workbook.getUser().getName();
//        this.title = workbook.getTitle();
//        this.numOfQuestion = workbook.getNumOfQuestion();
//    }

    // TODO : 테스트용. 이후 원상복구 필요
    public WorkbookResponseDto(Workbook workbook) {
        this.workbookId = workbook.getId();
        this.userId = 0L;
        this.userName = "user1";
        this.title = workbook.getTitle();
        this.numOfQuestion = workbook.getNumOfQuestion();
    }
}
