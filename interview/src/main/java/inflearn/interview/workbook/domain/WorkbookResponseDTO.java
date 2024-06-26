package inflearn.interview.workbook.domain;

import inflearn.interview.workbook.domain.Workbook;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkbookResponseDTO {
    Long listId;
    String title;

    public WorkbookResponseDTO(Workbook workbook) {
        this.listId = workbook.getId();
        this.title = workbook.getTitle();
    }

}
