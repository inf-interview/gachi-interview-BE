package inflearn.interview.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkbookRequestDTO implements BaseDTO{

    private Long userId;

    private String title;

    private String job;

}
