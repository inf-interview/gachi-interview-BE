package inflearn.interview.workbook.domain;

import inflearn.interview.common.domain.BaseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class WorkbookCreate implements BaseDTO {

    private Long userId;
    private String title;
    private String job;

    @Builder
    public WorkbookCreate(Long userId, String title, String job) {
        this.userId = userId;
        this.title = title;
        this.job = job;
    }
}
