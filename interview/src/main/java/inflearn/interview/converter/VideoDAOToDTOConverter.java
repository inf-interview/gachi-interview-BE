package inflearn.interview.converter;

import inflearn.interview.domain.Video;
import inflearn.interview.domain.dto.VideoDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VideoDAOToDTOConverter implements Converter<Video, VideoDTO>{
    @Override
    public VideoDTO convert(Video source) {
        VideoDTO DTO = new VideoDTO();
        DTO.setUserId(source.getUser().getUserId());
        DTO.setUserName(source.getUser().getName());
        DTO.setVideoId(source.getVideoId());
        DTO.setTime(source.getTime());
        DTO.setUpdatedTime(source.getUpdatedTime());
        DTO.setExposure(source.getExposure());
        DTO.setVideoTitle(source.getVideoTitle());
        DTO.setVideoLink(source.getVideoLink());
        DTO.setTags(source.getRawTags().split("\\."));

        return DTO;
    }
}
