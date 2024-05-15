package inflearn.interview.domain.dto;

import inflearn.interview.domain.Workbook;
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
