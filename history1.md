# step1

카테고리 필수 조건

- 상품은 항상 하나의 카테고리가 있어야 한다.
- 상품 카테고리는 수정할 수 있다.
- 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
- 상품 추가 시 카테고리를 지정하지 않으면 안되도록 한다.

## 로그

response에서 ResponseEntity를 사용해서 해보도록 하자 + 나중에 전부 수정해주자.

EntityModel -> spring hateoas 이용시 (하이퍼 미디어 규약?)

service 부분이 없어서 그런가 Mock가 제대로 설정되지 않는다.



### 의문

1. post, put에 대해 성공적으로 200을 반환해야한다면, 컨트롤러는 void 여야하는가? 아니면, ResponseEntity로 명시적?

명시적으로 하는 것이 좋다고 생각되어짐. 특정 상황에서는 POST에 대해 처리가 불가능할 수 있기 때문.

2. 200만 반환하고 싶다면, ResponseEntity 로 원시타입을 그대로 사용하여도 되는가? 에 대해

https://vesselsdiary.tistory.com/137

post, put 등에서 반환에서 특정 url로 location header를 변경해서 넘겨주기?


### 리팩토링 예약

- 서비스에 기본적인 CRUD 이름 통일하기

- DTO 이름 Request / Response 로 통일해주기

- 컨트롤러 메소드 이름 통일하기

- post, put에 대한 관리 : post는 생성, put은 일종의 업데이트로 생각하자.

- post, put에 대해 반환타입 -> 기본 200, 추가 전송 데이터 있으면 204, ResponseEntity 명시적 사용

- 컨트롤러에 대해서 Mock 객체를 활용한 테스트를 만들어보자.

