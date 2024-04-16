package inflearn.interview.dto;

import inflearn.interview.domain.Workbook;
import lombok.Data;

@Data
public class WorkbookDto {
    Long workbookId;
    Long userId;
    String userName;
    String title;

    public WorkbookDto(Workbook workbook) {
        this.workbookId = workbook.getId();
        this.userId = workbook.getUser().getUserId();
        this.userName = workbook.getUser().getName();
        this.title = workbook.getTitle();
    }
}
