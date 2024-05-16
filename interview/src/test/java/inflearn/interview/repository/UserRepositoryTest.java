package inflearn.interview.repository;

import inflearn.interview.AppConfig;
import inflearn.interview.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    UserRepository userRepository;

    User person1;
    User person2;

    @BeforeEach
    void before() {
        userRepository = (UserRepository) ac.getBean("userRepository");

        person1 = new User();
        person1.setName("권우현");
        person1.setUserId(1L);
        person1.setEmail("kwh1208@naver.com");
        person1.setSocial("네이버");
        person1.setTime(LocalDateTime.of(2024, 4, 2, 12, 0, 0));


        person2 = new User();
        person2.setName("고경희");
        person2.setUserId(2L);
        person2.setEmail("kwh871005@gmail.com");
        person2.setSocial("구글");
        person2.setTime(LocalDateTime.of(2024, 4, 2, 12, 0, 0));
    }

    @Test
    void userSaveTest(){
        userRepository.save(person1);
        Optional<User> find = userRepository.findById(1L);

        User result = find.get();

        assertThat(result).usingRecursiveComparison().isEqualTo(person1);
    }

    /**
     * 수정
     */
    @Test
    void userEditTest(){
        User saved = userRepository.save(person1);
        saved.setSocial("카카오");

        Optional<User> find = userRepository.findById(1L);

        User result = find.get();

        assertThat(result).usingRecursiveComparison().isEqualTo(person1);
    }

    /**
     * 삭제
     */
    @Test
    void userDeleteTest(){
        userRepository.save(person1);
        userRepository.delete(person1);

        List<User> result = userRepository.findAll();

        assertThat(result.size()).isEqualTo(0);
    }

    /**
     * 모두 조회
     */
    @Test
    void findAllUserTest(){
        userRepository.save(person1);
        userRepository.save(person2);

        List<User> results = userRepository.findAll();
        for (User result : results) {
            if (result.getUserId()==1L){
                assertThat(result).usingRecursiveComparison().isEqualTo(person1);
            }
            else assertThat(result).usingRecursiveComparison().isEqualTo(person2);
        }
    }


}
