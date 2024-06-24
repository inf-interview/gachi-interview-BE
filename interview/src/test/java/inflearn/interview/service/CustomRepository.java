package inflearn.interview.service;

import inflearn.interview.TestRepository;
import inflearn.interview.domain.User;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@Transactional
public class CustomRepository implements TestRepository {

    private final EntityManager em;

    public CustomRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User saveUser() {
        User user = new User();
        user.setRole("USER");
        user.setName("손상욱");
        user.setEmail("thstkddnr20@naver.com");
        user.setSocial("KAKAO");
        user.setCreatedAt(LocalDateTime.now());
        em.persist(user);
        return user;
    }
}
