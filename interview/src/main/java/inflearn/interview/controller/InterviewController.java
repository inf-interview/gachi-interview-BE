package inflearn.interview.controller;

import inflearn.interview.aop.ValidateUser;
import inflearn.interview.domain.dto.VideoDTO2;
import inflearn.interview.service.VideoService;
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

    @ValidateUser
    @PostMapping("/complete")
    public ResponseEntity<?> complete(@RequestBody VideoDTO2 video){
        Long videoId = videoService.completeVideo(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("videoId", videoId));
    }
}