# 깃허브 충돌 관련

merge이후 원격 20jcode 브랜치에서 rebase를 통해서 다음 브랜치를 해당 베이스로 변경해주어야함.

git - rebase - nextstep - 20jcode

경로 참조해서 하기

step0 -> PR -> merge -> step1을 rebase -> 진행 -> PR 이런 방식으로 되어야함.

## 이번주차 목표

1. 테스트 코드 구현
2. 주차별 목표 수행
3. HTTP 메소드 알맞은 양식 사용
4. 다른 사람 리뷰를 함께 보며 리펙토링 할 부분 찾기
5. ~~깃허브 동작 정확하게 익히기~~
6. 관리자 콘솔 front-end 구현
7. ~~JWT 부분 정상 동작 확인하기~~

### 진행내용

1. 위시 리스트 아이템 추가 test 오류

memberDTO에서 getID를 하는 과정에서 id가 조회되지 않아서 생기는 문제

id가 Null인 상태로 넘어와서 생긴다.

JWT를 통해서 인증하는 과정에 생긴 문제로 추정되어진다.

AuthenticationTool에서 id가 null인 member가 들어가서 생기는 문제 아닐까?

처음 컨트롤러에 LoginMember를 넣어주는 부분 -> ArgumentResolver에 문제가 있는 듯하다.

* 해결완료 -> webconfig를 작성하지 않아서 ArgumentResolver가 동작하지 않았기 때문이다.

2. test 코드 정상 동작하도록 만들기

하지만 wishlist 추가가 정상적으로 되고 있지 않은데, 이는 상품이 추가되지않아서 그런 상황이다.

이제 mock를 테스트과정에 도입해서 로직이 잘 돌아가는 지 확인하고, 추가적으로 예외를 정리해서 어디에서 문제가 생겼는지

디버깅을 할 수 있도록? 해야겠?다?

* 해결완료 -> wish 테스트 코드 정상 작동 확인

