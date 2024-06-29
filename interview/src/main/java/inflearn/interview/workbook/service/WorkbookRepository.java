package inflearn.interview.workbook.service;

import inflearn.interview.user.domain.User;
import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.workbook.domain.Workbook;
import inflearn.interview.workbook.infrastructure.WorkbookEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WorkbookRepository {

    List<WorkbookEntity> findAllByUserId(Long userId);

    Optional<Workbook> findById(Long id);

    Workbook save(Workbook workbook);

    void delete(Workbook workbook);
}
