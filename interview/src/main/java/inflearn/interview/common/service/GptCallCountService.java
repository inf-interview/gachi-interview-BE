package inflearn.interview.common.service;

import inflearn.interview.user.infrastructure.UserEntity;
import inflearn.interview.common.exception.OptionalNotFoundException;
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

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        //유저의 questionCall이 0인 경우
        //LocalDateTime을 지금 시간으로 초기화
        //count++;
        if (userEntity.getQuestionGptCallCount().equals(0)) {
            userEntity.setQuestionGptCallTime(LocalDateTime.now());
            userEntity.setQuestionGptCallCount(1);
            //유저의 questionCall이 1~4 인 경우
            //시간 체크 후 24시간이 지났으면 카운트를 다시 1로 변경
            //이외에는 count만 1씩 계속 늘리기
        } else if (userEntity.getQuestionGptCallCount() < QUESTION_MAX_COUNT) {
            if (isQuestionTimeAfter(userEntity)) {
                userEntity.setQuestionGptCallCount(1);
                userEntity.setQuestionGptCallTime(LocalDateTime.now());
            } else {
                userEntity.setQuestionGptCallCount(userEntity.getQuestionGptCallCount() + 1);
            }
            //유저의 questionCall이 5인경우
            //1. 5인 경우인데 LocalDateTime이 24시간이 안 지난 경우 -> 막고 예외 처리
            //2. 5인 경우인데 LocalDateTime이 24시간이 지난 경우 -> 새로 1로 할당하고 LocalDateTime 현재시간으로 초기화
        } else if (userEntity.getQuestionGptCallCount().equals(QUESTION_MAX_COUNT)) {
            if (isQuestionTimeAfter(userEntity)) {
                userEntity.setQuestionGptCallCount(1);
                userEntity.setQuestionGptCallTime(LocalDateTime.now());
            }
        }

    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void plusInterviewCallCount(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);

        if (userEntity.getInterviewGptCallCount().equals(0)) {
            userEntity.setInterviewGptCallTime(LocalDateTime.now());
            userEntity.setInterviewGptCallCount(1);

        } else if (userEntity.getInterviewGptCallCount() < INTERVIEW_MAX_COUNT) {
            if (isInterviewTimeAfter(userEntity)) {
                userEntity.setInterviewGptCallCount(1);
                userEntity.setInterviewGptCallTime(LocalDateTime.now());
            } else {
                userEntity.setInterviewGptCallCount(userEntity.getInterviewGptCallCount() + 1);
            }
        } else if (userEntity.getInterviewGptCallCount().equals(INTERVIEW_MAX_COUNT)) {
            if (isInterviewTimeAfter(userEntity)) {
                userEntity.setInterviewGptCallCount(1);
                userEntity.setInterviewGptCallTime(LocalDateTime.now());
            }
        }
    }

    // 인터뷰 완료 후 클라이언트로 보내줄 메서드
    @Transactional(readOnly = true)
    public Integer interviewCountToClient(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        return userEntity.getInterviewGptCallCount();
    }

    // 서버에서 유저의 카운트 체크하는 메서드
    public Integer getInterviewCount(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        if (isInterviewTimeAfter(userEntity)) {
            userEntity.setInterviewGptCallCount(0);
        }
        return userEntity.getInterviewGptCallCount();
    }

    public Integer getQuestionCount(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(OptionalNotFoundException::new);
        if (isQuestionTimeAfter(userEntity)) {
            userEntity.setQuestionGptCallCount(0);
        }
        return userEntity.getQuestionGptCallCount();
    }

    private boolean isInterviewTimeAfter(UserEntity userEntity) {
        if (userEntity.getInterviewGptCallTime() == null) {
            return true;
        }
        return userEntity.getInterviewGptCallTime().isAfter(userEntity.getInterviewGptCallTime().plusDays(1));
    }

    private boolean isQuestionTimeAfter(UserEntity userEntity) {
        if (userEntity.getQuestionGptCallTime() == null) {
            return true;
        }
        return userEntity.getQuestionGptCallTime().isAfter(userEntity.getQuestionGptCallTime().plusDays(1));
    }
}
