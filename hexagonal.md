# 아키텍처 변경

기존 Layered architecture에서 DDD(Domain Driven Design)를 적용하기 위해 hexagonal architecture로 변경하였다
아키텍처 변경의 최종 목표는 의존성을 낮추고 객체의 순수함을 유지하기 위함이다

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

기존 도메인에는 @Entity가 붙어있어 영속성 객체와 도메인을 모두 담당하였다 그리고 사진에서 볼 수 있듯이 해당 Post 객체에 대한 생성자만 있을 뿐 도메인은 아무일도 하지 않는다 
-> 객체지향적이지 않다
모든일은 Service에서 다 담당하여 진행했었다 -> Service는 모든일을 처리하는 신과같은 존재

#### 변경 후

![image](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/0fa02924-9ac5-4bf9-b634-0301893f8f12)
![image](https://github.com/inf-interview/gachi-interview-BE/assets/79399385/189b4275-eb2d-4396-b662-7c927a383a80)

변경된 도메인을 보면 게시글에 좋아요를 누르는 등의 행동을 취할 때 plusLike 메서드를 사용하여 객체 자신의 좋아요 수를 1늘리고있다
변경 후에는 Service는 그저 Controller에서 받아온 DTO를 도메인 계층으로 던져주게되고 Service의 부담이 조금 줄어들게 된다

변경 전에서는 도메인 객체에 @Entity 어노테이션이 붙어있어 도메인 객체이자 영속성 객체로 책임이 많았었다
이를 순수한 도메인 객체와 값을 갱신시켜주는 @Entity 어노테이션이 붙은 영속성 객체로 분리하였다

#### 트레이드오프

repository에서 찾아온 영속성 객체를 도메인으로 변경해주는 과정에서 매우 주의해야한다 값을 제대로 전달해주지 못할 경우 갱신이 되지 않거나 누락된다 -> 어쩌면 @Builder를 사용하는것이 문제일 수도 있음
배포 후에도 해당 문제가 많이 발견됨
해결방법으로는 Service단의 테스트 코드 작성을 조금 더 신경을 써서 모든 필드를 다 검증해야한다

### 3. repository 추상화

#### 변경 전

기존 리포지토리는 interface에서 JPARepository를 그대로 상속받아 사용하는 방식이었다
이는 리포지토리와 JPA 사이에 의존도가 너무 높았다

![image](https://github.com/user-attachments/assets/2b7e80b8-9801-4c5b-92a7-08ff3b085138)

#### 변경 후

JPA와 Repository 인터페이스 사이에 구현체를 하나 두고 그 구현체에서만 JPA를 의존하는 방식으로 변경되었다
Service는 인터페이스를 주입받고 그 자식인 구현체는 알지 못하여도 상관이없다

![image](https://github.com/user-attachments/assets/47fb2892-8efe-4c8d-b318-b7c0943b182d)

화살표의 방향을 바꾸어 의존성을 낮췄다

#### 트레이드오프

서비스에서 JPARepository를 사용하는것이 아닌 인터페이스를 상속받는것이기 때문에 JPA의 주요기능인 변경감지(Dirty Checking)를 사용하지 못하여 repository.save()등을 호출하여 변경이 있는 엔티티를 다시 갱신해주어야 한다
