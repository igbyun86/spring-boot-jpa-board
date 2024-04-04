# JPA Reference
http://querydsl.com/static/querydsl/3.5.0/reference/ko-KR/html/ch01.html

### 1.2 
Querydsl의 핵심 원칙은 타입 안정성(Type safety)이다. 도메인 타입의 프로퍼티를 반영해서 생성한 쿼리 타입을 이용해서 쿼리를 작성하게 된다. 또한, 완전히 타입에 안전한 방법으로 함수/메서드 호출이 이루어진다.

또 다른 중요한 원칙은 일관성(consistency)이다. 기반 기술에 상관없이 쿼리 경로와 오퍼레이션은 모두 동일하며, Query 인터페이스는 공통의 상위 인터페이스를 갖는다.



## Test
### 조회
- 페이징
- 조건
- JOIN
- subQuery

### 테스트 케이스
- 부서별 급여가 가장 많은 사원