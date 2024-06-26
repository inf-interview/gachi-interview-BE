package inflearn.interview.feedback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import inflearn.interview.common.aop.ValidateUser;
import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.feedback.domain.FeedbackDTO;
import inflearn.interview.common.domain.GptCountDTO;
import inflearn.interview.common.service.GptCallCountService;
import inflearn.interview.common.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final GptService gptService;
    private final GptCallCountService callCountService;

    @ValidateUser
    @PostMapping("/{video_Id}")
    public void submit(@AuthenticationPrincipal UserEntity userEntity, @PathVariable Long video_Id, @RequestBody FeedbackDTO dto) throws JsonProcessingException {
        gptService.GPTFeedback(video_Id, userEntity, dto);
    }

    @GetMapping("/limits")
    public ResponseEntity<?> checkLimits(@AuthenticationPrincipal UserEntity userEntity) {
        Integer count = callCountService.interviewCountToClient(userEntity.getUserId());
        GptCountDTO dto = new GptCountDTO(3, count);
        return ResponseEntity.ok(dto);
    }

//    @DeleteMapping("/{feedbackId}")
//    public void delete(@PathVariable Long feedbackId) {
//        gptServiceImpl.deleteFeedback(feedbackId);
//    }
//
//    @GetMapping("/{userId}")
//    public List<Feedback> getFeedbacks(@RequestParam Long userId){
//        return gptServiceImpl.getFeedbacks(userId);
//    }
//
//    @GetMapping("/{feedbackId}")
//    public Feedback getFeedback(@PathVariable Long feedbackId){
//        return gptServiceImpl.getFeedback(feedbackId);
//    }
}