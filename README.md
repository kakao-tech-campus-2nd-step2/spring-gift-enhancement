# spring-gift-enhancement

## 프로그래밍 요구사항
- 자바 코드 컨벤션을 지키면서 프로그래밍한다.
    - 기본적으로 Google Java Style Guide를 원칙으로 한다.
    - 단, 들여쓰기는 '2 spaces'가 아닌 '4 spaces'로 한다.
- indent(인덴트, 들여쓰기) depth를 3이 넘지 않도록 구현한다. 2까지만 허용한다.
    - 예를 들어 while문 안에 if문이 있으면 들여쓰기는 2이다.
    - 힌트: indent(인덴트, 들여쓰기) depth를 줄이는 좋은 방법은 함수(또는 메서드)를 분리하면 된다.
- 3항 연산자를 쓰지 않는다.
- 함수(또는 메서드)의 길이가 15라인을 넘어가지 않도록 구현한다.
    - 함수(또는 메서드)가 한 가지 일만 잘 하도록 구현한다.
- else 예약어를 쓰지 않는다.
    - else를 쓰지 말라고 하니 switch/case로 구현하는 경우가 있는데 switch/case도 허용하지 않는다.
    - 힌트: if 조건절에서 값을 return하는 방식으로 구현하면 else를 사용하지 않아도 된다.

## STEP-1 기능 요구 사항
- 상품 정보에 카테고리를 추가한다. 상품과 카테고리 모델 간의 관계를 고려하여 설계하고 구현한다.
- 상품에는 항상 하나의 카테고리가 있어야 한다.
- 상품 카테고리는 수정할 수 있다.
- 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
- 카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다.
- 카테고리의 예시는 아래와 같다.
  - 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점, ...
- 아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.
##### Request
```
GET /api/categories HTTP/1.1
```
##### Response
```
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
## STEP-1 기능 구현 목록
- Entity
  - CategoryEntity
    - Long id
      - Primary Key
      - Auto Increment 
    - String name
      - Unique Key
    - String color
    - String imageUrl
    - String description
  - ProductEntity
    - 기존 필드들...
    - CategoryEntity categoryEntity
      - ManyToOne Mapping
- Controller
  - RequestMapping("api/categories) 
  - GET, POST, PUT, DELETE 메소드에 따른 컨트롤러 메서드
- Service
  - 해당 CRUD에 따른 메서드
  - Page<Category> getAllCategory(Pageable pageable)
  - void save(Category category)
  - void update(Category category)
  - void delete(Long categoryId)
- Repository
  - JpaRepository를 extends
- Product 관련 클래스들
  - product의 정보에 CategoryId를 추가

## STEP-2 기능 요구 사항
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

#### Request
```
GET /api/products/1/options HTTP/1.1
```
#### Response
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

## STEP-2 기능 구현 목록
- Entity
  - OptionEntity
    - Long id
      - Primary Key
      - Auto Increment
    - String name
    - Long quantity
    - ProductEntity productEntity
      - ManyToOne Mapping
  - ProductEntity
    - 기존 필드들...
    - set<OptionEntity> optionEntities
      - 동일한 이름 중복 불가로 인해 Set 설정 
      - OneToMany Mapping
- Controller
  - RequestMapping("api/products/{id}/options")
  - GET, POST, PUT, DELETE 메소드에 따른 컨트롤러 메서드
- Service
  - 해당 CRUD에 따른 메서드
  - List<Option> getAllOptions()
  - Option getOptionById(Long id)
  - void save(Option option)
  - void update(Long id, Option option)
  - void delete(Long id)
- Repository
  - JpaRepository를 extends
