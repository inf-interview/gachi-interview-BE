package inflearn.interview.workbook.service;

import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.workbook.domain.Workbook;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkbookRepository extends JpaRepository<Workbook, Long> {

    List<Workbook> findAllByUser(UserEntity userEntity);

    Optional<Workbook> findById(Long id);

}
