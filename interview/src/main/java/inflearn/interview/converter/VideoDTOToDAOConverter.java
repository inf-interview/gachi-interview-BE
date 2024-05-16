package inflearn.interview.converter;

import inflearn.interview.domain.User;
import inflearn.interview.domain.Video;
import inflearn.interview.domain.dto.VideoDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VideoDTOToDAOConverter implements Converter<VideoDTO, Video>{

    @Override
    public Video convert(VideoDTO source) {
        Video DAO = new Video();
        User user = new User();
        user.setUserId(source.getUserId());
        user.setName(source.getUserName());
        DAO.setUser(user);
        DAO.setVideoId(source.getVideoId());
        DAO.setTime(source.getTime());
        DAO.setUpdatedTime(source.getUpdatedTime());
        DAO.setExposure(source.getExposure());
        DAO.setVideoTitle(source.getVideoTitle());
        DAO.setVideoLink(source.getVideoLink());
        String[] tags = source.getTags();
        StringBuilder rawTag = new StringBuilder();
        for (String tag : tags) {
            rawTag.append(tag).append(".");
        }
        DAO.setRawTags(rawTag.toString());
        return DAO;
    }
}
