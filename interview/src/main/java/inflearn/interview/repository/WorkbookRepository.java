package inflearn.interview.repository;

import inflearn.interview.domain.User;
import inflearn.interview.domain.Workbook;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkbookRepository extends JpaRepository<Workbook, Long> {

    List<Workbook> findAllByUser(User user);

    Optional<Workbook> findById(Long id);

}
