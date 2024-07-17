# spring-gift-enhancement

## 기능 요구 사항

- 상품 정보에 옵션을 추가한다. 상품과 옵션 모델 간의 관계를 고려하여 설계하고 구현한다.

- 상품에는 항상 하나 이상의 옵션이 있어야 한다.
    - 옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다.
    - 특수 문자
        - 가능: ( ), [ ], +, -, &, /, _
        - 그 외 특수 문자 사용 불가
    - 옵션 수량은 최소 1개 이상 1억 개 미만이다.
- 중복된 옵션은 구매 시 고객에게 불편을 줄 수 있다. 동일한 상품 내의 옵션 이름은 중복될 수 없다.
- (선택) 관리자 화면에서 옵션을 추가할 수 있다.
- 아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.

### Request

```
GET /api/products/1/options HTTP/1.1
```

### Response

```
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

## 프로그래밍 요구 사항

- 구현한 기능에 대해 적절한 테스트 전략을 생각하고 작성한다.

## 구현 기능 목록

1. Option 테이블에 대한 CRUD 기능을 테스트하는 코드를 작성한다.
    1. Option Controller 레이어에서 CRUD 테스트 코드를 작성한다.
    2. Option Service 레이어에서 CRUD 테스트 코드를 작성한다.
2. Option 테이블을 생성한다.
    1. id, name, quantity 세개의 값을 가지고 있다.
        - id : BIGINT
            - Primary key로, auto_increment를 사용한다.
        - name : VARCHAR(255)
            1. 한글과 영어 숫자 공백, 그리고 특수문자 (), [], +, -, &, /, _ 만 허용한다.
            2. 공백을 포함하여 최대 50자까지 입력할 수 있다.
            3. 동일한 Product 내에서 유일하다.
        - quantity : INT
            - 1 이상 1억 미만의 숫자로 제한한다.
        - product : BIGINT
            - Reference key로, Product 테이블을 참조한다.
3. Product 테이블과 Option 테이블을 연결한다.
    - Option이 Many, Product가 One으로 단방향으로 연결된다.
4. Product Controller에서 해당 상품의 옵션을 조회할 수 있다.
    - API 경로는 `/api/products/{product_id}/options` 이다.