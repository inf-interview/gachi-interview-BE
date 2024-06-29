package inflearn.interview.workbook.domain;

import inflearn.interview.common.domain.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWorkbook implements BaseDTO {

    private Long userId;

    private String title;

    private String job;

}
