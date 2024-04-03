package inflearn.interview.converter;

import inflearn.interview.AppConfig;
import inflearn.interview.domain.dao.UserDAO;
import inflearn.interview.domain.dao.VideoDAO;
import inflearn.interview.domain.dto.VideoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class VideoConverterTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    VideoDAOToDTOConverter DAOToDTOConverter;
    VideoDTOToDAOConverter DTOToDAOConverter;

    VideoDAO expectedDAO;
    VideoDTO expectedDTO;

    UserDAO person1;


    /**
     * 초기값 설정
     */
    @BeforeEach
    void before(){
        DAOToDTOConverter = ac.getBean(VideoDAOToDTOConverter.class);
        DTOToDAOConverter = ac.getBean(VideoDTOToDAOConverter.class);

        person1 = new UserDAO();
        person1.setName("권우현");
        person1.setUserId(1L);
        person1.setEmail("kwh1208@naver.com");
        person1.setSocial("네이버");
        person1.setTime(LocalDateTime.of(2024,4,2,12,0,0));

        expectedDAO = new VideoDAO();
        expectedDAO.setVideoId(1L);
        expectedDAO.setTime(LocalDateTime.of(2024, 4,2,12,0,0));
        expectedDAO.setExposure(true);
        expectedDAO.setVideoTitle("비디오 제목");
        expectedDAO.setVideoLink("비디오 링크");
        expectedDAO.setRawTags("삼성.카카오.네이버");
        expectedDAO.setUser(person1);

        expectedDTO = new VideoDTO();
        expectedDTO.setVideoId(1L);
        expectedDTO.setTime(LocalDateTime.of(2024, 4,2,12,0,0));
        expectedDTO.setExposure(true);
        expectedDTO.setVideoTitle("비디오 제목");
        expectedDTO.setVideoLink("비디오 링크");
        expectedDTO.setTags(new String[]{"삼성", "카카오", "네이버"});
        expectedDTO.setUserId(person1.getUserId());
        expectedDTO.setUserName(person1.getName());
    }



    @Test
    void DAOToDTOTest(){

        VideoDTO videoDTO = DAOToDTOConverter.convert(expectedDAO);

        assertThat(videoDTO).isInstanceOf(VideoDTO.class);
        assertThat(videoDTO.getVideoId()).isEqualTo(expectedDAO.getVideoId());
    }

    @Test
    void DTOToDAOTest(){
        VideoDAO videoDAO = DTOToDAOConverter.convert(expectedDTO);

        assertThat(videoDAO).isInstanceOf(VideoDAO.class);
        assertThat(videoDAO.getVideoId()).isEqualTo(expectedDTO.getVideoId());
    }




}