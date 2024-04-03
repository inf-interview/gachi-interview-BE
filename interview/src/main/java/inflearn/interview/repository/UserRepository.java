package inflearn.interview.repository;

import inflearn.interview.domain.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserDAO, Long> {
}