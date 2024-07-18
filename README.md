# spring-gift-enhancement

## step2 기능 구현 목록

기능 요구 사항
상품 정보에 옵션을 추가한다. 상품과 옵션 모델 간의 관계를 고려하여 설계하고 구현한다.

- 상품에는 항상 하나 이상의 옵션이 있어야 한다.
- 옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다.
- 특수 문자
    - 가능: ( ), [ ], +, -, &, /, _
    - 그 외 특수 문자 사용 불가
- 옵션 수량은 최소 1개 이상 1억 개 미만이다.
- 중복된 옵션은 구매 시 고객에게 불편을 줄 수 있다. 동일한 상품 내의 옵션 이름은 중복될 수 없다.
- (선택) 관리자 화면에서 옵션을 추가할 수 있다.
  아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.

```
Request
GET /api/products/1/options HTTP/1.1
Response
HTTP/1.1 200
Content-Type: application/json

[
  {
    "id": 464946561,
    "name": "01. [Best] 시어버터 핸드 & 시어 스틱 립 밤",
    "quantity": 969
  }
]
```

- [ ] Option CRUD API 구현
    - [ ] option 추가
    - [ ] option 수정
    - [ ] option 삭제
    - [ ] 상품 조회에 option 추가
    - [ ] 상품 추가에 option 항목 추가
    - [ ] 상품 수정에 option 항목 추가
    - [ ] 상품별 옵션 조회
    - [ ] 옵션별 상품 조회
- [ ] OptionService 구현
- [ ] OptionRepository 구현

## step2로 넘어오면서 리팩토링 해야할 목록

- [ ] 테스트 코드 전체적인 리팩토링
    - [ ] service 단위 테스트
    - [ ] controller 복합 테스트
- [ ] pageRequest 작성
- [ ] pageResponse 함수형 인터페이스 사용하는 부분 수정
- [ ] 상품 조회 API 테스트 코드 수정