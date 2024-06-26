package inflearn.interview.common.service;

import inflearn.interview.user.domain.User;
import inflearn.interview.common.exception.OptionalNotFoundException;
import inflearn.interview.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static inflearn.interview.common.constant.GptCount.INTERVIEW_MAX_COUNT;
import static inflearn.interview.common.constant.GptCount.QUESTION_MAX_COUNT;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GptCallCountService {

    private final UserRepository userRepository;

    //문제 -> 전날에 COUNT를 2번만 사용한 유저가 24시간이 지난 다음날 사용하여도 count + 1이 되고 시간 초기화가 안됨
    @Transactional(propagation = Propagation.REQUIRES_NEW) //GPT의 대답을 기다리기보다 카운팅을 먼저 올리도록 설정
    public void plusQuestionCallCount(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        //유저의 questionCall이 0인 경우
        //LocalDateTime을 지금 시간으로 초기화
        //count++;
        if (user.getQuestionGptCallCount().equals(0)) {
            user.setQuestionGptCallTime(LocalDateTime.now());
            user.setQuestionGptCallCount(1);
            //유저의 questionCall이 1~4 인 경우
            //시간 체크 후 24시간이 지났으면 카운트를 다시 1로 변경
            //이외에는 count만 1씩 계속 늘리기
        } else if (user.getQuestionGptCallCount() < QUESTION_MAX_COUNT) {
            if (isQuestionTimeAfter(user)) {
                user.setQuestionGptCallCount(1);
                user.setQuestionGptCallTime(LocalDateTime.now());
            } else {
                user.setQuestionGptCallCount(user.getQuestionGptCallCount() + 1);
            }
            //유저의 questionCall이 5인경우
            //1. 5인 경우인데 LocalDateTime이 24시간이 안 지난 경우 -> 막고 예외 처리
            //2. 5인 경우인데 LocalDateTime이 24시간이 지난 경우 -> 새로 1로 할당하고 LocalDateTime 현재시간으로 초기화
        } else if (user.getQuestionGptCallCount().equals(QUESTION_MAX_COUNT)) {
            if (isQuestionTimeAfter(user)) {
                user.setQuestionGptCallCount(1);
                user.setQuestionGptCallTime(LocalDateTime.now());
            }
        }

    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void plusInterviewCallCount(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);

        if (user.getInterviewGptCallCount().equals(0)) {
            user.setInterviewGptCallTime(LocalDateTime.now());
            user.setInterviewGptCallCount(1);

        } else if (user.getInterviewGptCallCount() < INTERVIEW_MAX_COUNT) {
            if (isInterviewTimeAfter(user)) {
                user.setInterviewGptCallCount(1);
                user.setInterviewGptCallTime(LocalDateTime.now());
            } else {
                user.setInterviewGptCallCount(user.getInterviewGptCallCount() + 1);
            }
        } else if (user.getInterviewGptCallCount().equals(INTERVIEW_MAX_COUNT)) {
            if (isInterviewTimeAfter(user)) {
                user.setInterviewGptCallCount(1);
                user.setInterviewGptCallTime(LocalDateTime.now());
            }
        }
    }

    // 인터뷰 완료 후 클라이언트로 보내줄 메서드
    @Transactional(readOnly = true)
    public Integer interviewCountToClient(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        return user.getInterviewGptCallCount();
    }

    // 서버에서 유저의 카운트 체크하는 메서드
    public Integer getInterviewCount(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        if (isInterviewTimeAfter(user)) {
            user.setInterviewGptCallCount(0);
        }
        return user.getInterviewGptCallCount();
    }

    public Integer getQuestionCount(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        if (isQuestionTimeAfter(user)) {
            user.setQuestionGptCallCount(0);
        }
        return user.getQuestionGptCallCount();
    }

    private boolean isInterviewTimeAfter(User user) {
        if (user.getInterviewGptCallTime() == null) {
            return true;
        }
        return user.getInterviewGptCallTime().isAfter(user.getInterviewGptCallTime().plusDays(1));
    }

    private boolean isQuestionTimeAfter(User user) {
        if (user.getQuestionGptCallTime() == null) {
            return true;
        }
        return user.getQuestionGptCallTime().isAfter(user.getQuestionGptCallTime().plusDays(1));
    }
}
