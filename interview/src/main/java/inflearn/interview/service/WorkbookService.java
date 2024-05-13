package inflearn.interview.service;

import inflearn.interview.domain.User;
import inflearn.interview.domain.Workbook;
import inflearn.interview.repository.WorkbookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkbookService {

    private final WorkbookRepository workbookRepository;

    public List<Workbook> getWorkbooks() {
        return workbookRepository.findAll();
    }

    public Workbook createWorkbook(User user, String title) {
        // TODO : 반환값 재확인 필요
        Workbook workbook = new Workbook(user, title);
        return workbookRepository.save(workbook);
    }

    public Workbook findWorkbook(Long workbookId) {
        return workbookRepository.findById(workbookId).orElseGet(null);
    }


    public Workbook updateWorkbook(Long workbookId, String newTitle) {
        Workbook workbook = findWorkbook(workbookId);
        workbook.setTitle(newTitle);
        return workbookRepository.save(workbook);
    }

    public void deleteWorkbook(Long workbookId) {
        Workbook workbook = workbookRepository.findById(workbookId).get();
        workbookRepository.delete(workbook);
    }
}

