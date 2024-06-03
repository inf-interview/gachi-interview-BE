package inflearn.interview.controller;


import inflearn.interview.aop.ValidateUser;
import inflearn.interview.domain.dto.VideoDTO2;
import inflearn.interview.service.VideoLikeService;
import inflearn.interview.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final VideoLikeService videoLikeService;

    @GetMapping("/{video_id}")
    public VideoDTO2 getVideoController(@PathVariable Long video_id){
        return videoService.getVideoById(video_id);
    }

    @ValidateUser
    @PatchMapping("/{video_id}")
    public void editController(@PathVariable Long video_id, @RequestBody @Validated(VideoDTO2.patch.class) VideoDTO2 video){
        videoService.updateVideo(video_id, video);
    }

    @ValidateUser
    @DeleteMapping("/{video_id}")
    public void deleteController(@PathVariable Long video_id, @RequestBody @Validated(VideoDTO2.delete.class) VideoDTO2 video){
        videoService.deleteVideo(video_id);
    }

    @ValidateUser
    @PostMapping("/{video_id}/like")
    public Map<String, Long> likeVideoController(@PathVariable Long video_id, @RequestBody @Validated(VideoDTO2.like.class) VideoDTO2 video){
        return Map.of("numOfLike", videoLikeService.addLike(video_id, video));
    }

    /**
     * 페이징이랑 sortType맞춰서 수정
     */

    @GetMapping("/list")
    public Page<VideoDTO2> videoListController(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "time") String sortType) {
        return videoService.getVideoList(sortType, page);
    }
}