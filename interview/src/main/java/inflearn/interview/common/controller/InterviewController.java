package inflearn.interview.common.controller;

import inflearn.interview.common.aop.ValidateUser;
import inflearn.interview.common.domain.UrlReturnDTO;
import inflearn.interview.video.domain.VideoDTO2;
import inflearn.interview.common.domain.VideoNameDTO;
import inflearn.interview.common.service.S3Service;
import inflearn.interview.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {
    /**
     * 영상 녹화 완료
     */
    private final VideoService videoService;
    private final S3Service s3Service;

    @ValidateUser
    @PostMapping("/complete")
    public ResponseEntity<?> complete(@RequestBody VideoDTO2 video){
        Long videoId = videoService.completeVideo(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("videoId", videoId));
    }

    @PostMapping("/presigned")
    public ResponseEntity<?> createPresigned(@RequestBody VideoNameDTO videoNameDTO) {
        UrlReturnDTO urlDTO = s3Service.getPresignedUrl(videoNameDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(urlDTO);
    }
}