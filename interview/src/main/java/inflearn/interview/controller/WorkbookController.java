package inflearn.interview.controller;

import inflearn.interview.dto.WorkbookDto;
import inflearn.interview.service.WorkbookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // TODO: 로컬 테스트용. 이후 삭제 필요
@RequestMapping("/interview/question")
@Controller
public class WorkbookController {

    private final WorkbookService workbookService;

    @GetMapping(value = "/list")
    public ResponseEntity<List<WorkbookDto>> getWorkbookList() {
        List<WorkbookDto> workbooks = workbookService.getWorkbooks();

        return new ResponseEntity<>(workbooks, HttpStatus.OK);
    }

}




