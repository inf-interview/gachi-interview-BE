package inflearn.interview.controller;

import inflearn.interview.domain.Feedback;
import inflearn.interview.domain.User;
import inflearn.interview.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    /*
      gpt 피드백 보내기
      gpt 피드백 삭제
      내가 요청한 피드백들 보기
     */
    private final FeedbackService feedbackService;



    @PostMapping("/submit-gpt")
    public void submit(@RequestBody User user, Long videoId){
        feedbackService.GPTFeedback(videoId, user);
    }

    @DeleteMapping("/delete-feedback/{feedbackId}")
    public void delete(@PathVariable Long feedbackId){
        feedbackService.deleteFeedback(feedbackId);
    }


    @GetMapping("/feedbacks")
    public List<Feedback> getFeedbacks(Long videoId){
        return feedbackService.getFeedbacks(videoId);
    }

    @GetMapping("/feedback/{feedbackId}")
    public Feedback getFeedback(@PathVariable Long feedbackId){
        return feedbackService.getFeedback(feedbackId);
    }

}