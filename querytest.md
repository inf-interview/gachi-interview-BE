# 게시글 목록 조회 부하 테스트

게시글 1000건 저장 후 각 게시글에 댓글 2개씩 저장

Pagination이 적용되어있어 한 페이지당 게시글은 12건 씩 호출
테스트는 1000(numberOfThread) x 10회(Loop count)로 10000건을 총 10번 진행하여 평균을 냈다 

## 테스트 1 (서브쿼리) 1138.6

![테스트 1](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/97ebec02-57cd-43fd-b85f-526effe3b894)

select절에 select 서브쿼리를 두고 댓글의 count를 가져오고 있다

![게시글목록 10000건 부하 테스트 1 서브쿼리 (댓글2건씩 저장) 10회 실행](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/89168639-2cf7-4d45-a122-03f394698d38)

Throughput(처리량) 1138.6/s 이다 초당 1138개의 요청 처리


## 테스트 2 (leftjoin) 1584.2

![테스트 2](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/47d2b1a5-fdd1-4080-a8eb-8f901fac90b7)

댓글의 게시글의 id와 게시글의 id로 on절에 조건을 주고 게시글의 id로 groupby를 하여 join하였다

![게시글목록 10000건 부하 테스트 2 조인 (댓글2건씩 저장) 10회 실행](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/049f5103-8f25-4916-858c-bdc6bb599f5f)

처리량 1584.2/s 나오게되었다 확실히 서브쿼리보다는 성능이 1.4배정도 좋아졌다

## 테스트 3 (게시글 도메인에 댓글 갯수를 두기) 1703.2

![테스트 3](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/661a3818-5bef-4da0-af5c-c72885a9c161)

따로 서브쿼리나 조인을 하지않고 그냥 게시글에 컬럼을 하나 더 두고 댓글이 작성될 경우 댓글수가 오르도록 하였다

![게시글목록 10000건 부하 테스트 3 게시글에 컬럼추가 (댓글2건씩 저장) 10회 실행](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/8e2a7aae-c6ff-4472-aa89-53f175429a8b)

처리량이 1703.2/s로 상승하였다


## 결론
서브쿼리보다 조인을 사용하는 것이 성능이 더 좋다는 말만 들어봤었다
이번 기회에 jmeter를 사용하여 직접 테스트를 해봤는데 눈으로 확인하는 것이 역시 믿음이 간다
확실히 테스트1보다는 테스트2가 성능이 좋고 테스트2와 테스트3은 조금 고민해볼만 하다

#### 트레이드오프
아무래도 테스트 3번을 적용시키기 위해 댓글 작성될때, 삭제될 때의 로직이 모두 변경되고 도메인 자체가 변경되는 일이다보니 적지않은 공사인 것을 알 수 있다 
