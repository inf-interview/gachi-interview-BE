# 아키텍처 변경

기존 Layered architecture에서 DDD(Domain Driven Design)를 적용하기 위해 hexagonal architecture로 변경하였다

## 변경점

### 1. 패키지 구조

#### 변경 전

![image](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/8fd2a596-cf2a-4cd6-b25c-826306753bb4)
![image](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/a0e926f8-d753-4721-ba1e-0595d13fefa3)

레이어드 아키텍처로 처음에 만들면서 생각한 가장 큰 문제는 도메인이 한눈에 들어오지 않는다는 것이고 DTO를 최소화하여 만든것임에도 늘어나는 DTO를 한 디렉토리에 몰아넣으면서 DTO가 정확히 어떤일을 하는지 알 수 없고 가시성이 너무 떨어졌다

#### 변경 후

![image](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/22604e73-a014-4b42-98a8-2d365db78c3a)
![image](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/adf73124-af41-4351-8fba-719ad30d01b6)

최상위 디렉토리 이름을 도메인의 이름으로 변경하게 되어 내가 찾으려고 하는 파일을 좀 더 쉽게 접근할 수 있게되었고 디렉토리가 잘 구분되고 각 클래스의 역할을 나타낸 이름을 명확히 적고 사용하여 DTO(response)의 양을 줄이지 않아도 가시성이 떨어지지 않게되었다 

### 2. 도메인 객체와 영속성 객체의 분리

#### 변경 전

![image](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/6f348be5-c2ee-422e-860a-efb3b3ebe762)

기존 도메인에는 @Entity가 붙어있어 영속성 객체와 도메인을 모두 담당하였다 그리고 사진에서 볼 수 있듯이 해당 Post 객체에 대한 생성자만 있을 뿐 도메인은 아무일도 하지 않는다 -> 객체지향적이지 않다
모든일은 Service에서 다 담당하여 진행했었다 -> Service는 모든일을 처리하는 신과같은 존재

#### 변경 후

![image](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/0fa02924-9ac5-4bf9-b634-0301893f8f12)
![image](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/189b4275-eb2d-4396-b662-7c927a383a80)

변경된 도메인을 보면 게시글에 좋아요를 누르는 등의 행동을 취할 때 plusLike 메서드를 사용하여 객체 자신의 좋아요 수를 1늘리고있다
변경 후에는 Service는 그저 Controller에서 받아온 DTO를 도메인 계층으로 던져주게되고 Service의 부담이 조금 줄어들게 된다

#### 트레이드오프

repository에서 찾아온 영속성 객체를 도메인으로 변경해주는 과정에서 매우 주의해야한다 값을 제대로 전달해주지 못할 경우 갱신이 되지 않거나 누락된다 -> 어쩌면 @Builder를 사용하는것이 문제일 수도 있음
배포 후에도 해당 문제가 많이 발견됨
해결방법으로는 Service단의 테스트 코드 작성을 조금 더 신경을 써서 모든 필드를 다 검증해야한다

========================================================================================
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
