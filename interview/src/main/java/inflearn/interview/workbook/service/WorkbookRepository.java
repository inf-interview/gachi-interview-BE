package inflearn.interview.workbook.service;

import inflearn.interview.workbook.domain.Workbook;
import inflearn.interview.workbook.infrastructure.WorkbookEntity;
import java.util.List;
import java.util.Optional;

public interface WorkbookRepository {

    List<WorkbookEntity> findAllByUserId(Long userId);

    Optional<Workbook> findById(Long id);

    Workbook save(Workbook workbook);

    void delete(Workbook workbook);
}
