# spring-gift-product
## 기능 요구 사항
- 상품을 조회, 추가, 수정, 삭제할 수 있는 간단한 HTTP API를 구현한다.
- HTTP 요청과 응답은 JSON 형식으로 주고받는다.
- 현재는 별도의 데이터베이스가 없으므로 적절한 자바 컬렉션 프레임워크를 사용하여 메모리에 저장한다.

## 기능 목록
- Controller
  - get
  - add
  - update
  - delete
- Entity
  - product
- DTO
  - RequestDTO
  - ResponseDTO

- 관리자 화면 구현
  - 메인 페이지
    - getAll
  - 상품 입력 폼
    - 상품명, 상품가격, 상품url
  - 상품 선택 후 삭제
- 메모리에 저장하고 있던 모든 코드를 제거하고 H2 데이터베이스를 사용하도록 변경한다.
- 사용하는 테이블은 애플리케이션이 실행될 때 구축되어야 한다.

# spring-gift-wishlist
- step1
  - 상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있다.
  - 특수 문자 가능: ( ), [ ], +, -, &, /, _
  - 그 외 특수 문자 사용 불가
  - "카카오"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있다.
- step2
- 사용자가 회원 가입, 로그인, 추후 회원별 기능을 이용할 수 있도록 구현한다.
  - 회원은 이메일과 비밀번호를 입력하여 가입한다.
  - 토큰을 받으려면 이메일과 비밀번호를 보내야 하며, 가입한 이메일과 비밀번호가 일치하면 토큰이 발급된다.
  - 토큰을 생성하는 방법에는 여러 가지가 있다. 방법 중 하나를 선택한다.
  - (선택) 회원을 조회, 추가, 수정, 삭제할 수 있는 관리자 화면을 구현한다.
-step3
  - 위시 리스트에 등록된 상품 목록을 조회할 수 있다.
  - 위시 리스트에 상품을 추가할 수 있다.
  - 위시 리스트에 담긴 상품을 삭제할 수 있다.

# spring-gift-jpa
- step1
  - 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
  - 아래의 DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성해 본다.
  - @DataJpaTest를 사용하여 학습 테스트를 해 본다.
- step2
  - 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
  - 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.
- step3
- 상품과 위시 리스트 보기에 페이지네이션을 구현한다.
  - 대부분의 게시판은 모든 게시글을 한 번에 표시하지 않고 여러 페이지로 나누어 표시한다. 정렬 방법을 설정하여 보고 싶은 정보의 우선 순위를 정할 수도 있다.
  - 페이지네이션은 원하는 정렬 방법, 페이지 크기 및 페이지에 따라 정보를 전달하는 방법이다.
# spring-gift-enhancement
- step1
- 상품 정보에 카테고리를 추가한다. 상품과 카테고리 모델 간의 관계를 고려하여 설계하고 구현한다.
  - 상품에는 항상 하나의 카테고리가 있어야 한다.
    - 상품 카테고리는 수정할 수 있다.
    - 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
  - 카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다.
  - 카테고리는 수정할 수 있다.
  - 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
  - 카테고리의 예시는 아래와 같다.
    - 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점
  - 아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.
```
Request
GET /api/categories HTTP/1.1
```
```
Response
HTTP/1.1 200
Content-Type: application/json

[
  {
    "id": 91,
    "name": "교환권",
    "color": "#6c95d1",
    "imageUrl": "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png",
    "description": ""
  }
]
```
- Category Entity 만들기
- Category - Product : 일대다 관계
- Category crud api 만들기
- 상품에는 항상 하나의 카테고리가 있어야한다 -> 관리자 화면에서 카테고리를 지정할 수 있다 = 상품은 관리자만 할 수 있다.

- step2
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
```
GET /api/products/1/options HTTP/1.1
```
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
- Option Entity 만들기
- Option - Product : 다대일 단방향 관계
- Product 삭제 시 Option 전부 삭제
- Option crud api 만들기

- step3
  - Option Entity에 옵션 수 차감하는 메서드 추가
  - OptionService에서 차감할 숫자보다 존재하는 옵션 수량이 적으면 예외